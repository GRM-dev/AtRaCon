package pl.grm.atracon.server.conf;

import pl.grm.atracon.lib.conf.*;

public class ServerConfig extends ConfigDB {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		for (ConfigParams param : ConfigParams.values()) {
			String paramName = param.toString();
			switch (param.getParamType()) {
				case 0 :
					add(new ConfigData(paramName, ""));
					break;
				case 1 :
					add(new ConfigData(paramName, param.getSParam()));
					break;
				case 2 :
					add(new ConfigData(paramName, param.getIParam()));
					break;
				case 3 :
					add(new ConfigData(paramName, param.getDParam()));
					break;
			}
		}
	}

}
