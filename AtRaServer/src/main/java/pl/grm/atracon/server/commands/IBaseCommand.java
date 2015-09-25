/**
 * 
 */
package pl.grm.atracon.server.commands;

import pl.grm.atracon.server.ServerMain;

/**
 * @author Levvy055
 *
 */
public interface IBaseCommand {

	public boolean execute(Commands command, String args, ServerMain serverMain) throws Exception;
}
