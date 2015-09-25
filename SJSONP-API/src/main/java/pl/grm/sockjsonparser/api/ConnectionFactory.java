/**
 * 
 */
package pl.grm.sockjsonparser.api;

import java.io.IOException;
import java.net.*;

import pl.grm.sockjsonparser.connection.*;
import pl.grm.sockjsonparser.json.JsonConverter;

/**
 * @author Levvy055
 *
 */
public class ConnectionFactory {

	private static Socket socket;
	private static ServerSocket sSocket;
	private static boolean closing;

	private ConnectionFactory() {}

	public static SConnection establishConnection(String host, int port, ConnectionTask task, ConnectioSide side)
			throws IOException, NullPointerException, ClassNotFoundException {
		SConnection con = null;
		socket = null;
		closing = false;
		JsonConverter.init(task.getClass());
		switch (side) {
			case SERVER :
				sSocket = new ServerSocket(port);
				try {
					socket = sSocket.accept();
				}
				catch (SocketException e) {
					if (!closing) { throw e; }
				}
				break;
			case CLIENT :
				socket = new Socket(host, port);
				break;
		}
		if (closing) { return null; }
		con = ConnectionHandler.getConnection(task, socket, side);
		return con;
	}

	public static void interruptConnecting() {
		closing = true;
		try {
			if (socket != null) {
				socket.close();
			}
			if (sSocket != null) {
				sSocket.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
