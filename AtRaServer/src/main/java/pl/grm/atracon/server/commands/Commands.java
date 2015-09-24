package pl.grm.atracon.server.commands;

public enum Commands {
						NONE("", CommandType.OFFLINE),
						CLOSE("close", CommandType.OFFLINE),
						STOP("stop", CommandType.ONLINE),
						CONNECTION("connection", CommandType.ONLINE),
						START("start", CommandType.OFFLINE),
						JSON("json", CommandType.OFFLINE),
						STATUS("status", CommandType.OFFLINE);

	private String command;
	private CommandType type;

	private Commands(String name, CommandType type) {
		this.command = name;
		this.type = type;
	}

	public static Commands getCommand(String commS) {
		for (Commands commT : Commands.values()) {
			if (commT == NONE) {
				continue;
			}
			commS = commS.toLowerCase() + " ";
			String commandS = commT.getCommandString().toLowerCase() + " ";
			if (commS.startsWith(commandS)) { return commT; }
		}
		return NONE;
	}

	public static String getOffset(String commS) {
		Commands comm = getCommand(commS);
		if (comm == NONE) { return ""; }
		return commS.replace(comm.getCommandString(), "");
	}

	public String getCommandString() {
		return "!" + command;
	}

	public CommandType getType() {
		return type;
	}
}
