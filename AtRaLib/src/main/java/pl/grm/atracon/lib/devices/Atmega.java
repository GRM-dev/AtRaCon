/**
 * 
 */
package pl.grm.atracon.lib.devices;

import java.util.Map;

/**
 * @author Levvy055
 *
 */
public interface Atmega {

	int getId();

	RaspPi getRaspPi();

	void setRaspPi(RaspPi raspPi);

	String getName();

	void setName(String name);

	int getPort();

	void setPort(int port);

	Map<Integer, Register> getRegistry();

	void setRegistry(Map<Integer, Register> registry);

}