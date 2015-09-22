/**
 * 
 */
package pl.grm.atracon.rasp.conf;

/**
 * @author Levvy055
 *
 */
public enum ConfigParams {
							RMI_NAME("serverHandler"),
							RMI_PORT(22345),
							RMI_HOST("192.168.1.11");

	private String sParam;
	private int iParam;
	private double dParam;
	private byte paramType;

	private ConfigParams() {
		this.paramType = 0;
	}

	private ConfigParams(String param) {
		this.sParam = param;
		this.paramType = 1;
	}

	private ConfigParams(int param) {
		this.iParam = param;
		this.paramType = 2;
	}

	private ConfigParams(double param) {
		this.dParam = param;
		this.paramType = 3;
	}

	public String getSParam() {
		return sParam;
	}

	public int getIParam() {
		return iParam;
	}

	public double getDParam() {
		return dParam;
	}

	public byte getParamType() {
		return paramType;
	}
}
