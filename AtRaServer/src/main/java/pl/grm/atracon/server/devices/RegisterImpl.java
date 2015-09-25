/**
 * 
 */
package pl.grm.atracon.server.devices;

import javax.persistence.*;

import org.json.JSONObject;

import pl.grm.atracon.lib.devices.*;
import pl.grm.atracon.lib.misc.*;
import pl.grm.sockjsonparser.json.JsonSerializable;

/**
 * @author Levvy055
 *
 */
@Entity
@Table(name = "registry")
public class RegisterImpl implements JsonSerializable, Register {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@JoinColumn(name = "atm_id", nullable = false)
	@ManyToOne
	private AtmegaImpl atmega;
	@Column(name = "reg_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private RegType regType;
	@Column(name = "reg_addr", nullable = false)
	private int regAddress;
	@Column(name = "reg_value")
	private String regValue;
	@Column(name = "reg_value_type")
	@Enumerated(EnumType.STRING)
	private ValueType regValueType;

	public RegisterImpl() {}

	public RegisterImpl(int id, AtmegaImpl atmega, RegType regType, int regAddress, String regValue,
			ValueType regValueType) {
		super();
		this.id = id;
		this.atmega = atmega;
		this.regType = regType;
		this.regAddress = regAddress;
		this.regValue = regValue;
		this.regValueType = regValueType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#getId()
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
	 * @see pl.grm.atracon.server.devices.Register#getAtmega()
	 */
	@Override
	public Atmega getAtmega() {
		return atmega;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#setAtmega(pl.grm.atracon.lib.
	 * devices.Atmega)
	 */
	@Override
	public void setAtmega(Atmega atmega) {
		this.atmega = (AtmegaImpl) atmega;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#getRegType()
	 */
	@Override
	public RegType getRegType() {
		return regType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.atracon.server.devices.Register#setRegType(pl.grm.atracon.lib.misc
	 * .RegType)
	 */
	@Override
	public void setRegType(RegType regType) {
		this.regType = regType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#getRegAddress()
	 */
	@Override
	public int getRegAddress() {
		return regAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#setRegAddress(int)
	 */
	@Override
	public void setRegAddress(int regAddress) {
		this.regAddress = regAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#getRegValue()
	 */
	@Override
	public String getRegValue() {
		return regValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#setRegValue(java.lang.String)
	 */
	@Override
	public void setRegValue(String regValue) {
		this.regValue = regValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.server.devices.Register#getRegValueType()
	 */
	@Override
	public ValueType getRegValueType() {
		return regValueType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.atracon.server.devices.Register#setRegValueType(pl.grm.atracon.lib
	 * .misc.ValueType)
	 */
	@Override
	public void setRegValueType(ValueType regValueType) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.json.JsonSerializable#getAsJsonObject()
	 */
	@Override
	public JSONObject getAsJsonObject() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.sockjsonparser.json.JsonSerializable#toJSONString()
	 */
	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

}
