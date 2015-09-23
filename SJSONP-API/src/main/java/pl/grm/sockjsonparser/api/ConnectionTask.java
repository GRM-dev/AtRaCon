/**
 * 
 */
package pl.grm.sockjsonparser.api;

import pl.grm.sockjsonparser.connection.SConnection;

/**
 * @author Levvy055
 *
 */
public interface ConnectionTask extends Runnable {

	public void setSConnection(SConnection conn);

	public boolean isContinuausly();
}
