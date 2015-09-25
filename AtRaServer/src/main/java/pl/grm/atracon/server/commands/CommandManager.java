package pl.grm.atracon.server.commands;

import java.util.*;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.server.ServerMain;
import pl.grm.atracon.server.commands.impl.*;

public class CommandManager {

	private ServerMain serverMain;
	private ArrayList<String> lastCommands;
	private HashMap<Commands, IBaseCommand> baseCommands;

	public CommandManager(ServerMain serverMain) {
		this.serverMain = serverMain;
		lastCommands = new ArrayList<>();
		baseCommands = new HashMap<>();
		add(Commands.EXIT, new CommandExit());
		add(Commands.SHOW, new CommandShow());
	}

	/**
	 * Initialize base commands
	 */
	public void add(Commands commandName, IBaseCommand command) {
		baseCommands.put(commandName, command);
	}

	/**
	 * Executes command
	 * 
	 * @param command
	 *            command to execute
	 * @param args
	 *            arguments of command
	 * @param offset
	 *            if args contains command string set it to false.\n When args
	 *            are just offset than true.
	 * @param cType
	 *            command invoked by server or client
	 * @param connection
	 *            connection on which commend should be executed
	 * @return true if correctly executed
	 * @throws Exception
	 */
	public boolean executeCommand(Commands command, String args, boolean offset) throws Exception {
		if (!offset) {
			args = Commands.getOffset(args);
		}
		return executeCommand(command, args);
	}

	/**
	 * Executes command
	 * 
	 * @param command
	 *            command to execute
	 * @param args
	 *            arguments of command
	 * @param cType
	 *            command invoked by server or client
	 * @param connection
	 *            connection on which commend should be executed
	 * @return true if correctly executed
	 * @throws Exception
	 */
	public synchronized boolean executeCommand(Commands command, String args) throws Exception {
		System.out.println(command);
		if (baseCommands.containsKey(command)) {
			ARCLogger.info("Executing " + command.toString() + " command with params: " + args);
			IBaseCommand cmm = baseCommands.get(command);
			return cmm.execute(command, args, serverMain);
		} else {
			throw new CommandException("There is no " + command.toString() + " implementation of command");
		}
	}

	public void addCommandToList(String input) {
		if (lastCommands.size() == 100) {
			lastCommands.remove(0);
		}
		lastCommands.add(input);
	}

	public String getLastCommand() {
		if (!lastCommands.isEmpty()) { return lastCommands.get(lastCommands.size() - 1); }
		return "";
	}

	public boolean wasExecuted(String input) {
		if (input != null && input != "" && lastCommands.contains(input)) { return true; }
		return false;
	}

	public String getPreviousCommand(String input) {
		if (wasExecuted(input)) {
			int i = lastCommands.indexOf(input);
			if (i != 0) { return lastCommands.get(i - 1); }
		}
		return "";
	}

	public boolean hasNextCommand(String input) {
		if (wasExecuted(input) && lastCommands.indexOf(input) < lastCommands.size() - 1) { return true; }
		return false;
	}

	public String getNextCommand(String input) {
		if (hasNextCommand(input)) {
			int i = lastCommands.indexOf(input);
			return lastCommands.get(i + 1);
		}
		return "";
	}
}
