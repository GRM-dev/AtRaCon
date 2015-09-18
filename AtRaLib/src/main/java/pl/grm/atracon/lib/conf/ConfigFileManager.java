package pl.grm.atracon.lib.conf;

import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;

import org.ini4j.*;
import org.ini4j.Profile.Section;

import pl.grm.atracon.lib.ARCLogger;

public class ConfigFileManager {

	public static boolean configExists(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) { return false; }
		Ini ini;
		try {
			ini = new Wini(file);
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		Section section = ini.get("conf");
		if (section == null) { return false; }
		return true;
	}

	public static void saveConfig(String fileName, ConfigDB config) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			Ini ini = new Wini(file);
			if (!config.isReady()) { return; }
			Section confSection = ini.get("conf");
			if (confSection == null) {
				ini.add("conf");
				confSection = ini.get("conf");
			}
			for (Iterator<String> it = config.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				ConfigData value = config.get(key);
				if (!confSection.containsKey(key) || confSection.get(key) != value.getValue()) {
					switch (value.getValueType()) {
						case STRING :
							ini.add("conf", key, value.getValue());
							break;
						case DOUBLE :
							ini.add("conf", key, Double.valueOf(value.getValue()));
							break;
						case INT :
							ini.add("conf", key, Integer.valueOf(value.getValue()));
							break;
					}
				}
			}
			ini.store();
		}
		catch (IOException e) {
			ARCLogger.log(Level.CONFIG, "cannot save config file", e);
			e.printStackTrace();
		}
	}

	public static ConfigDB readConfig(String filename, ConfigDB configsID) {
		File file = new File(filename);
		try {
			Ini ini = new Wini(file);
			Section section = ini.get("conf");
			if (section == null) { throw new IOException("Cannot find conf section"); }
			for (String key : section.keySet()) {
				try {
					ConfigData keyE = configsID.get(key);
					if (keyE != null) {
						keyE.setValue(section.get(key));
					} else {
						ARCLogger.log(Level.CONFIG, "Found incorrect config key: '" + key + "'", null);
					}
				}
				catch (IllegalArgumentException e) {
					ARCLogger.log(Level.CONFIG, "Config with name " + key + " not exists", e);
				}
			}
		}
		catch (IOException e) {
			ARCLogger.log(Level.CONFIG, "cannot load config file. Try to repair it or delete", e);
			e.printStackTrace();
		}
		return configsID;
	}
}