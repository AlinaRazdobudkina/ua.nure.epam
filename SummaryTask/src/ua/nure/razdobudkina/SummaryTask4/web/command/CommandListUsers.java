package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.Role;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandListUsers extends Command {

	
	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandListUsers.class);

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		DBManager manager = DBManager.getInstance();
		
		String preCommand = request.getParameter("preCommand");
		List<User> listUsers = null;
		
		if("teachers".equals(preCommand)) {
			listUsers = manager.allUsersForRole(Role.TEACHER.ordinal());
			LOG.trace("Found in DB: list students --> " + listUsers);
		}else {
			listUsers = manager.allUsersForRole(Role.STUDENT.ordinal());
			LOG.trace("Found in DB: list teachers --> " + listUsers);
		}
		
		request.setAttribute("listUsers", listUsers);
		request.setAttribute("preCommand", preCommand);
		LOG.trace("Set the request attribute: listUsers --> " + listUsers);
		
		String forward = Path.PAGE_LIST_USERS;

		LOG.debug("Command finished");
		return forward;
	}

	

}
