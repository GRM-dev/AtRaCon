package pl.grm.atracon.rasp;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import org.ini4j.*;
import org.ini4j.Profile.Section;

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
	private Map<Integer, Integer> atmDevs;

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
		atmDevs = getPortMappings();
		if (!Boolean.parseBoolean(confID.get(ConfigParams.SYNCED.toString()).getValue())) {
			// TODO: read from file which atmega device state was changed and
			// add to field
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

	/**
	 * @return
	 */
	private Map<Integer, Integer> getPortMappings() {
		Map<Integer, Integer> devs = new HashMap<>();
		String filename = confID.get(ConfigParams.PORT_MAPPINGS_FILE.toString()).getValue();
		File file = new File(filename);
		try {
			Ini ini = new Wini(file);
			Section section = ini.get("maps");
			if (section == null) { throw new IOException("Cannot find conf section"); }
			for (String key : section.keySet()) {
				try {
					String value = section.get(key);
					devs.put(Integer.parseInt(key), Integer.parseInt(value));
				}
				catch (IllegalArgumentException e) {
					ARCLogger.warn("Config with name " + key + " not exists", e);
				}
			}
		}
		catch (IOException e) {
			ARCLogger.warn("cannot load config file. Try to repair it or delete", e);
			e.printStackTrace();
		}
		return devs;
	}
}