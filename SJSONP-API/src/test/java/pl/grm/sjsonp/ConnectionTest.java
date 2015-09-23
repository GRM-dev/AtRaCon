/**
 * 
 */
package pl.grm.sjsonp;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pl.grm.sockjsonparser.api.ConnectionFactory;
import pl.grm.sockjsonparser.connection.ConnectioSide;

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
		Runnable task = () -> {

		};
		Thread thread = new Thread(() -> {
			try {
				ConnectionFactory.establishConnection("127.0.0.1", 33221, task, ConnectioSide.SERVER);
			}
			catch (IOException e) {
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
