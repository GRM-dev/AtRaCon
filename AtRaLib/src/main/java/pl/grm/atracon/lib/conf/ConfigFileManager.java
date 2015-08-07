package pl.grm.atracon.lib.conf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

import pl.grm.atracon.lib.ARCLogger;

public class ConfigFileManager {

	public static boolean configExists(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		}
		Ini ini;
		try {
			ini = new Wini(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		Section section = ini.get("conf");
		if (section == null) {
			return false;
		}
		return true;
	}

	public static void saveConfig(String fileName, ConfigDB config) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			Ini ini = new Wini(file);
			if (!config.isReady()) {
				return;
			}
			for (Iterator<String> it = config.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				ConfigData value = config.get(key);
				if (!ini.get("conf").containsKey(key) || ini.get("conf").get(key) != value.getValue()) {
					if ((value.getValueType() == ValueType.STRING)) {
						ini.add("conf", key, value.getValue());
					} else if (value.getValueType() == ValueType.DOUBLE) {
						ini.add("conf", key, Double.valueOf(value.getValue()));
					} else if (value.getValueType() == ValueType.INT) {
						ini.add("conf", key, Integer.valueOf(value.getValue()));
					}
				}
			}
			ini.store();
		} catch (IOException e) {
			ARCLogger.log(Level.CONFIG, "cannot save config file", e);
			e.printStackTrace();
		}
	}

	public static ConfigDB readConfig(String filename, ConfigDB configsID) {
		File file = new File(filename);
		try {
			Ini ini = new Wini(file);
			Section section = ini.get("conf");
			if (section == null) {
				throw new IOException("Cannot find conf section");
			}
			for (String key : section.keySet()) {
				try {
					ConfigData keyE = configsID.get(key);
					if (keyE != null) {
						keyE.setValue(section.get(key));
					} else {
						ARCLogger.log(Level.CONFIG, "Found incorrect config key: '" + key + "'", null);
					}
				} catch (IllegalArgumentException e) {
					ARCLogger.log(Level.CONFIG, "Config with name " + key + " not exists", e);
				}
			}
		} catch (IOException e) {
			ARCLogger.log(Level.CONFIG, "cannot load config file. Try to repair it or delete", e);
			e.printStackTrace();
		}
		return configsID;
	}
}