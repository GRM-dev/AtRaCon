package pl.grm.atracon.lib.rmi;

import java.rmi.*;

import pl.grm.atracon.lib.devices.RaspPi;

public interface IAtRaConRemoteController extends Remote {

	public RaspPi getPi() throws RemoteException;

}
