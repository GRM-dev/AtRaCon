package pl.grm.atracon.lib.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

public class FileOperations {
	private static Logger logger;

	public void setLogger(Logger logger) {
		FileOperations.logger = logger;
	}

	public static void saveConfig(String fileName, HashMap<ConfigId, String> config) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			Ini ini = new Wini(file);
			for (Iterator<ConfigId> it = config.keySet().iterator(); it.hasNext();) {
				ConfigId key = it.next();
				String value = config.get(key);
				ini.add("configuration", key.toString(), value);
			}
			ini.store();
		} catch (IOException e) {
			logger.log(Level.CONFIG, "cannot save config file", e);
			e.printStackTrace();
		}
	}

	public static HashMap<ConfigId, String> readConfig(String filename, Class<? extends ConfigId> clazz) {
		HashMap<ConfigId, String> fConfig = new HashMap<>();
		File file = new File(filename);
		try {
			Ini ini = new Wini(file);
			Section section = ini.get("configuration");
			if (section == null) {
				throw new IOException("Cannot find configuration section");
			}
			for (String key : section.keySet()) {
				try {
					ConfigId keyE = ConfigId.getFromString(key);
					fConfig.put(keyE, section.get(key));
				} catch (IllegalArgumentException e) {
					logger.log(Level.CONFIG, "Config with name " + key + " not exists", e);
				}
			}
		} catch (IOException e) {
			logger.log(Level.CONFIG, "cannot load config file. Try to repair it or delete", e);
			e.printStackTrace();
		}
		return fConfig;
	}

}
