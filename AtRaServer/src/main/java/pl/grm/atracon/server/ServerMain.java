package pl.grm.atracon.server;

import java.net.*;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.*;

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

public class ServerMain {

	private static final String LOG_FILE_NAME = "ARC-Server.log";
	private static final String ERR_LOG_FILE_NAME = "ARC-Error_Server.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon_Server.ini";
	private ConfigDB confID;
	private DBHandlerImpl dbHandler;
	private Thread rmiThread;
	private Thread clientConnectionThread;
	private static ServerMain server;
	private ClientConnector clientConnector;
	private CommandManager commandManager;
	private ServerConsole console;
	private volatile boolean running;
	private Thread consoleThread;

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
	}

	/**
	 * Start server operations
	 */
	private void start() {
		setRunning(true);
		try {
			dbHandler.initConnection();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			ARCLogger.error(e);
			stop();
		}
		startRMIServer();
		startSocketServer();
		startConsole();
	}

	private void startRMIServer() {
		try {
			IAtRaConRemoteController arcServer = new AtRaConServer();

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
		clientConnectionThread = new Thread(() -> {
			try {
				clientConnector = new ClientConnector(confID);
			}
			catch (Exception e) {
				e.printStackTrace();
				ARCLogger.error(e);
			}
		});
		clientConnectionThread.setName("ARC socket thread");
		clientConnectionThread.start();
	}

	/**
	 * 
	 */
	private void startConsole() {
		commandManager = new CommandManager(this);
		console = new ServerConsole(this);
		consoleThread = new Thread(console);
		consoleThread.start();
	}

	/**
	 * Stops server operations
	 */
	public static void stop() {
		if (server != null && server.isRunning()) {
			server.setRunning(false);
			server.dbHandler.closeConnection();
			ARCLogger.info("Stopping AtRaCon server.");
		}
		ARCLogger.closeLoggers();
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