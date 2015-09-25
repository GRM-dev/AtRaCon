/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

import pl.grm.atracon.lib.devices.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "devices_atm")
public class AtmegaImpl implements Serializable, Atmega {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@JoinColumn(name = "rasppi_id")
	@ManyToOne
	private RaspPiImpl raspPi;
	@Column(name = "f_name", unique = true, nullable = false)
	private String name;
	@Column(name = "f_port", unique = true, nullable = false)
	private int port;
	@OneToMany(mappedBy = "atmega", targetEntity = RegisterImpl.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapKey(name = "regAddress")
	private Map<Integer, Register> registry;

	public AtmegaImpl() {}

	public AtmegaImpl(int id, RaspPiImpl raspPi, String name, int port, Map<Integer, Register> registry) {
		this.id = id;
		this.raspPi = raspPi;
		this.name = name;
		this.port = port;
		this.registry = registry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#getRaspPi()
	 */
	@Override
	public RaspPi getRaspPi() {
		return raspPi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.atracon.server.devices.Atmega#setRaspPi(pl.grm.atracon.lib.devices
	 * .RaspPi)
	 */
	@Override
	public void setRaspPi(RaspPi raspPi) {
		this.raspPi = (RaspPiImpl) raspPi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#getPort()
	 */
	@Override
	public int getPort() {
		return port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#setPort(int)
	 */
	@Override
	public void setPort(int port) {
		this.port = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#getRegistry()
	 */
	@Override
	public Map<Integer, Register> getRegistry() {
		return registry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Atmega#setRegistry(java.util.Map)
	 */
	@Override
	public void setRegistry(Map<Integer, Register> registry) {
		this.registry = registry;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Atmega [id=" + id);
		builder.append(", name='" + name + "'");
		builder.append(", port=" + port);
		builder.append(", belongs to=" + raspPi.getName());
		builder.append(", contain " + registry.size() + " registry addresses");
		builder.append("]");
		return builder.toString();
	}
}
