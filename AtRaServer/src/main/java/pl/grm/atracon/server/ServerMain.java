package pl.grm.atracon.server;

import java.util.List;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.*;
import pl.grm.atracon.server.conf.ServerConfig;
import pl.grm.atracon.server.db.DBHandler;
import pl.grm.atracon.server.devices.RaspPi;

public class ServerMain {

	private static final String LOG_FILE_NAME = "ARC-Server.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon_Server.ini";
	private ConfigDB confID;
	private DBHandler dbHandler;

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger(LOG_FILE_NAME)) {
			System.err.println("Cannot create log handler");
		}
		ARCLogger.info("Starting AtRaCon server ...");

		ServerMain server = new ServerMain();
		try {
			server.initialize();
			server.start();
		}
		catch (Exception e) {
			e.printStackTrace();
			ARCLogger.log(Level.SEVERE, "Server error\n" + e.getMessage(), e);
		}
		if (server != null) {
			server.stop();
		}
		ARCLogger.info("Stopping AtRaCon server.");
		ARCLogger.closeLogger();
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
		dbHandler = new DBHandler(confID);
	}

	/**
	 * Start server operations
	 */
	private void start() {
		try {
			dbHandler.initConnection();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			ARCLogger.log(Level.SEVERE, e.getMessage(), e);
			stop();
		}
		List<RaspPi> devs = dbHandler.getRaspPiDevices();
		for (RaspPi raspPi : devs) {
			System.out.println("Dev " + raspPi.getId() + ": ");
			System.out.println("  -name: " + raspPi.getName());
			System.out.println("  -address: " + raspPi.getAddress());
			System.out.println("  -activated: " + raspPi.isActivated());
			System.out.println("  -description: " + raspPi.getDesc() == null ? "none" : raspPi.getDesc());
			System.out.println(
					"  -last active: " + raspPi.getLastActive() == null ? "NN" : raspPi.getLastActive().getTime());
		}
	}

	/**
	 * Stops server operations
	 */
	public void stop() {
		dbHandler.closeConnection();

	}

}