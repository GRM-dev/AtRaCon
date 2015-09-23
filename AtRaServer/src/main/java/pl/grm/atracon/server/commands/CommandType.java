package pl.grm.atracon.server.commands;

public enum CommandType {
							OFFLINE(0),
							ONLINE(1);

	private int id;

	private CommandType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}