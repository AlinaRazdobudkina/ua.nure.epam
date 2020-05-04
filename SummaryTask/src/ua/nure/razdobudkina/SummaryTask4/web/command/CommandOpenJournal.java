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
import ua.nure.razdobudkina.SummaryTask4.db.bean.CourseExtend;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Journal;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandOpenJournal extends Command {

	
	private static final long serialVersionUID = 6119223321629138759L;
	private static final Logger LOG = Logger.getLogger(CommandOpenJournal.class);

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		HttpSession session = request.getSession();
		
		DBManager manager = DBManager.getInstance();
		
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		List<Journal> journal = manager.allCoursesJournal(courseId);
		
		CourseExtend course = manager.findCourseById(courseId);
		
		LOG.trace("Found in DB: list entries in journal for course --> " + journal);
		
		request.setAttribute("EndDate", course.getEndDate());
		request.setAttribute("courseId", course.getId());
			
		session.setAttribute("journalForTeacher", journal);
		LOG.trace("Set the session attribute: journalForTeacher --> " + journal);
		
		String forward = Path.PAGE_JOURNAL_FOR_TEACHER;

		LOG.debug("Command finished");
		return forward;
	}

	

}
