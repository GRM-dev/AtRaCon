package pl.grm.sockjsonparser.json;

import java.io.*;
import java.net.URL;
import java.util.*;

import org.json.*;

public class JsonConverter {

	private static List<String> classNames;

	public static JsonSerializable toObject(String msg) throws JSONException, Exception {
		try {
			if (JsonConverter.canBeDeserializated(msg)) {
				JSONObject jsonObject = new JSONObject(msg);
				JsonSerializable jObj = (JsonSerializable) jsonObject.get("object");
				return jObj;
			}
		}
		catch (NullPointerException e) {
			throw new JsonConvertException(e);
		}
		throw new JsonConvertException("Cannot be deserializated");
	}

	public static String toString(JsonSerializable obj) {
		JsonSerializable jObj = obj;
		return jObj.toString();
	}

	public static boolean canBeDeserializated(String msg) throws Exception {
		if (classNames == null) { throw new Exception("Classes not mapped or not declared to implement"); }
		try {
			String type = new JSONObject(msg).getString("type");
			return classNames.contains(type);
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static void init(Class<?> sClazz) throws NullPointerException, IOException, ClassNotFoundException {
		classNames = new ArrayList<>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) { throw new NullPointerException("No class loader"); }
		String path = sClazz.getPackage().getName();
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, path));
		}
		for (Class clazz : classes) {
			String name = clazz.getName();
			int i = name.lastIndexOf('.');
			name = name.substring(i + 1);
			classNames.add(name);
		}
	}

	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String path) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) { return classes; }
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, path + "/" + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				Class<?> clazz = Class.forName(
						path.replace('/', '.') + "." + file.getName().substring(0, file.getName().length() - 6));
				if (clazz.isAssignableFrom(JsonSerializable.class) || clazz.getSuperclass() == JsonSerializable.class) {
					classes.add(clazz);
				}
			}
		}
		return classes;
	}
}
