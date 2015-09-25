/**
 * 
 */
package pl.grm.atracon.lib.rmi;

import java.util.List;

import pl.grm.atracon.lib.devices.*;

/**
 * @author Levvy055
 *
 */
public interface DBHandler {

	public List<RaspPi> getRaspPiDevices();

	public RaspPi getRaspPiDevice(int ID);

	public List<Atmega> getAtmegaDevices();

	public List<Atmega> getAtmegaDevices(int raspPiId);

	public Atmega getAtmegaDevice(int raspPiId, int atmegaPort);

	public Atmega getAtmegaDevice(int atmegaId);

	public List<Register> getAllRegistry();

	public Register getRegister(int regId);

}
