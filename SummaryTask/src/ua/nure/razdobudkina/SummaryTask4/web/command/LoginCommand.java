package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.DBManagerForTest;
import ua.nure.razdobudkina.SummaryTask4.db.Role;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

/**
 * Login command.
 * 
 * @author A.Razdobudkina
 * 
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();
		
		HttpSession session = request.getSession();
		
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			throw new AppException("Login/password cannot be empty");
		}

		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			request.setAttribute("errorMessage","Cannot find user with such login/password");
			return Path.PAGE_LOGIN;
		}

		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		List<Course> listCourse = manager.allCourses();
		LOG.trace("Found in DB: list courses --> " + listCourse);

		session.setAttribute("listCourses", listCourse);
		LOG.trace("Set the session attribute: listCourses --> " + listCourse);

		String forward = Path.PAGE_ERROR_PAGE;
		
		if(!user.isActive()) {
			request.setAttribute("errorMessage","We are sorry. Your account is not active");
			return Path.PAGE_LOGIN;
		}

		if (userRole == Role.ADMIN || userRole == Role.TEACHER || userRole == Role.STUDENT) {
			forward = Path.COMMAND_LIST_COURSES;
		}

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

}