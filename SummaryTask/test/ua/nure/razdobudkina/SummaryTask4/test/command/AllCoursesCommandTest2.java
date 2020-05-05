package ua.nure.razdobudkina.SummaryTask4.test.command;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.razdobudkina.SummaryTask4.db.DBManagerForTest;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;
import ua.nure.razdobudkina.SummaryTask4.web.command.CommandListCourses;
import ua.nure.razdobudkina.SummaryTask4.web.command.LoginCommand;


public class AllCoursesCommandTest2 extends Mockito {

	@Test
	public void executeTest() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		
		DBManagerForTest manager = mock(DBManagerForTest.class);

		CommandListCourses loginCommand = new CommandListCourses();
		loginCommand.execute(request, response);

		//verify(request, atLeast(1)).getParameter("password");
		//verify(request, atLeast(1)).getParameter("login");
	}

}
