package pl.grm.atracon.rasp;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.conf.ConfigFileManager;
import pl.grm.atracon.rasp.conf.RPiConfigId;

public class RMain {

	public static void main(String[] args) {
		if (!ARCLogger.setupLogger("ARC-RaspPi.log")) {
			System.err.println("Cannot create log handler");
		}
		System.out.println("Starting");
		ARCLogger.info("Starting AtRaCon RaspberryPi Server Extenion ...");
		ConfigDB confID = new RPiConfigId();
		if (ConfigFileManager.configExists("AtRaCon.ini")) {
			ConfigFileManager.saveConfig("AtRaCon.in", confID);
		} else {
			confID = ConfigFileManager.readConfig("AtRaCon.ini", confID);
		}

		confID.updateValue("", "");

		GpioController controller = GpioFactory.getInstance();
		GpioPinDigitalInput myInput18 = controller.provisionDigitalInputPin(RaspiPin.GPIO_18, "MyInput18",
				PinPullResistance.PULL_DOWN);

		while (true) {
			if (myInput18.isHigh()) {
				System.out.println("1");
			} else {
				System.out.println("0");
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

		// FileOperations.saveConfig("AtRaCon.ini", confID);
		ARCLogger.info("Stopping AtRaCon RaspberryPi Server Extenion.");
		ARCLogger.closeLogger();
	}
}