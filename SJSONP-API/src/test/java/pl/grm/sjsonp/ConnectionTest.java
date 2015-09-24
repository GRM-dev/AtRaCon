/**
 * 
 */
package pl.grm.sjsonp;

import static org.junit.Assert.fail;

import org.junit.Test;

import pl.grm.sockjsonparser.api.*;
import pl.grm.sockjsonparser.connection.*;

/**
 * @author Levvy055
 *
 */
public class ConnectionTest {

	@Test
	public void testClientSocketCreation() {
		// TODO: to implement
		// fail("Not yet implemented");
	}

	@Test
	public void testServerSocketCreation() {
		ConnectionTask task = new ConnectionTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}

			@Override
			public void setSConnection(SConnection conn) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isContinuausly() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		Thread thread = new Thread(() -> {
			try {
				ConnectionFactory.establishConnection("127.0.0.1", 33221, task, ConnectioSide.SERVER);
			}
			catch (Exception e) {
				e.printStackTrace();
				fail("Got exception: " + e.getMessage());
			}
		});
		thread.start();
		try {
			Thread.sleep(2000l);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread.interrupt();
	}

	@Test
	public void testConnectionEstablishment() {
		// TODO: to implement
		// fail("Not yet implemented");
	}

}
