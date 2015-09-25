/**
 * 
 */
package pl.grm.atracon.lib.devices;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Levvy055
 *
 */
public interface RaspPi {

	int getId();

	String getName();

	void setName(String name);

	String getAddress();

	void setAddress(String address);

	String getDesc();

	void setDesc(String desc);

	Timestamp getLastActive();

	void setLastActive(Timestamp lastActive);

	boolean isActivated();

	void setActivated(boolean activated);

	Set<Atmega> getAtmDevices();

	void setAtmDevices(Set<Atmega> atmDevices);

}