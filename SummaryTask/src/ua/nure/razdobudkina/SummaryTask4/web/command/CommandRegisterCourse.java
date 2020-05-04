package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandRegisterCourse extends Command {

	private static final long serialVersionUID = 6271527174017565837L;
	private static final Logger LOG = Logger.getLogger(CommandRegisterCourse.class);

	public CommandRegisterCourse() {
		super();
	}
	
	public CommandRegisterCourse(boolean usePRG) {
		super(usePRG);
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		String forward = Path.COMMAND_LIST_COURSES;
		
		HttpSession session = request.getSession();
		
		System.out.println(request.getParameter("course_id"));
		
		int courseId = Integer.parseInt(request.getParameter("course_id"));
		
		User user = (User) session.getAttribute("user");
		
		session.setAttribute("errorMessage", "");
		session.setAttribute("result", "");
		
		DBManager manager = DBManager.getInstance();
		
		System.out.println(user.getId());
		
		if(manager.addCourseStudent(user.getId(), courseId)) {
			session.setAttribute("result", "You have been successfully registered for the course");
		}else {
			session.setAttribute("errorMessage", "You have already registered for this course");
		}
		LOG.trace("Find student for a course and Insert in journal course with student ");
		
		LOG.debug("Command finished");
		
		return forward;
	}

}
