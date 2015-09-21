package pl.grm.atracon.lib;

import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Logging class to log all things from program to file
 */

public class ARCLogger {

	private static Logger logger;
	private static Logger errLogger;

	public static void info(String msg) {
		if (logger != null) {
			logger.info(msg);
		}
	}

	public static void log(Level level, String msg, Throwable thrown) {
		if (logger != null) {
			logger.log(level, msg, thrown);
		}
	}

	public static void error(Exception e) {
		error(e.getMessage(), e);
	}

	public static void error(String msg, Exception e) {
		if (e != null) {
			errLogger.log(Level.SEVERE, msg, e);
		}
	}

	public static void warn(String msg, Exception e) {
		if (e == null) {
			warn(msg);
		} else {
			logger.log(Level.WARNING, msg, e);
			errLogger.log(Level.WARNING, msg, e);
		}
	}

	public static void warn(String msg) {
		logger.warning(msg);
		errLogger.warning(msg);
	}

	public static void printDebugFieldValue(Object obj, String... stringsFieldNames) {
		Class<?> clazz = obj.getClass();
		if (stringsFieldNames.length == 0) { return; }
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
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void setLogger(Logger loggerT) {
		if (logger == null) {
			ARCLogger.logger = loggerT;
		}
	}

	public static void setErrorLogger(Logger loggerT) {
		if (errLogger == null) {
			ARCLogger.errLogger = loggerT;
		}
	}

	/**
	 * creates logger and associates with fileHandler
	 * 
	 * @return logger if logger created and is valid
	 */
	public static Logger setupLogger(String logFileName) {
		if (logFileName == null || logFileName == "") {
			logFileName = "log.log";
		}
		Logger loggerT = Logger.getLogger(logFileName);
		FileHandler fHandler = null;
		File mainDir = new File(Constants.LOGSFOLDERNAME);
		try {
			if (!mainDir.exists()) {
				if (!mainDir.mkdir()) { throw new IOException(
						"Cannot create logs '" + Constants.LOGSFOLDERNAME + "' folder!"); }
			}
			fHandler = new FileHandler(Constants.LOGSFOLDERNAME + "/" + logFileName, 1048476, 1, true);
			SimpleFormatter formatter = new SimpleFormatter();
			fHandler.setFormatter(formatter);
		}
		catch (SecurityException | IOException e) {
			e.printStackTrace();
			return null;
		}
		loggerT.addHandler(fHandler);
		loggerT.info("Log Started");
		return loggerT;
	}

	public static void closeLoggers() {
		if (logger != null) {
			for (Handler h : logger.getHandlers()) {
				h.flush();
				h.close();
				logger.removeHandler(h);
			}
		}
		if (errLogger != null) {
			for (Handler h : errLogger.getHandlers()) {
				h.flush();
				h.close();
				errLogger.removeHandler(h);
			}
		}
	}
}
