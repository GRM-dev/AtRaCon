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

		DeviceType dev = getDevType(devS);

		int id = 0;
		try {

			if (idS.equals("all") || idS.equals("list")) {
				List<?> devs = getDevs(db, dev);
				if (devs != null) {
					for (Object object : devs) {
						System.out.println(object.toString());
					}
					return true;
				} else {
					System.out.println("devs NULL!");
				}
			} else if ((id = Integer.parseInt(idS)) > 0) {
				Object devobj = getDev(db, dev, id);
				if (devobj != null) {
					System.out.println(devobj.toString());
				} else {
					System.out.println("Could not find " + dev.toString() + " device with ID: " + id);
				}
				return true;
			} else {
				System.out.println("Error: " + idS);
			}
		}
		catch (NumberFormatException e) {
			ARCLogger.error(e);
		}
		return false;
	}

	private List<?> getDevs(DBHandler db, DeviceType dev) {
		List<?> devs = null;
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
		return devs;
	}

	private Object getDev(DBHandler db, DeviceType dev, int id) {
		Object devobj = null;
		switch (dev) {
			case ATMEGA :
				devobj = db.getAtmegaDevice(id);
				break;
			case RASPPI :
				devobj = db.getRaspPiDevice(id);
				break;
			case REGISTER :
				devobj = db.getRegister(id);
				break;
			default :
				break;
		}
		return devobj;
	}

	private DeviceType getDevType(String devS) throws CommandException {
		switch (devS) {
			case "pi" :
				return DeviceType.RASPPI;
			case "atmega" :
			case "atm" :
				return DeviceType.ATMEGA;
			case "registry" :
			case "reg" :
				return DeviceType.REGISTER;
			default :
				throw new CommandException("Bad param!" + subCommands);
		}
	}
}
