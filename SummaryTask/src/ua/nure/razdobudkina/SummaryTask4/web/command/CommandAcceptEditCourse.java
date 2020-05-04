package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;
import java.time.LocalDate;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandAcceptEditCourse extends Command {

	private static final long serialVersionUID = 6271527174017565837L;
	private static final Logger LOG = Logger.getLogger(CommandAcceptEditCourse.class);

	public CommandAcceptEditCourse() {
		super();
	}
	
	public CommandAcceptEditCourse(boolean usePRG) {
		super(usePRG);
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		String forward = Path.COMMAND_LIST_COURSES;
		
		DBManager manager = DBManager.getInstance();
		
		Course course = new Course();
		
		course.setCategoryId(manager.findCategory(Integer.parseInt(request.getParameter("category"))).getId());
		course.setDescription(request.getParameter("description"));
		course.setName(request.getParameter("name"));
		course.setStartDate(LocalDate.parse((request.getParameter("startDate"))));
		course.setEndDate(LocalDate.parse((request.getParameter("endDate"))));
		course.setTeacherId(manager.findUserById(Integer.parseInt(request.getParameter("teacher"))).getId());
		course.setLength(Integer.parseInt(request.getParameter("length")));
		
		String preCommand = request.getParameter("preCommand");
		System.out.println(preCommand);
		String result;
		
		if("update".equals(preCommand)) {		
			course.setId(Integer.parseInt(request.getParameter("courseId")));
			manager.updateCourse(course);
			LOG.trace("Update course");
			result = "Saved successfully";
		}else {
			manager.addCourse(course);
			result = "Course has been added";
			LOG.trace("Add course");
		}

		forward += "&result=" + result;
	
		LOG.debug("Command finished");
		
		return forward;
	}
}
