/**
 * 
 */
package pl.grm.atracon.server.commands;

/**
 * @author Levvy055
 *
 */
public class CommandException extends Exception {

	private static final long serialVersionUID = 1L;

	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandException(String message) {
		super(message);
	}

	public CommandException(Throwable cause) {
		super(cause);
	}
}
