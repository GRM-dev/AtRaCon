package pl.grm.atracon.lib.conf;

import java.util.HashMap;

public abstract class ConfigDB extends HashMap<String, ConfigData> {
	private static final long serialVersionUID = 1L;
	private static boolean prepared;

	protected void add(ConfigData confId) {
		if (!containsKey(confId.getName())) {
			put(confId.getName(), confId);
		}
	}

	public abstract void init();

	private void prepare() {
		init();
		prepared = true;
	}

	public boolean isReady() {
		if (!prepared) {
			prepare();
		}
		return prepared && !isEmpty();
	}

	public void remove(String confName) {
		if (containsKey(confName)) {
			super.remove(confName);
		}
	}

	public void updateValue(String name, String value) {
		get(name).setValue(value);
	}

	public void updateValue(String name, double value) {
		get(name).setValue(value);
	}

	public void updateValue(String name, int value) {
		get(name).setValue(value);
	}

}
