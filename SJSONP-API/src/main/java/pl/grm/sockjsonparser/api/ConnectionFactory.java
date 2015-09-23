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

	private ConnectionFactory() {}

	public static SConnection establishConnection(String host, int port, ConnectionTask task, ConnectioSide side)
			throws IOException, NullPointerException, ClassNotFoundException {
		SConnection con = null;
		Socket socket = null;
		JsonConverter.init(task.getClass());
		switch (side) {
			case SERVER :
				ServerSocket sSocket = new ServerSocket(port);
				socket = sSocket.accept();

				break;
			case CLIENT :
				socket = new Socket(host, port);
				break;
		}
		con = ConnectionHandler.getConnection(task, socket, side);
		return con;
	}

}
