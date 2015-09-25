package pl.grm.sockjsonparser.json;

import java.io.Serializable;

import org.json.JSONObject;

public interface JsonSerializable extends Serializable {

	public abstract JSONObject getAsJsonObject();

	public String toJSONString();
}
