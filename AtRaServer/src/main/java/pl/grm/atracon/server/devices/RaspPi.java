/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "devices_pi")
public class RaspPi implements Serializable {

	@Id
	@GeneratedValue(generator = "increment")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "address")
	private String address;
	@Column(name = "desc")
	private String desc;
	@Column(name = "last_active")
	private Date lastActive;
	@Column(name = "activated")
	private boolean activated;

	public RaspPi() {}

	public RaspPi(String name, String address, String desc, Date lastActive, boolean activated) {
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

	public Date getLastActive() {
		return lastActive;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
