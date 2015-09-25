/**
 * 
 */
package pl.grm.atracon.server.commands.impl;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.server.ServerMain;
import pl.grm.atracon.server.commands.*;

/**
 * @author Levvy055
 *
 */
public class CommandExit implements IBaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.grm.atracon.server.commands.IBaseCommand#execute(pl.grm.atracon.server
	 * .commands.Commands, java.lang.String)
	 */
	@Override
	public boolean execute(Commands command, String args, ServerMain serverMain) throws Exception {
		try {
			if (args != null) {
				args = args.trim();
				if (!args.equals("")) {}
				final long time;
				if (args.equals("now")) {
					time = 0;
				} else {
					time = Long.parseLong(args);
				}
				Thread task = new Thread(new Runnable() {

					final long startTime = System.currentTimeMillis();
					final long endTime = startTime + time;

					@Override
					public void run() {
						long r = 10000;
						while (true) {
							r = endTime - System.currentTimeMillis();
							if (r <= 0) {
								ServerMain.exit();
								break;
							}
						}
					}
				});
				task.setName("Exit Command Sheduler");
				task.start();
				return true;
			}
		}
		catch (NumberFormatException e)

		{
			ARCLogger.warn(e.getMessage());
		}
		ARCLogger.info("You should provide milisecunds time or 'now' as param.");
		return false;
	}

}
