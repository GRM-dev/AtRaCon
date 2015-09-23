/**
 * 
 */
package pl.grm.atracon.server.conf;

/**
 * @author Levvy055
 *
 */
public enum ConfigParams {
							DB_HOST("127.0.0.1"),
							DB_PORT(3306),
							DB_USERNAME("atracon"),
							DB_PASSWORD,
							DB_NAME("atracon"),
							RMI_NAME("serverHandler"),
							RMI_PORT(22345),
							SOCKET_LISTENING_PORT(22520);

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
