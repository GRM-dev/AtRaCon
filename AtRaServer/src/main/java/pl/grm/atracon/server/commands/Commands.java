package pl.grm.atracon.server.commands;

public enum Commands {
						NONE("", CommandType.CLIENT_OFFLINE),
						EXIT("exit"),
						STOP("stop", CommandType.ONE_ONLINE),
						DISCONNECT("disconnect", CommandType.ONE_ONLINE),
						START("start", CommandType.ALL_OFFLINE),
						SHOW("show"),
						STATUS("status");

	private String command;
	private CommandType[] types;

	private Commands(String name, CommandType... types) {
		this.command = name;
		this.types = types;
	}

	public static Commands getCommand(String commS) {
		if (commS == null || commS == "") { return NONE; }
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

	public CommandType[] getTypes() {
		return types;
	}

	public int getTypesHash() {
		int hash = 0;
		if (types != null) {
			for (CommandType commandType : types) {
				hash += commandType.getId();
			}
		}
		return hash;
	}
}
