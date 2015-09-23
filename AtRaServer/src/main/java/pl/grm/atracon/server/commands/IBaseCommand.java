/**
 * 
 */
package pl.grm.atracon.server.commands;

/**
 * @author Levvy055
 *
 */
public interface IBaseCommand {

	public boolean execute(Commands command, String args, CommandType cType) throws Exception;
}
