package pl.grm.atracon.server.commands;

import java.io.*;

import pl.grm.atracon.server.ServerMain;

public class ServerConsole implements Runnable {

	private ServerMain serverMain;
	private boolean stop;

	public ServerConsole(ServerMain serverMain) {
		this.serverMain = serverMain;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("ARC Server Console Thread");
		String command = "";
		do {
			command = readCommand();
			serverMain.getCM().executeCommand(Commands.getCommand(command), command, false);
		}
		while (!stop);
		ServerMain.stop();
	}

	public String readCommand() {
		String command = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			command = br.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return command;
	}
}
