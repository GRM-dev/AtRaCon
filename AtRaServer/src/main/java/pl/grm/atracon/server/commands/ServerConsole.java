package pl.grm.atracon.server.commands;

import java.io.*;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.server.ServerMain;

public class ServerConsole implements Runnable {

	private ServerMain serverMain;
	private boolean stop;
	private BufferedReader br;

	public ServerConsole(ServerMain serverMain) {
		this.serverMain = serverMain;
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void run() {
		Thread.currentThread().setName("ARC Server Console Thread");
		CommandManager cm = serverMain.getCM();
		String command = "";
		Commands cmd = null;
		boolean executed = false;
		do {
			if (cmd == Commands.EXIT && executed) {
				synchronized (cmd) {
					try {
						cmd.wait();
					}
					catch (InterruptedException e) {}
				}
			} else {
				command = readCommand();
			}
			cmd = Commands.getCommand(command);
			if (cmd == Commands.NONE) {
				ARCLogger.info("Bad command");
			} else {
				try {
					executed = cm.executeCommand(cmd, command, false);
				}
				catch (Exception e) {
					ARCLogger.error(e);
				}
			}
		}
		while (!isStop());
	}

	public String readCommand() {
		String command = "";
		try {
			command = br.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return command;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
