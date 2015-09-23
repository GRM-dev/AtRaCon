/**
 * 
 */
package pl.grm.sockjsonparser.connection;

import java.net.Socket;

import pl.grm.sockjsonparser.api.ConnectionTask;

/**
 * @author Levvy055
 *
 */
public class ConnectionHandler {

	/**
	 * 
	 */
	private ConnectionHandler() {}

	public static SConnection getConnection(ConnectionTask task, Socket socket, ConnectioSide side) {
		switch (side) {
			case CLIENT :
				return getClient(task, socket);
			case SERVER :
				return getServer(task, socket);
			default :
				break;
		}
		return null;
	}

	private static SConnection getServer(ConnectionTask task, Socket socket) {
		return new SConnectionServerImpl(task, socket);
	}

	private static SConnection getClient(ConnectionTask task, Socket socket) {
		return new SConnectionClientImpl(task, socket);
	}
}
