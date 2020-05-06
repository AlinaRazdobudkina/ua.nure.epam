package ua.nure.razdobudkina.SummaryTask4.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.razdobudkina.SummaryTask4.db.bean.CourseExtend;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Category;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Journal;
import ua.nure.razdobudkina.SummaryTask4.db.entity.User;
import ua.nure.razdobudkina.SummaryTask4.exception.DBException;
import ua.nure.razdobudkina.SummaryTask4.exception.Messages;

/**
 * DB manager
 * 
 * @author A.Razdobudkina
 * 
 */
public class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DBManager instance;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			System.out.println(initContext);
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			System.out.println(envContext);
			ds = (DataSource) envContext.lookup("jdbc/ST4DB");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	private DataSource ds;

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT * FROM categories WHERE id=?";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private static final String SQL_UPDATE_COURSE = "UPDATE courses SET name=?, start_date=?, end_date=?,"
			+ " length=?, category_id=?, teacher_id=?, description=? WHERE id=?";

	private static final String SQL_ADD_COURSE = "INSERT INTO courses VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_ADD_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_DELETE_COURSE = "DELETE FROM courses WHERE id=?";

	private static final String SQL_GET_JOURNAL_FOR_COURSE = "SELECT * FROM journal WHERE course_id=?";

	private static final String SQL_UPDATE_USER = "UPDATE users SET password=?,"
			+ " first_name=?, last_name=?, phone=?, email=?, active=?" + "	WHERE id=?";

	private static final String SQL_GET_ALL_COURSES = "SELECT * FROM courses";

	private static final String SQL_GET_ALL_COURSES_WITH_NAMES = "SELECT DISTINCT courses.*,"
			+ " (SELECT COUNT(course_id) from journal WHERE journal.course_id = courses.id) AS countStudents, "
			+ "categories.name AS categoryName,"
			+ " CONCAT(users.first_name,CONCAT(CHAR(194),CHAR(160)),users.last_name) AS teacherName FROM courses"
			+ " LEFT JOIN journal ON courses.id = journal.course_id "
			+ "LEFT JOIN categories ON courses.category_id = categories.id"
			+ " LEFT JOIN users ON courses.teacher_id = users.id";
	
	private static final String SQL_FIND_COURSE_BY_ID = SQL_GET_ALL_COURSES_WITH_NAMES + " WHERE courses.id=?";

	private static final String SQL_ADD_COURSE_STUDENT = "INSERT INTO journal SET user_id = ?, course_id = ?";

	private static final String SQL_FIND_STUDENT_FOR_COURSE = "SELECT * FROM journal WHERE user_id = ? AND course_id = ?";

	private static final String SQL_GET_ALL_COURSES_FOR_STUDENT = "SELECT * FROM courses LEFT JOIN journal"
			+ " ON courses.id = journal.course_id WHERE journal.course_id IS NOT NULL AND journal.user_id = ?";

	private static final String SQL_SET_RATING = "UPDATE journal SET rating=? WHERE user_id = ? AND course_id = ?";

	private static final String SQL_GET_ALL_CATEGORIES = "SELECT * FROM categories";

	private static final String SQL_USERS_FOR_ROLE = "SELECT * FROM users WHERE role_id=?";
	
	private static final String SQL_LIST_STUDENTS_EXTEND = "SELECT DISTINCT users.*,"
			+ " (SELECT COUNT(course_id) from journal WHERE journal.user_id = users.id) AS countCourses, "
			+ "(SELECT ROUND(AVG(journal.rating)) from journal WHERE journal.user_id = users.id AND journal.rating != 0) AS avgRating "
			+ "FROM users WHERE role_id=?";

	private static final String SQL_UPDATE_STATUS_USER = "UPDATE users SET active=? WHERE id=?";

	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	/**
	 * Returns all courses.
	 * 
	 * @return List of course entities.
	 */
	public List<Course> allCourses() throws DBException {
		List<Course> coursesList = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_COURSES);
			while (rs.next()) {
				coursesList.add(extractCourse(rs, false));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return coursesList;
	}
	
	/**
	 * Returns all courses with names and count of students.
	 * 
	 * @return List of courseExtend entities.
	 */
	public List<CourseExtend> allCoursesWithNames() throws DBException {
		List<CourseExtend> coursesList = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			System.out.println("before query");
			rs = stmt.executeQuery(SQL_GET_ALL_COURSES_WITH_NAMES);
			System.out.println("after query");
			while (rs.next()) {
				coursesList.add((CourseExtend) extractCourse(rs, true));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return coursesList;
	}

	/**
	 * Returns all courses for student.
	 * 
	 * @return List of course entities.
	 */
	public List<Journal> allCoursesForStudent(int userId) throws DBException {
		List<Journal> journalList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_ALL_COURSES_FOR_STUDENT);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				journalList.add(extractJournal(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES_BY_USER_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return journalList;
	}

	/**
	 * Returns all entry in journal for course.
	 * 
	 * @return List of journal.
	 */
	public List<Journal> allCoursesJournal(int courseId) throws DBException {
		List<Journal> journalList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_JOURNAL_FOR_COURSE);
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				journalList.add(extractJournal(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_JOURNAL, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return journalList;
	}

	/**
	 * Register course for student
	 * 
	 * @param user_id user identifier. course_id course identifier.
	 * @throws DBException
	 */
	public boolean addCourseStudent(int userId, int courseId) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();

			pstmt = con.prepareStatement(SQL_ADD_COURSE_STUDENT);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, courseId);

			if (!findStudentInJournal(userId, courseId) && pstmt.executeUpdate() > 0) {
				con.commit();
				return true;
			}
			rollback(con);
			return false;

		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_ADD_COURSE_IN_JOURNAL, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Update rating in journal for student and course
	 * 
	 * @param userId   user identifier.
	 * @param courseId course identifier.
	 * @throws DBException
	 */
	public void updateRatingInJournalForCourseStudent(int rating, int userId, int courseId) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_SET_RATING);
			pstmt.setInt(1, rating);
			pstmt.setInt(2, userId);
			pstmt.setInt(3, courseId);
			pstmt.executeUpdate();
			con.commit();

		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_RATING_JOURNAL, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Find student for a course
	 * 
	 * @param userId   user identifier.
	 * @param courseId course identifier.
	 * @throws DBException
	 * @return boolean
	 */
	public boolean findStudentInJournal(int userId, int courseId) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_STUDENT_FOR_COURSE);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, courseId);
			rs = pstmt.executeQuery();
			result = rs.next();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_JOURNAL_FOR_USER_COURSE, ex);
		} finally {
			close(con, pstmt, rs);
		}

		return result;
	}

	/**
	 * Returns a category with the given identifier.
	 * 
	 * @param id category identifier.
	 * @return Category entity.
	 * @throws DBException
	 */
	public Category findCategory(int id) throws DBException {
		Category category = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CATEGORIES_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				category = extractCategory(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORY_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return category;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user with the given id.
	 * 
	 * @param id User id.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserById(int id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a course with the given id.
	 * 
	 * @param id Course id.
	 * @return Course entity.
	 * @throws DBException
	 */
	public CourseExtend findCourseById(int id) throws DBException {
		CourseExtend course = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_COURSE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				course = (CourseExtend) extractCourse(rs, true);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSE_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return course;
	}

	/**
	 * Update user.
	 * 
	 * @param User user to update.
	 * @throws DBException
	 */
	public void updateUser(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Update status user.
	 * 
	 * @param int     userId identifier,
	 * @param boolean status.
	 * @throws DBException
	 */
	public void updateUser(int userId, boolean status) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_STATUS_USER);
			pstmt.setBoolean(1, status);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Update course.
	 * 
	 * @param CourseExtend course,
	 * @throws DBException
	 * @throws SQLException 
	 */
	public void updateCourse(Course course) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateCourse(con, course);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_COURSE, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Add course.
	 * 
	 * @param CourseExtend course,
	 * @throws DBException
	 * @throws SQLException 
	 */
	public void addCourse(Course course) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateCourse(con, course);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_ADD_COURSE, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Delete course.
	 * 
	 * @param CourseExtend course,
	 * @throws DBException
	 * @throws SQLException 
	 */
	public void deleteCourse(int courseId) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_COURSE);
			pstmt.setInt(1, courseId);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_COURSE, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * List of all categories.
	 * 
	 * @return List<Category> list Course entity
	 * @throws DBException
	 * @throws SQLException 
	 */
	public List<Category> allCategories() throws DBException {
		List<Category> categorieslList = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_CATEGORIES);
			while (rs.next()) {
				categorieslList.add(extractCategory(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return categorieslList;
	}

	/**
	 * List of all user with role id.
	 * 
	 * @param id roleId identifier
	 * @return List<User> list User entity
	 * @throws DBException
	 * @throws SQLException 
	 */
	public List<User> allUsersForRole(int roleId) throws DBException {
		List<User> userlList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_USERS_FOR_ROLE);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userlList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES_FOR_ROLE, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return userlList;
	}
	

	public List<User> allUsersForRoleExtend(int roleId) throws DBException {
		List<User> userlList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_LIST_STUDENTS_EXTEND);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = (extractUser(rs));
				userlList.add(extractUserExtend(rs,user));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES_FOR_ROLE, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return userlList;
	}

	// //////////////////////////////////////////////////////////
	// Entity access methods (for transactions)
	// //////////////////////////////////////////////////////////

	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			int userId = user.getId();
			if (userId == 0) {
				pstmt = con.prepareStatement(SQL_ADD_USER);
			} else {
				pstmt = con.prepareStatement(SQL_UPDATE_USER);
			}
			int k = 1;
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setString(k++, user.getPhone());
			pstmt.setString(k++, user.getEmail());
			pstmt.setBoolean(k++, user.isActive());
			pstmt.setInt(k++, user.getRoleId());
			if (userId != 0) {
				pstmt.setInt(k, userId);
			}
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	private void updateCourse(Connection con, Course course) throws SQLException {

		PreparedStatement pstmt = null;
		try {
			int courseId = course.getId();
			System.out.println(courseId);
			if (course.getId() != 0) {
				pstmt = con.prepareStatement(SQL_UPDATE_COURSE);
			} else {
				pstmt = con.prepareStatement(SQL_ADD_COURSE);
			}
			int k = 1;
			System.out.println(course);
			pstmt.setString(k++, course.getName());
			pstmt.setDate(k++, Date.valueOf(course.getStartDate()));
			pstmt.setDate(k++, Date.valueOf(course.getEndDate()));
			pstmt.setInt(k++, course.getLength());
			pstmt.setInt(k++, course.getCategoryId());
			pstmt.setInt(k++, course.getTeacherId());
			pstmt.setString(k++, course.getDescription());

			if (courseId != 0) {
				pstmt.setInt(k, course.getId());
			}
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}

	}

	/**
	 * Closes a connection.
	 * 
	 * @param con Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}


	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs Result set from which a user entity will be extracted.
	 * @return User entity
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setPhone(rs.getString(Fields.USER_PHONE));
		user.setEmail(rs.getString(Fields.USER_EMAIL));
		user.setActive(rs.getBoolean(Fields.USER_ACTIVE));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}
	
	private User extractUserExtend(ResultSet rs,User user) throws SQLException {
		user.setCountOfCourse(rs.getInt(Fields.USER_COUNT_OF_COURSES));
		user.setAvgRating(rs.getInt(Fields.USER_AVG_RATING));
		System.out.println(user);
		return user;
	}

	/**
	 * Extracts an course entity from the result set.
	 * 
	 * @param rs Result set from which a course entity will be extracted.
	 * @param extend boolean which course need create
	 * @return Course entity
	 */
	private Course extractCourse(ResultSet rs, boolean extend) throws SQLException {		
		Course course = new Course();
		if(extend) {
			course = new CourseExtend();
			System.out.println(course);
			((CourseExtend) course).setCategoryName(rs.getString(Fields.COURSE_CATEGORY_NAME));
			((CourseExtend) course).setTeacherName(rs.getString(Fields.COURSE_TEACHER_NAME));
			((CourseExtend) course).setCountOfStudents(rs.getInt(Fields.COURSE_COUNT_OF_STUDENTS));
		}
		course.setId(rs.getInt(Fields.ENTITY_ID));
		course.setName(rs.getString(Fields.COURSE_NAME));
		course.setDescription(rs.getString(Fields.COURSE_DESCRIPTION));
		course.setStartDate(rs.getDate(Fields.COURSE_START_DATE).toLocalDate());
		course.setEndDate(rs.getDate(Fields.COURSE_END_DATE).toLocalDate());
		course.setTeacherId(rs.getInt(Fields.COURSE_TEACHER_ID));
		course.setLength(rs.getInt(Fields.COURSE_LENGTH));
		course.setCategoryId(rs.getInt(Fields.COURSE_CATEGORY_ID));
		System.out.println(course);
		return course;
	}

	/**
	 * Extracts a category entity from the result set.
	 * 
	 * @param rs Result set from which a category entity will be extracted.
	 * @return Category entity.
	 */
	private Category extractCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt(Fields.ENTITY_ID));
		category.setName(rs.getString(Fields.CATEGORY_NAME));
		return category;
	}

	/**
	 * Extracts a journal from the result set.
	 * 
	 * @param rs Result set from which a menu item entity will be extracted.
	 * @return Journal entity.
	 * @throws DBException
	 */

	private Journal extractJournal(ResultSet rs) throws SQLException, DBException {
		Journal journal = new Journal();

		CourseExtend course = findCourseById(rs.getInt(Fields.COURSE_ID_JOURNAL));
		User user = findUserById(rs.getInt(Fields.USER_ID_JOURNAL));
		journal.setCourse(course);
		journal.setUser(user);
		journal.setRating(rs.getInt(Fields.USER_JOURNAL_RAITING));
		journal.setStatus();

		return journal;
	}

}