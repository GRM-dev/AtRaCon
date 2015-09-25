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
import pl.grm.sockjsonparser.json.JsonSerializable;

/**
 * @author Levvy055
 *
 */
public class ClientConnector {

	private int port;
	private SConnection connection;
	private ConnectionTask task;

	public ClientConnector(ConfigDB conf) throws NullPointerException, ClassNotFoundException {
		ConfigData cD = conf.get(ConfigParams.SOCKET_LISTENING_PORT.toString());
		port = Integer.parseInt(cD.getValue());
		task = new ServerTask();
	}

	public void waitForConnection() throws ClassNotFoundException {
		try {
			connection = ConnectionFactory.establishConnection(null, port, task, ConnectioSide.SERVER);
		}
		catch (IOException e) {
			e.printStackTrace();
			ARCLogger.error(e);
		}
	}

	/**
	 * 
	 */
	public void powerListener() {
		while (isConnected()) {
			JsonSerializable object = connection.getReceivedObject();
			System.out.println(object.toJSONString());
		}
	}

	public void interruptConnection() {
		ConnectionFactory.interruptConnecting();
	}

	public boolean isConnected() {
		if (connection != null) { return connection.isConnected(); }
		return false;
	}

	/**
	 * @return Connection
	 */
	public SConnection getConnection() {
		return connection;
	}
}
