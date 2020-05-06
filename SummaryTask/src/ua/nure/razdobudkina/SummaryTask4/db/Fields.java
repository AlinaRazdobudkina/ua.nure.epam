package ua.nure.razdobudkina.SummaryTask4.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 * @author D.Kolesnikov
 * 
 */
public final class Fields {
	
	// entities
	public static final String ENTITY_ID = "id";
	
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_PHONE = "phone";
	public static final String USER_EMAIL = "email";
	public static final String USER_ACTIVE = "active";
	public static final String USER_ROLE_ID = "role_id";
	public static final String USER_COUNT_OF_COURSES = "countCourses";
	public static final String USER_AVG_RATING = "avgRating";
	
	public static final String COURSE_NAME = "name";
	public static final String COURSE_START_DATE = "start_date";
	public static final String COURSE_END_DATE= "end_date";
	public static final String COURSE_LENGTH= "length";
	public static final String COURSE_TEACHER_ID= "teacher_id";
	public static final String COURSE_CATEGORY_ID= "category_id";
	public static final String COURSE_DESCRIPTION= "description";

	public static final String CATEGORY_NAME = "name";
	
	public static final String USER_JOURNAL_RAITING = "rating";	
	public static final String USER_ID_JOURNAL = "user_id";	
	public static final String COURSE_ID_JOURNAL = "course_id";	
	
	// beans
	public static final String COURSE_CATEGORY_NAME = "categoryName";	
	public static final String COURSE_TEACHER_NAME = "teacherName";	
	public static final String COURSE_COUNT_OF_STUDENTS = "countStudents";	

	
}