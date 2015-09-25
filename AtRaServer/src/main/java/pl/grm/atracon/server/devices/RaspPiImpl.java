/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;

import pl.grm.atracon.lib.devices.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "devices_pi")
public class RaspPiImpl implements Serializable, RaspPi {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "f_name", unique = true, nullable = false)
	private String name;
	@Column(name = "f_address", nullable = false)
	private String address;
	@Column(name = "f_description")
	private String desc;
	@Column(name = "f_last_active")
	private Timestamp lastActive;
	@Column(name = "f_activated", nullable = false)
	private boolean activated;
	@OneToMany(mappedBy = "raspPi", targetEntity = AtmegaImpl.class, fetch = FetchType.EAGER)
	private Set<Atmega> atmDevices;

	public RaspPiImpl() {}

	public RaspPiImpl(String name, String address, String desc, Timestamp lastActive, boolean activated) {
		this.name = name;
		this.address = address;
		this.desc = desc;
		this.lastActive = lastActive;
		this.activated = activated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#getId()
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
	 * @see pl.grm.atracon.server.devices.RaspPi#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#getAddress()
	 */
	@Override
	public String getAddress() {
		return address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#setAddress(java.lang.String)
	 */
	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#getDesc()
	 */
	@Override
	public String getDesc() {
		return desc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#setDesc(java.lang.String)
	 */
	@Override
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#getLastActive()
	 */
	@Override
	public Timestamp getLastActive() {
		return lastActive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.atracon.server.devices.RaspPi#setLastActive(java.sql.Timestamp)
	 */
	@Override
	public void setLastActive(Timestamp lastActive) {
		this.lastActive = lastActive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#isActivated()
	 */
	@Override
	public boolean isActivated() {
		return activated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#setActivated(boolean)
	 */
	@Override
	public void setActivated(boolean activated) {
		if (activated == false && this.activated == true) {
			this.setLastActive(Timestamp.valueOf(LocalDateTime.now()));
		}
		this.activated = activated;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#getAtmDevices()
	 */
	@Override
	public Set<Atmega> getAtmDevices() {
		return atmDevices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.RaspPi#setAtmDevices(java.util.Set)
	 */
	@Override
	public void setAtmDevices(Set<Atmega> atmDevices) {
		this.atmDevices = atmDevices;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RaspPi [id=" + id);
		builder.append(", name='" + name + "'");
		builder.append(", address=" + address);
		builder.append(", desc=");
		if (desc == null)
			builder.append("NN");
		else builder.append(desc);
		builder.append(", lastActive=");
		if (lastActive == null)
			builder.append("Never");
		else builder.append(new Date(lastActive.getTime()).toString());
		builder.append(", activated=" + activated);
		builder.append(", contain " + atmDevices.size() + " Atmega devices");
		builder.append("]");
		return builder.toString();
	}
}
