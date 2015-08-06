package pl.grm.atracon.lib.conf;

public class ConfigData {
	private String name;
	private String value;
	private ValueType vType;

	public ConfigData(String name, String value) {
		this.name = name;
		this.setValue(value);
	}

	public ConfigData(String name, int value) {
		this.name = name;
		this.setValue(value);
	}

	public ConfigData(String name, double value) {
		this.name = name;
		this.setValue(value);
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public ValueType getValueType() {
		return this.vType;
	}

	public void setValue(String value) {
		this.value = value;
		this.vType = ValueType.STRING;
	}

	public void setValue(double value) {
		this.value = String.valueOf(value);
		this.vType = ValueType.DOUBLE;
	}

	public void setValue(int value) {
		this.value = String.valueOf(value);
		this.vType = ValueType.INT;
	}
}
