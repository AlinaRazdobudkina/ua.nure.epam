package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author A.Razdobudkina
 * 
 */
public abstract class Command implements Serializable {
	private static final long serialVersionUID = 8879403039606311780L;

	private boolean usePRG;

	public Command() {
		super();
		this.usePRG = false;
	}

	public Command(boolean usePRG) {
		super();
		this.usePRG = usePRG;
	}
	

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */
	public abstract String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
	
	public boolean isUsePRG() {
		return usePRG;
	}

	public void setUsePRG(boolean usePRG) {
		this.usePRG = usePRG;
	}
}