package pl.grm.atracon.lib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logging class to log all things from program to file
 */

public class ARCLogger {
	private static Logger logger;

	public static void info(String msg) {
		if (logger != null)
			logger.info(msg);
	}

	public static void log(Level level, String msg, Throwable thrown) {
		if (logger != null)
			logger.log(level, msg, thrown);
	}

	public static void logException(Exception e) {
		log(Level.SEVERE, e.getMessage(), e);
	}

	public static void setLogger(Logger logger) {
		if (logger != null)
			ARCLogger.logger = logger;
	}

	/**
	 * creates logger and associates with fileHandler
	 * 
	 * @return tru if logger created and is valid
	 */
	public static boolean setupLogger(String logFileName) {
		if (logFileName == null || logFileName == "") {
			logFileName = "log.log";
		}
		Logger logger = Logger.getLogger(logFileName);
		FileHandler fHandler = null;
		File mainDir = new File(Constants.LOGSFOLDERNAME);
		try {
			if (!mainDir.exists()) {
				if (!mainDir.mkdir()) {
					throw new IOException("Cannot create logs '" + Constants.LOGSFOLDERNAME + "' folder!");
				}
			}
			fHandler = new FileHandler(Constants.LOGSFOLDERNAME + logFileName, 1048476, 3, true);
			SimpleFormatter formatter = new SimpleFormatter();
			fHandler.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			return false;
		}
		logger.addHandler(fHandler);
		logger.info("Log Started");
		setLogger(logger);
		return true;
	}

	public static void closeLogger() {
		if (logger != null) {
			Handler handler = logger.getHandlers()[0];
			logger.removeHandler(handler);
			handler.close();
		}
	}

	public static void printDebugFieldValue(Object obj, String... stringsFieldNames) {
		Class<?> clazz = obj.getClass();
		if (stringsFieldNames.length == 0) {
			return;
		}
		ArrayList<Field> fields = new ArrayList<Field>();
		Field[] fieldsClazz = clazz.getDeclaredFields();
		for (Field field : fieldsClazz) {
			fields.add(field);
		}
		Class<?> sClazz = clazz;
		while (sClazz.getSuperclass() != null && sClazz.getSuperclass() != Object.class) {
			Class<?> superClass = sClazz.getSuperclass();
			fieldsClazz = superClass.getDeclaredFields();
			for (Field field : fieldsClazz) {
				fields.add(field);
			}
			sClazz = superClass;
		}
		System.out.println("ANALYZE OF " + fields.size() + " FIELDS");
		try {
			for (String string : stringsFieldNames) {
				System.out.print("Field ");
				for (Field field : fields) {
					if (field.getName().equalsIgnoreCase(string)) {
						if (clazz.getMethod("toString") != null) {
							Method method = clazz.getMethod("toString");
							Class<?> fieldClass = field.getDeclaringClass();
							Object objCasted = fieldClass.cast(obj);
							System.out.print(string + ": " + method.invoke(objCasted));
						} else {
							System.out.print(string + ": " + field.toString());
						}
					}
				}
				System.out.println("");
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
