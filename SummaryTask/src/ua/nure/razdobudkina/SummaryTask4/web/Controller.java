package ua.nure.razdobudkina.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;
import ua.nure.razdobudkina.SummaryTask4.web.command.Command;
import ua.nure.razdobudkina.SummaryTask4.web.command.CommandContainer;

/**
 * Main servlet controller.
 * 
 * @author A.Razdobudkina
 * 
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2423353715955164816L;

	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.debug("Controller starts GET");
		process(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * method POST & GET of this controller.
	 */
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LOG.debug("Controller starts POST");

		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);

		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);

		String forward = Path.PAGE_ERROR_PAGE;
		boolean usePRG = false;
		try {
			forward = command.execute(request, response);
			usePRG = command.isUsePRG();
		} catch (AppException ex) {
			request.setAttribute("errorMessageException", ex.getMessage());
		}
		LOG.trace("Forward address --> " + forward);

		LOG.debug("Controller POST finished, now go to forward address --> " + forward);

		if (usePRG) {
			response.sendRedirect(forward);
		} else {
			request.getRequestDispatcher(forward).forward(request, response);
		}
	}

}