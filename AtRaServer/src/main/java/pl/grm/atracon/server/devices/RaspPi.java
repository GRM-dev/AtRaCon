/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "devices_pi")
public class RaspPi implements Serializable {

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
	@OneToMany(mappedBy = "raspPi", targetEntity = Atmega.class, fetch = FetchType.EAGER)
	private Set<Atmega> atmDevices;

	public RaspPi() {}

	public RaspPi(String name, String address, String desc, Timestamp lastActive, boolean activated) {
		this.name = name;
		this.address = address;
		this.desc = desc;
		this.lastActive = lastActive;
		this.activated = activated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Timestamp getLastActive() {
		return lastActive;
	}

	public void setLastActive(Timestamp lastActive) {
		this.lastActive = lastActive;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		if (activated == false && this.activated == true) {
			this.setLastActive(Timestamp.valueOf(LocalDateTime.now()));
		}
		this.activated = activated;

	}

	public Set<Atmega> getAtmDevices() {
		return atmDevices;
	}

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
