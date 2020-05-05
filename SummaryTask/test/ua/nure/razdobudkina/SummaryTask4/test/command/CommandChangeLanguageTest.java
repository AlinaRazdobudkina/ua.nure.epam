package ua.nure.razdobudkina.SummaryTask4.test.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;
import ua.nure.razdobudkina.SummaryTask4.web.command.CommandChangeLanguage;


public class CommandChangeLanguageTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);

		when(request.getParameter("language")).thenReturn("language");
		when(request.getSession()).thenReturn(session);
		
		String forward = new CommandChangeLanguage().execute(request, response);

		verify(request, atLeast(1));

		assertEquals(Path.PAGE_PROFILE, forward);
	}

}
