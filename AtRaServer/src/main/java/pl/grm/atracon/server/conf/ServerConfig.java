package pl.grm.atracon.server.conf;

import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.conf.ConfigData;

public class ServerConfig extends ConfigDB {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		add(new ConfigData("DB_HOST", "127.0.0.1"));
		add(new ConfigData("DB_PORT", 3306));
		add(new ConfigData("DB_USERNAME", "atracon"));
		add(new ConfigData("DB_PASSWORD", "passwd"));
		add(new ConfigData("DB_TABLE", "atmegareg"));
		add(new ConfigData("RMI_NAME", "dbHandler"));
		add(new ConfigData("RMI_PORT", 22345));
	}

}
