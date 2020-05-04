package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;

public class CommandChangeStatusStudent extends Command {

	private static final long serialVersionUID = 6271527174017565837L;
	private static final Logger LOG = Logger.getLogger(CommandChangeStatusStudent.class);

	public CommandChangeStatusStudent() {
		super();
	}
	
	public CommandChangeStatusStudent(boolean usePRG) {
		super(usePRG);
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		String forward = Path.COMMAND_LIST_STUDENTS;
		
		String userId = request.getParameter("userId");
		
		DBManager manager = DBManager.getInstance();

		String [] checked = request.getParameterValues("status");
		boolean checkedStudent = false;
		if(checked != null) {
			for (String id : checked) {
				if(userId.equals(id)) {
					checkedStudent = true;
				}
			}
		}
		System.out.println(checkedStudent);
		manager.updateUser(Integer.parseInt(userId), checkedStudent); 

		
		LOG.trace("Update status student");
		
		LOG.debug("Command finished");
		
		return forward;
	}
}
