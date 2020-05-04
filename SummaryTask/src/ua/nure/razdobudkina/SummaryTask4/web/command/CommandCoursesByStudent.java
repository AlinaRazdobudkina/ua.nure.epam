package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Journal;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandCoursesByStudent extends Command {

	
	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandCoursesByStudent.class);

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		String status = request.getParameter("status");
		
		DBManager manager = DBManager.getInstance();
		List<Journal> listCourse = manager.allCoursesForStudent(user.getId());
		LOG.trace("Found in DB: list courses for student --> " + listCourse);
		
		request.setAttribute("listCoursesStudent", listCourse);
		LOG.trace("Set the session attribute: listCourses --> " + listCourse);
		
		if (status != null) {
			request.setAttribute("listCoursesStudent", listCourse.stream()
					  .filter(s -> s.getStatus() == Integer.parseInt(status))
					  .collect(Collectors.toList()));
		}
		
		String forward = Path.PAGE_ALL_COURSES_FOR_STUDENT;

		LOG.debug("Command finished");
		return forward;
	}

	

}
