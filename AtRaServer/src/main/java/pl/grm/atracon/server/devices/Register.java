/**
 * 
 */
package pl.grm.atracon.server.devices;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "registry")
public class Register implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@JoinColumn(name = "atm_id", nullable = false)
	@ManyToOne
	private Atmega atmega;
	@Column(name = "reg_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private RegType regType;
	@Column(name = "reg_addr", nullable = false)
	private int regAddress;
	@Column(name = "reg_value")
	private String regValue;
	@Column(name = "reg_value_type")
	@Enumerated(EnumType.STRING)
	private RegValueType regValueType;

	public Register() {}

	public Register(int id, Atmega atmega, RegType regType, int regAddress, String regValue,
			RegValueType regValueType) {
		super();
		this.id = id;
		this.atmega = atmega;
		this.regType = regType;
		this.regAddress = regAddress;
		this.regValue = regValue;
		this.regValueType = regValueType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Atmega getAtmega() {
		return atmega;
	}

	public void setAtmega(Atmega atmega) {
		this.atmega = atmega;
	}

	public RegType getRegType() {
		return regType;
	}

	public void setRegType(RegType regType) {
		this.regType = regType;
	}

	public int getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(int regAddress) {
		this.regAddress = regAddress;
	}

	public String getRegValue() {
		return regValue;
	}

	public void setRegValue(String regValue) {
		this.regValue = regValue;
	}

	public RegValueType getRegValueType() {
		return regValueType;
	}

	public void setRegValueType(RegValueType regValueType) {
		this.regValueType = regValueType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Registry [id=" + id);
		builder.append(", atmega='" + atmega.getName() + "'");
		builder.append(", regType=" + regType);
		builder.append(", regAddress=" + regAddress);
		builder.append(", regValue=");
		if (regValue == null)
			builder.append("NN");
		else builder.append(regValue);
		builder.append(", regValueType=");
		if (regValue == null)
			builder.append("NN");
		else builder.append(regValueType);
		builder.append("]");
		return builder.toString();
	}

}
