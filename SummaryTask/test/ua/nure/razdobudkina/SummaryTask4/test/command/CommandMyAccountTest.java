package ua.nure.razdobudkina.SummaryTask4.test.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.razdobudkina.SummaryTask4.Path;
import ua.nure.razdobudkina.SummaryTask4.exception.AppException;
import ua.nure.razdobudkina.SummaryTask4.web.command.CommandMyAccount;


public class CommandMyAccountTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		String forward = new CommandMyAccount().execute(request, response);

		assertEquals(Path.PAGE_PROFILE, forward);
	}

}
