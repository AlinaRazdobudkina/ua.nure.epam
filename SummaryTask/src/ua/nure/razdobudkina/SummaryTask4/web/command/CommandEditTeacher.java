package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandEditTeacher extends Command {

	
	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandEditTeacher.class);

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");

		 User user = new User();
		 		
		request.setAttribute("user", user);

		
		LOG.trace("Set the request attribute: user --> " + user);

		String forward = Path.PAGE_EDIT_TEACHER;

		LOG.debug("Command finished");
		return forward;
	}

	

}
