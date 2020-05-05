package ua.nure.razdobudkina.SummaryTask4.db;

import java.util.List;

import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.DBException;


/**
 * DB manager for test.
 * 
 * @author A.Razdobudkina
 * 
 */
public class DBManagerForTest {

	private final DBManager dbManager;
	
	public DBManagerForTest() throws DBException {
		super();
		this.dbManager = DBManager.getInstance();
	}

	public User findUserByLoginTest(String login) throws DBException {
		return this.dbManager.findUserByLogin(login);
	}
	
	public List<Course> allCoursesTest() throws DBException {
		return this.dbManager.allCourses();
	}
}