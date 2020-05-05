package ua.nure.razdobudkina.SummaryTask4.customtags;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SumDateTags extends SimpleTagSupport {

	private String countDays;
	
	public void setCountDays(String countDays) {
		this.countDays = countDays;
	}

	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("Days is:" + countDays);
		try {
			int amount = Integer.parseInt(countDays);
			LocalDate newDate = LocalDate.now().plusDays(amount);
			getJspContext().getOut().print(newDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SkipPageException("Exception in sum date count days " + countDays);
		}
	}
	
}
