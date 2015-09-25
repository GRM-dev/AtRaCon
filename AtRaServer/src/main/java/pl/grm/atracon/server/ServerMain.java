package pl.grm.atracon.server;

import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.*;
import pl.grm.atracon.lib.rmi.IAtRaConRemoteController;
import pl.grm.atracon.server.commands.*;
import pl.grm.atracon.server.conf.*;
import pl.grm.atracon.server.db.DBHandlerImpl;
import pl.grm.atracon.server.rmi.AtRaConServer;
import pl.grm.atracon.server.sockets.ClientConnector;
import pl.grm.sockjsonparser.connection.SConnection;

public class ServerMain {

	private static final String LOG_FILE_NAME = "ARC-Server.log";
	private static final String ERR_LOG_FILE_NAME = "ARC-Error_Server.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon_Server.ini";
	private ConfigDB confID;
	private DBHandlerImpl dbHandler;
	private ExecutorService executorClientsService;
	private ExecutorService executorPiService;
	private ExecutorService executorConsoleService;
	private static ServerMain server;
	private ClientConnector clientConnector;
	private CommandManager commandManager;
	private ServerConsole console;
	private volatile boolean running;
	private IAtRaConRemoteController arcServer;

	public static void main(String[] args) {
		ARCLogger.setLogger(ARCLogger.setupLogger(LOG_FILE_NAME));
		ARCLogger.setErrorLogger(ARCLogger.setupLogger(ERR_LOG_FILE_NAME));
		ARCLogger.info("Starting AtRaCon server ...");
		server = new ServerMain();
		try {
			server.initialize();
			server.start();
		}
		catch (Exception e) {
			e.printStackTrace();
			ARCLogger.error("Server error\n" + e.getMessage(), e);
		}

	}

	private ServerMain() {
		confID = new ServerConfig();
	}

	/**
	 * Initialization of server on startup
	 */
	private void initialize() {
		confID.init();
		if (!ConfigFileManager.configExists(CONFIG_FILE_NAME)) {
			ConfigFileManager.saveConfig(CONFIG_FILE_NAME, confID);
		} else {
			confID = ConfigFileManager.readConfig(CONFIG_FILE_NAME, confID);
			ConfigFileManager.saveConfig(CONFIG_FILE_NAME, confID);
		}
		Logger logger = Logger.getLogger(ServerMain.class);
		logger.info(confID.values());
		dbHandler = new DBHandlerImpl(confID);
		executorClientsService = Executors.newFixedThreadPool(2);
		// executorPiService = Executors.newFixedThreadPool(2);
		executorConsoleService = Executors.newSingleThreadExecutor();
	}

	/**
	 * Start server operations
	 */
	private void start() {
		setRunning(true);
		try {
			dbHandler.initConnection();
			startRMIServer();
			startSocketServer();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			ARCLogger.error(e);
			exit();
		}
		startConsole();
	}

	private void startRMIServer() {
		try {
			arcServer = new AtRaConServer();
			ConfigData portConf = confID.get(ConfigParams.RMI_PORT.toString());
			int port = ConfigParams.RMI_PORT.getIParam();
			try {
				if (portConf.getValueType() == ValueType.INT && portConf.getValue() != null
						&& portConf.getValue() != "") {
					port = Integer.parseInt(portConf.getValue());
				} else {
					ARCLogger.warn("Coudn't get port address. Using default instead");
				}
			}
			catch (NumberFormatException e) {
				ARCLogger.warn("Coudn't get port address. Using default instead", e);
			}
			Registry registry = LocateRegistry.createRegistry(port);
			try {
				InetAddress myHost = Inet4Address.getLocalHost();
				String myIP = myHost.getHostAddress();
				ARCLogger.info("Server running on host: " + myIP);
			}
			catch (UnknownHostException e) {
				ARCLogger.warn("Error while getting own IP Address!", e);
				e.printStackTrace();
			}
			String rmiName = confID.get(ConfigParams.RMI_NAME.toString()).getValue();
			try {
				registry.bind(rmiName, arcServer);
			}
			catch (AccessException e1) {
				ARCLogger.error(e1);
				e1.printStackTrace();
			}
			catch (AlreadyBoundException e) {
				ARCLogger.error(e);
				e.printStackTrace();
			}
		}
		catch (RemoteException e) {
			ARCLogger.error(e);
			e.printStackTrace();
		}
	}

	private void startSocketServer() {
		executorClientsService.execute(() -> {
			try {
				clientConnector = new ClientConnector(confID);
				clientConnector.waitForConnection();
			}
			catch (Exception e) {
				e.printStackTrace();
				ARCLogger.error(e);
			}
		});
	}

	/**
	 * Starts console thread
	 */
	private void startConsole() {
		commandManager = new CommandManager(this);
		console = new ServerConsole(this);
		executorConsoleService.execute(console);
	}

	/**
	 * Stops server operations
	 */
	public static void exit() {
		if (server != null && server.isRunning()) {
			server.stop();
			server.console.setStop(true);
			server.executorConsoleService.shutdownNow();
			try {
				server.executorConsoleService.awaitTermination(2000, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) {}
			ARCLogger.closeLoggers();
		}
	}

	public void stop() {
		setRunning(false);
		int port = Integer.parseInt(server.confID.get(ConfigParams.RMI_PORT.toString()).getValue());
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			registry.unbind(server.confID.get(ConfigParams.RMI_NAME.toString()).getValue());
			UnicastRemoteObject.unexportObject(server.arcServer, true);
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
		catch (NotBoundException e) {
			e.printStackTrace();
		}
		ARCLogger.info("Stopping AtRaCon server.");
		if (getClient() != null) {
			getClient().close();
			try {
				getClient().getSocket().close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		clientConnector.interruptConnection();
		try {
			Thread.sleep(300l);
			executorClientsService.shutdown();
			Thread.sleep(500l);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorClientsService.shutdownNow();
		dbHandler.closeConnection();
	}

	public SConnection getClient() {
		if (clientConnector == null) return null;
		return clientConnector.getConnection();
	}

	public CommandManager getCM() {
		return this.commandManager;
	}

	public boolean isRunning() {
		return running;
	}

	private void setRunning(boolean runnning) {
		this.running = runnning;
	}

}