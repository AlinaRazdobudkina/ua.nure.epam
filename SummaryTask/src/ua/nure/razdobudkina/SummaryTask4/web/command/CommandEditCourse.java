package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.Role;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Category;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandEditCourse extends Command {

	
	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandEditCourse.class);

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		DBManager manager = DBManager.getInstance();
		
		String courseId = request.getParameter("courseId");
		Course course = null;
		if(courseId == null) {
			course = new Course();
			request.setAttribute("preCommand", "add");
		}else {
			course = manager.findCourseById(Integer.parseInt(courseId));
			request.setAttribute("preCommand", "update");
			LOG.trace("Found in DB course --> " + course);
		}
		
		List<Category> listCategories = manager.allCategories();
		
		LOG.trace("Found in DB list of categories --> " + listCategories);
		
		List<User> listTecheres = manager.allUsersForRole(Role.TEACHER.ordinal());
		
		LOG.trace("Found in DB list of teacheres --> " + listTecheres);
			
		request.setAttribute("course", course);
		request.setAttribute("listCategories", listCategories);
		request.setAttribute("listTheacheres", listTecheres);
		
		LOG.trace("Set the request attribute: course --> " + course);
		LOG.trace("Set the request attribute: listCategories --> " + listCategories);
		LOG.trace("Set the request attribute: listTheacheres --> " + listTecheres);
		
		String forward = Path.PAGE_EDIT_COURSE;

		LOG.debug("Command finished");
		return forward;
	}

	

}
