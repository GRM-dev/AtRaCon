/**
 * 
 */
package pl.grm.atracon.server.sockets;

import java.io.IOException;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.*;
import pl.grm.atracon.server.conf.ConfigParams;
import pl.grm.sockjsonparser.api.*;
import pl.grm.sockjsonparser.connection.*;

/**
 * @author Levvy055
 *
 */
public class ClientConnector {

	private int port;
	private SConnection connection;

	public ClientConnector(ConfigDB conf) throws NullPointerException, ClassNotFoundException {
		ConfigData cD = conf.get(ConfigParams.SOCKET_LISTENING_PORT.toString());
		port = Integer.parseInt(cD.getValue());
		ConnectionTask task = new ServerTask();
		try {
			connection = ConnectionFactory.establishConnection(null, port, task, ConnectioSide.SERVER);
		}
		catch (IOException e) {
			e.printStackTrace();
			ARCLogger.error(e);
		}

	}

}