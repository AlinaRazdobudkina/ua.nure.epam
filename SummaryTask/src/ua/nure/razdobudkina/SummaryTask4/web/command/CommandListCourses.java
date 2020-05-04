package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.Role;
import ua.nure.razdobudkina.SummaryTask4.db.bean.CourseExtend;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Category;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandListCourses extends Command {

	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandListCourses.class);

	private static Comparator<CourseExtend> compareByNameAsc = (CourseExtend course1, CourseExtend course2) -> {

		if (course1.getName().compareTo(course2.getName()) > 1)
			return 1;
		else
			return -1;
	};
	
	private static Comparator<CourseExtend> compareByNameDesc = (CourseExtend course1, CourseExtend course2) -> {

		if (course1.getName().compareTo(course2.getName()) < 0)
			return 1;
		else
			return -1;
	};
	
	private static Comparator<CourseExtend> compareByLength = (CourseExtend course1, CourseExtend course2) -> {

		if (course1.getLength() > course2.getLength())
			return 1;
		else
			return -1;
	};

	private static Comparator<CourseExtend> compareByCountStudents = (CourseExtend course1, CourseExtend course2) -> {

		if (course1.getCountOdStudents() > course2.getCountOdStudents())
			return 1;
		else
			return -1;
	};
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();
		
		String searchTopic = request.getParameter("searchTopic");
		
		String searchTeacher = request.getParameter("searchTeacher");
		
		List<CourseExtend> listCourse = null;
		listCourse = manager.allCoursesWithNames();
		
		request.setAttribute("listCourses", listCourse);
		
		LOG.trace("Set the request attribute: listCourses --> " + listCourse);
		
		if (searchTopic != null) {
			request.setAttribute("listCourses", listCourse.stream()
					  .filter(s -> s.getCategoryId() == Integer.parseInt(searchTopic))
					  .collect(Collectors.toList()));
		}
		
		if (searchTeacher != null) {
			request.setAttribute("listCourses", listCourse.stream()
					  .filter(s -> s.getTeacherId() == Integer.parseInt(searchTeacher))
					  .collect(Collectors.toList()));
		}
		
		
		LOG.trace("Found in DB: list courses --> " + listCourse);

		List<Category> listCategories = manager.allCategories();
		
		LOG.trace("Found in DB list of categories --> " + listCategories);
		
		List<User> listTeachers = manager.allUsersForRole(Role.TEACHER.ordinal());
		
		LOG.trace("Found in DB list of teacheres --> " + listTeachers);

		request.setAttribute("listCategories", listCategories);
		request.setAttribute("listTeachers", listTeachers);
		
		String result = request.getParameter("result");
		if (result != null) {
			request.setAttribute("result", result);
		}

		String sorted = request.getParameter("sorted");
		if (sorted != null) {
			switch (sorted) {
			case "nameAsc":
				Collections.sort(listCourse, compareByNameAsc);
				break;
			case "nameDesc":
				Collections.sort(listCourse, compareByNameDesc);
				break;
			case "length":
				Collections.sort(listCourse, compareByLength);
				break;
			case "countStudents":
				Collections.sort(listCourse, compareByCountStudents);
				break;
			default:
				break;
			}
		}

		String forward = Path.PAGE_ALL_COURSES;

		LOG.debug("Command finished");
		return forward;
	}

}
