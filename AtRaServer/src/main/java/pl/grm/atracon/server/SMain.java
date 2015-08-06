package pl.grm.atracon.server;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.conf.ConfigFileManager;
import pl.grm.atracon.server.conf.ServerConfig;

public class SMain {

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger("ARC-Server.log")) {
			System.err.println("Cannot create log handler");
		}
		ARCLogger.info("Starting AtRaCon server ...");
		ConfigDB confID = new ServerConfig();
		if (ConfigFileManager.configExists("AtRaCon.ini")) {
			ConfigFileManager.saveConfig("AtRaCon.in", confID);
		} else {
			confID = ConfigFileManager.readConfig("AtRaCon.ini", confID);
		}

		confID.updateValue("tt", "test");
		ARCLogger.info("Stopping AtRaCon server.");
		ARCLogger.closeLogger();
	}
}
