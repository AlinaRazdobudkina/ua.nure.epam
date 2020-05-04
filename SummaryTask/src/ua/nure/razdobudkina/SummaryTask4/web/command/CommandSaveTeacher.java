package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.Role;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandSaveTeacher extends Command {

	private static final long serialVersionUID = 6271527174017565837L;
	private static final Logger LOG = Logger.getLogger(CommandSaveTeacher.class);

	public CommandSaveTeacher() {
		super();
	}
	
	public CommandSaveTeacher(boolean usePRG) {
		super(usePRG);
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		String forward = Path.COMMAND_LIST_TEACHERS;
		
		DBManager manager = DBManager.getInstance();
		
		User user = new User();
		
		user.setRoleId(Role.TEACHER.ordinal());
		user.setActive(true);
		
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setPhone(request.getParameter("phone"));

		User userInDB = manager.findUserByLogin(user.getLogin());
		if(userInDB != null) {
			request.setAttribute("errorMessage", "Such login already exists");
			request.setAttribute("user",user);
			return Path.PAGE_EDIT_TEACHER;
		}
		
		manager.updateUser(user);

		LOG.trace("Add teacher");
	
		LOG.debug("Command finished");
		
		return forward;
	}
}
