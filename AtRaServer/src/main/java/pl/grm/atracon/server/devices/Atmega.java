/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "devices_atm")
public class Atmega implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@JoinColumn(name = "rasppi_id")
	@ManyToOne
	private RaspPi raspPi;
	@Column(name = "f_name", unique = true, nullable = false)
	private String name;
	@Column(name = "f_port", unique = true, nullable = false)
	private int port;
	@OneToMany(mappedBy = "atmega", targetEntity = Register.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapKey(name = "regAddress")
	private Map<Integer, Register> registry;

	public Atmega() {}

	public Atmega(int id, RaspPi raspPi, String name, int port, Map<Integer, Register> registry) {
		this.id = id;
		this.raspPi = raspPi;
		this.name = name;
		this.port = port;
		this.registry = registry;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RaspPi getRaspPi() {
		return raspPi;
	}

	public void setRaspPi(RaspPi raspPi) {
		this.raspPi = raspPi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Map<Integer, Register> getRegistry() {
		return registry;
	}

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
