/**
 * 
 */
package pl.grm.atracon.server.sockets;

import pl.grm.sockjsonparser.api.ConnectionTask;
import pl.grm.sockjsonparser.connection.SConnection;

/**
 * @author Levvy055
 *
 */
public class ServerTask implements ConnectionTask {

	private SConnection conn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.api.ConnectionTask#setSConnection(pl.grm.
	 * sockjsonparser.connection.SConnection)
	 */
	@Override
	public void setSConnection(SConnection conn) {
		this.conn = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.api.ConnectionTask#isContinuausly()
	 */
	@Override
	public boolean isContinuausly() {
		return true;
	}
}
