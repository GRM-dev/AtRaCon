package pl.grm.atracon.rasp;

import pl.grm.atracon.lib.ARCLogger;

public class RMain {

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger("ARC-RaspPi.log")) {
			System.err.println("Cannot create log handler");
		}
		ARCLogger.info("Starting AtRaCon RaspberryPi Server Extenion ...");
		// GpioController controller = GpioFactory.getInstance();
		// controller.
		ARCLogger.info("Stopping AtRaCon RaspberryPi Server Extenion.");
		ARCLogger.closeLogger();
	}
}