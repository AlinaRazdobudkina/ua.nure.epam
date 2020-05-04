package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandChangeLanguage extends Command {

	
	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandChangeLanguage.class);

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		String language = request.getParameter("language");
		
		HttpSession session = request.getSession();
		
		session.setAttribute("language", language);
		
		String forward = Path.PAGE_PROFILE;

		LOG.debug("Command finished");
		return forward;
	}

	

}
