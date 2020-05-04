package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandSaveEntryInJournal extends Command {

	private static final long serialVersionUID = 6271527174017565837L;
	private static final Logger LOG = Logger.getLogger(CommandSaveEntryInJournal.class);

	public CommandSaveEntryInJournal() {
		super();
	}
	
	public CommandSaveEntryInJournal(boolean usePRG) {
		super(usePRG);
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		String forward = Path.COMMAND_JOURNAL_FOR_TEACHER;
		
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		int userId = Integer.parseInt(request.getParameter("user_id"));
		int rating = Integer.parseInt(request.getParameter("rating"));

		String result;
		
		forward += "&courseId=" + courseId;
		
		DBManager manager = DBManager.getInstance();
		
		manager.updateRatingInJournalForCourseStudent(rating,userId, courseId); 

		result = "Save";
		
		forward += "&result=" + result;
		
		LOG.trace("Update rating in journal ");
		
		LOG.debug("Command finished");
		
		return forward;
	}
}
