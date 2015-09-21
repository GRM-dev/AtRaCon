/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "devices_pi")
public class RaspPi implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "description")
	private String desc;
	@Column(name = "last_active")
	private Timestamp lastActive;
	@Column(name = "activated")
	private boolean activated;

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
		this.activated = activated;
	}
}
