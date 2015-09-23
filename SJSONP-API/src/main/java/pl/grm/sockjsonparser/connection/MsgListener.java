/**
 * 
 */
package pl.grm.sockjsonparser.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.JSONException;

import pl.grm.sockjsonparser.json.*;

/**
 * @author Levvy055
 *
 */
public class MsgListener extends Thread {

	private ConcurrentLinkedQueue<JsonSerializable> receivedObjects;
	private Socket socket;
	private volatile boolean running = true;

	public MsgListener(Socket socket) {
		this.socket = socket;
		this.setName("Socket Listening thread");
		receivedObjects = new ConcurrentLinkedQueue<>();
	}

	@Override
	public void run() {
		while (isRunning()) {
			try {
				String sObj = PacketParser.receivePacket(socket);
				JsonSerializable obj = JsonConverter.toObject(sObj);
				receivedObjects.add(obj);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public JsonSerializable getReceivedObject() {
		return receivedObjects.poll();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
