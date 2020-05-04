package ua.nure.razdobudkina.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author A.Razdobudkina
 * 
 */
public final class Path {
	
	// pages
	public static final String PAGE_LOGIN = "/login2.jsp";
	public static final String PAGE_WELCOME = "/index.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	
	public static final String PAGE_ALL_COURSES = "/allCourses.jsp";
	public static final String PAGE_PROFILE = "/WEB-INF/jsp/profile.jsp";
	public static final String PAGE_ALL_COURSES_FOR_STUDENT = "/WEB-INF/jsp/coursesByStudent.jsp";
	public static final String PAGE_JOURNAL_FOR_TEACHER = "/WEB-INF/jsp/journalForTeacher.jsp";
	
	public static final String PAGE_EDIT_COURSE = "/WEB-INF/jsp/editCourse.jsp";
	
	public static final String PAGE_EDIT_TEACHER = "/WEB-INF/jsp/editTeacher.jsp";
	
	public static final String PAGE_LIST_USERS = "/WEB-INF/jsp/listUsers.jsp";
	
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";

	// commands
	public static final String COMMAND_LIST_COURSES = "controller?command=listCourses";
	public static final String COMMAND_JOURNAL_FOR_TEACHER = "controller?command=openJournal";
	public static final String COMMAND_LIST_STUDENTS = "controller?command=listUsers&preCommand=students";
	public static final String COMMAND_LIST_TEACHERS = "controller?command=listUsers&preCommand=teachers";

}