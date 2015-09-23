/**
 * 
 */
package pl.grm.sockjsonparser.connection;

import java.io.*;
import java.net.Socket;

import pl.grm.sockjsonparser.json.JsonSerializable;

/**
 * @author Levvy055
 *
 */
public interface SConnection {

	void sendObj(JsonSerializable obj) throws IOException;

	JsonSerializable getReceivedObject();

	void close();

	int getPort();

	InputStream getIs();

	OutputStream getOs();

	Socket getSocket();

	boolean isConnected();

	void setConnected(boolean connected);

	boolean isInitialized();

	boolean isClosing();

	void setClosing(boolean closing);

}