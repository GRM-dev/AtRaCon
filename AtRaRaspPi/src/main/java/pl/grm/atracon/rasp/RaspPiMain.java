package pl.grm.atracon.rasp;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.conf.ConfigFileManager;
import pl.grm.atracon.rasp.conf.RPiConfigId;

public class RaspPiMain {

	private static final String LOG_FILE_NAME = "ARC-RaspPi.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon.ini";

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger(LOG_FILE_NAME)) {
			System.err.println("Cannot create log handler");
		}
		System.out.println("Starting");
		ARCLogger.info("Starting AtRaCon RaspberryPi Server Extenion ...");
		ConfigDB confID = new RPiConfigId();
		confID.init();
		if (!ConfigFileManager.configExists(CONFIG_FILE_NAME)) {
			ConfigFileManager.saveConfig(CONFIG_FILE_NAME, confID);
		} else {
			confID = ConfigFileManager.readConfig(CONFIG_FILE_NAME, confID);
		}

		ConfigFileManager.saveConfig("AtRaCon.ini", confID);
		ARCLogger.info("Stopping AtRaCon RaspberryPi Server Extenion.");
		ARCLogger.closeLogger();
	}
}