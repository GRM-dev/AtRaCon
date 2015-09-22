/**
 * 
 */
package pl.grm.atracon.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import pl.grm.atracon.lib.rmi.IAtRaConRemoteController;

/**
 * @author Levvy055
 *
 */
public class AtRaConServer extends UnicastRemoteObject implements IAtRaConRemoteController {

	private static final long serialVersionUID = 1L;

	/**
	 * @throws RemoteException
	 */
	public AtRaConServer() throws RemoteException {
		super();
	}

}
