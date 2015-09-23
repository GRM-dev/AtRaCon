package pl.grm.sockjsonparser.connection;

import java.io.*;
import java.net.Socket;

import pl.grm.sockjsonparser.api.ConnectionTask;
import pl.grm.sockjsonparser.json.JsonSerializable;

class SConnectionServerImpl extends Thread implements SConnection {

	private int port;
	private InputStream is;
	private OutputStream os;
	private boolean connected;
	private boolean initialized;
	private Socket socket;
	private boolean closing;
	private ConnectionTask task;
	private MsgListener listener;

	public SConnectionServerImpl(ConnectionTask task, Socket socket) {
		this.task = task;
		this.socket = socket;
		this.port = socket.getPort();
		this.setName("Socket Connection Thread");
		this.setClosing(false);
		this.listener = new MsgListener(socket);
	}

	public void execute() {
		if (task != null) {
			task.run();
		}
	}

	@Override
	public void run() {
		if (!isConnected() && !isInitialized()) {
			try {
				configureConnection();
				setInitialized(true);
				listener.start();
				while (isConnected()) {
					execute();
					if (!task.isContinuausly()) {
						break;
					}
				}
			}
			catch (IOException ex) {
				if (!isClosing()) {
					ex.printStackTrace();
				}
			}
			finally {
				listener.interrupt();
				listener = null;
				close();
			}
		}
	}

	public void configureConnection() throws IOException {
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			setConnected(true);
		}
		catch (IOException e) {
			if (!e.getMessage().contains("socket closed")) {
				e.printStackTrace();
			}
			throw new IOException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.connection.SConnection#sendObj(pl.grm.
	 * sockjsonparser.json.JsonSerializable)
	 */
	@Override
	public void sendObj(JsonSerializable obj) throws IOException {
		PacketParser.sendPacket(obj.toString(), socket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.connection.SConnection#getReceivedObjects()
	 */
	@Override
	public JsonSerializable getReceivedObject() {
		return listener.getReceivedObject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#closeConnection()
	 */
	@Override
	public void close() {
		try {
			if (socket != null) {
				socket.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		setConnected(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#getPort()
	 */
	@Override
	public int getPort() {
		return port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#getIs()
	 */
	@Override
	public InputStream getIs() {
		return is;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#getOs()
	 */
	@Override
	public OutputStream getOs() {
		return os;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#getSocket()
	 */
	@Override
	public Socket getSocket() {
		return socket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return connected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.sockjsonparser.server.connection.Connection#setConnected(boolean)
	 */
	@Override
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#isInitialized()
	 */
	@Override
	public boolean isInitialized() {
		return initialized;
	}

	private void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.server.connection.Connection#isClosing()
	 */
	@Override
	public boolean isClosing() {
		return this.closing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.sockjsonparser.server.connection.Connection#setClosing(boolean)
	 */
	@Override
	public void setClosing(boolean closing) {
		this.closing = closing;
	}
}
