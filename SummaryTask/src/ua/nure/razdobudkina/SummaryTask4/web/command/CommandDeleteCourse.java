package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandDeleteCourse extends Command {

	private static final long serialVersionUID = 6271527174017565837L;
	private static final Logger LOG = Logger.getLogger(CommandDeleteCourse.class);

	public CommandDeleteCourse() {
		super();
	}

	public CommandDeleteCourse(boolean usePRG) {
		super(usePRG);
	}

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		String forward = Path.COMMAND_LIST_COURSES;

		DBManager manager = DBManager.getInstance();

		manager.deleteCourse(Integer.parseInt(request.getParameter("courseId")));
		LOG.trace("Delete course");
		String result = "Course has been deleted";

		forward += "&result=" + result;

		LOG.debug("Command finished");

		return forward;
	}
}
