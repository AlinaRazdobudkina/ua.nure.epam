package ua.nure.razdobudkina.SummaryTask4.test.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.razdobudkina.SummaryTask4.db.DBManagerForTest;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;
import ua.nure.razdobudkina.SummaryTask4.web.command.LoginCommand;

public class LoginCommandTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		
		DBManagerForTest manager = mock(DBManagerForTest.class);


		User user = mock(User.class);
		user.setId(1);
		user.setLogin("login");
		user.setFirstName("Some Name");
		user.setLastName("SomeLname");
		user.setEmail("email");
		user.setPassword("password");
		user.setRoleId(0);
		user.setActive(true);

		when(request.getSession()).thenReturn(session);
		when(request.getParameter("login")).thenReturn("login");
		when(request.getParameter("password")).thenReturn("password");
		when(manager.findUserByLoginTest("login")).thenReturn(user);

		LoginCommand loginCommand = new LoginCommand();
		loginCommand.execute(request, response);

		verify(request, atLeast(1)).getParameter("password");
		verify(request, atLeast(1)).getParameter("login");
	}

}
