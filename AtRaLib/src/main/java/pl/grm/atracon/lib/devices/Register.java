/**
 * 
 */
package pl.grm.atracon.lib.devices;

import pl.grm.atracon.lib.misc.*;

/**
 * @author Levvy055
 *
 */
public interface Register {

	int getId();

	Atmega getAtmega();

	void setAtmega(Atmega atmega);

	RegType getRegType();

	void setRegType(RegType regType);

	int getRegAddress();

	void setRegAddress(int regAddress);

	String getRegValue();

	void setRegValue(String regValue);

	ValueType getRegValueType();

	void setRegValueType(ValueType regValueType);

}