package pl.grm.atracon.server;

import pl.grm.atracon.lib.ARCLogger;

public class SMain {

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger("ARC-Server.log")) {
			System.err.println("Cannot create log handler");
		}
		ARCLogger.info("Starting AtRaCon server ...");

		ARCLogger.info("Stopping AtRaCon server.");
		ARCLogger.closeLogger();
	}
}
