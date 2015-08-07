package pl.grm.atracon.server;

import java.util.logging.Level;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.conf.ConfigFileManager;
import pl.grm.atracon.server.conf.ServerConfig;

public class ServerMain {

	private static final String LOG_FILE_NAME = "ARC-Server.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon_Server.ini";
	private ConfigDB confID;

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger(LOG_FILE_NAME)) {
			System.err.println("Cannot create log handler");
		}
		ARCLogger.info("Starting AtRaCon server ...");

		ServerMain server = new ServerMain();
		try {
			server.initialize();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			ARCLogger.log(Level.SEVERE, "Server error\n" + e.getMessage(), e);
		}

		ARCLogger.info("Stopping AtRaCon server.");
		ARCLogger.closeLogger();
	}

	public ServerMain() {
		confID = new ServerConfig();
	}

	private void initialize() {
		confID.init();
		if (!ConfigFileManager.configExists(CONFIG_FILE_NAME)) {
			ConfigFileManager.saveConfig(CONFIG_FILE_NAME, confID);
		} else {
			confID = ConfigFileManager.readConfig(CONFIG_FILE_NAME, confID);
		}
	}

	private void start() {

	}

}