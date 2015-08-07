package pl.grm.atracon.rasp.conf;

import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.conf.ConfigData;

public class RPiConfigId extends ConfigDB {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		add(new ConfigData("HOST_ADDRESS", "localhost"));
		add(new ConfigData("HOST_PORT", "22345"));
		add(new ConfigData("HOST_RMI_NAME", "dbHandler"));
		add(new ConfigData("CONN_ATTEMPTS", 5));
		add(new ConfigData("HOST_CONN_TIME_SPAN", 5000));
	}

}
