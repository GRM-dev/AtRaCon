package pl.grm.atracon.server;

import java.util.*;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.*;
import pl.grm.atracon.server.conf.ServerConfig;
import pl.grm.atracon.server.db.DBHandler;
import pl.grm.atracon.server.devices.*;

public class ServerMain {

	private static final String LOG_FILE_NAME = "ARC-Server.log";
	private static final String ERR_LOG_FILE_NAME = "ARC-Error_Server.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon_Server.ini";
	private ConfigDB confID;
	private DBHandler dbHandler;

	public static void main(String[] args) {
		ARCLogger.setLogger(ARCLogger.setupLogger(LOG_FILE_NAME));
		ARCLogger.setErrorLogger(ARCLogger.setupLogger(ERR_LOG_FILE_NAME));
		ARCLogger.info("Starting AtRaCon server ...");
		ServerMain server = new ServerMain();
		try {
			server.initialize();
			server.start();
		}
		catch (Exception e) {
			e.printStackTrace();
			ARCLogger.error("Server error\n" + e.getMessage(), e);
		}
		if (server != null) {
			server.stop();
		}
		ARCLogger.info("Stopping AtRaCon server.");
		ARCLogger.closeLoggers();
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
			ARCLogger.error(e);
			stop();
		}

		List<RaspPi> devs = dbHandler.getRaspPiDevices();
		for (RaspPi raspPi : devs) {
			ARCLogger.info(raspPi.toString());
			ARCLogger.info("Atmega devices: ");
			Set<Atmega> atmL = raspPi.getAtmDevices();// dbHandler.getAtmegaDevices(raspPi.getId());
			for (Atmega atmega : atmL) {
				ARCLogger.info(atmega.toString());
				ARCLogger.info("Registry entries: ");
				Map<Integer, Register> registry = atmega.getRegistry();
				Iterator<Integer> it = registry.keySet().iterator();
				while (it.hasNext()) {
					Integer address = it.next();
					Register register = registry.get(address);
					ARCLogger.info(register.toString());
				}
			}
		}

		List<Register> regs = dbHandler.getRegistry();
		for (Register registry : regs) {
			ARCLogger.info(registry.toString());
		}
	}

	/**
	 * Stops server operations
	 */
	public void stop() {
		dbHandler.closeConnection();

	}

}