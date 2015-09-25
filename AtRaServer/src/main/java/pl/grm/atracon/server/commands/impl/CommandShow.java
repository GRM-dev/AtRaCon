/**
 * 
 */
package pl.grm.atracon.server.commands.impl;

import java.util.List;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.devices.DeviceType;
import pl.grm.atracon.lib.rmi.DBHandler;
import pl.grm.atracon.server.ServerMain;
import pl.grm.atracon.server.commands.*;

/**
 * @author Levvy055
 *
 */
public class CommandShow implements IBaseCommand {

	private String subCommands = "\nAvailable params:\n\t- device[pi/atmega/registry] [(device_id)/'all/list']";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.atracon.server.commands.IBaseCommand#execute(pl.grm.atracon.server
	 * .commands.Commands, java.lang.String, pl.grm.atracon.server.ServerMain)
	 */
	@Override
	public boolean execute(Commands command, String args, ServerMain serverMain) throws Exception {
		if (args == null || args.equals("")) { throw new CommandException(
				"To execute command " + command.getCommandString() + " you need to pass arguments." + subCommands); }

		DBHandler db = serverMain.getDatabase();
		String[] argsS = args.trim().split("\\s+");
		if (argsS.length < 2) { throw new CommandException("Too less parameters. " + argsS.length + subCommands); }
		if (argsS.length > 2) { throw new CommandException("Too much parameters. " + argsS.length + subCommands); }
		String devS = argsS[0].trim();
		String idS = argsS[1].trim();

		DeviceType dev;
		switch (devS) {
			case "pi" :
				dev = DeviceType.RASPPI;
				break;
			case "atmega" :
			case "atm" :
				dev = DeviceType.ATMEGA;
				break;
			case "registry" :
			case "reg" :
				dev = DeviceType.REGISTER;
				break;
			default :
				throw new CommandException("Bad param!" + subCommands);
		}

		int id = 0;
		try {
			List<?> devs = null;
			if (idS.equals("all") || idS.equals("list")) {
				switch (dev) {
					case ATMEGA :
						devs = db.getAtmegaDevices();
						break;
					case RASPPI :
						devs = db.getRaspPiDevices();
						break;
					case REGISTER :
						devs = db.getAllRegistry();
						break;
					default :
						break;
				}
			} else if ((id = Integer.parseInt(idS)) > 0) {
				System.out.println("ID: " + id);
			} else {
				System.out.println("Error: " + idS);
			}
			if (devs != null) {
				for (Object object : devs) {
					System.out.println(object.toString());
				}
			} else {
				System.out.println("devs NULL!");
			}
		}
		catch (NumberFormatException e) {
			ARCLogger.error(e);
		}
		return false;
	}
}
