package pl.grm.atracon.server.commands;

/**
 * CLIENT_ONLINE & DB_ONLINE & PI_ONLINE = 2+20+200=222 CLIENT_ONLINE &
 * DB_ONLINE & PI_OFFLINE = 2+20+100=122
 * 
 * 
 * @author Levvy055
 *
 */
public enum CommandType {
							CLIENT_ONLINE(2),
							CLIENT_OFFLINE(1),
							DB_ONLINE(20),
							DB_OFFLINE(10),
							PI_ONLINE(200),
							PI_OFFLINE(100),
							ALL_ONLINE(222),
							ALL_OFFLINE(111),
							ONE_ONLINE(333),
							NONE_ONLINE(0);

	private int id;

	private CommandType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * @param cType
	 * @return
	 */
	public boolean correct(int hash) {
		if (id == hash) return true;
		if (hash % 10 == id || hash % 10 == id + 1) return true;
		if ((hash % 100) / 10 == id || (hash % 100) / 10 == id + 1) return true;
		if ((hash & 1000) / 100 == id || (hash & 1000) / 100 == id + 1) return true;
		return false;
	}
}