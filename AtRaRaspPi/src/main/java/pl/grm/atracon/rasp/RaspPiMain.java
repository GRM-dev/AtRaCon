package pl.grm.atracon.rasp;

import java.rmi.*;
import java.rmi.registry.*;

import com.pi4j.io.gpio.*;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.*;
import pl.grm.atracon.lib.rmi.IAtRaConRemoteController;
import pl.grm.atracon.rasp.conf.*;
import pl.grm.atracon.rasp.conn.gp.GpioManager;

public class RaspPiMain {

	private static final String LOG_FILE_NAME = "ARC-RaspPi.log";
	private static final String ERR_LOG_FILE_NAME = "ARC-Error_RaspPi.log";
	private static final String CONFIG_FILE_NAME = "AtRaCon.ini";
	private ConfigDB confID;

	public static void main(String[] args) {
		ARCLogger.setLogger(ARCLogger.setupLogger(LOG_FILE_NAME));
		ARCLogger.setErrorLogger(ARCLogger.setupLogger(ERR_LOG_FILE_NAME));
		System.out.println("Starting");
		RaspPiMain pi = new RaspPiMain();
		pi.init();
		pi.start();
		pi.stop();
		ARCLogger.closeLoggers();
	}

	/**
	 * initialization
	 */
	private void init() {
		confID = new RPiConfigId();
		confID.checkReady();
		if (!ConfigFileManager.configExists(CONFIG_FILE_NAME)) {
			ConfigFileManager.saveConfig(CONFIG_FILE_NAME, confID);
		} else {
			confID = ConfigFileManager.readConfig(CONFIG_FILE_NAME, confID);
		}
	}

	private void start() {
		ARCLogger.info("Starting AtRaCon RaspberryPi Server Extenion ...");
		GpioManager gpioManager = new GpioManager();
		gpioManager.readOnPort(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN, 50);
		startServerConnector();
	}

	private void startServerConnector() {
		try {
			String host = confID.get(ConfigParams.RMI_HOST.toString()).getValue();
			int port = Integer.parseInt(confID.get(ConfigParams.RMI_PORT.toString()).getValue());
			Registry reg = LocateRegistry.getRegistry(host, port);
			IAtRaConRemoteController serverHandler = (IAtRaConRemoteController) reg
					.lookup(confID.get(ConfigParams.RMI_NAME.toString()).getValue());
		}
		catch (NumberFormatException e) {
			ARCLogger.error(e);
			e.printStackTrace();
		}
		catch (RemoteException e) {
			ARCLogger.error(e);
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			ARCLogger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * stops program and closes it.
	 */
	private void stop() {
		ConfigFileManager.saveConfig("AtRaCon.ini", confID);
		ARCLogger.info("Stopping AtRaCon RaspberryPi Server Extenion.");

	}
}