package ua.nure.razdobudkina.SummaryTask4.db.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Journal implements Serializable {

	private static final long serialVersionUID = -6871101360432842895L;

	private User user;

	private Course course;

	private int rating;

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus() {
		LocalDate now = LocalDate.now();
		status = 0;
		if (now.compareTo(course.getStartDate()) < 0) {
			status = -1;
		} 
		if (now.compareTo(course.getEndDate()) >= 0) {
			status = 1;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) { 
		this.rating = rating;
	}

	public int getProgressCourseInPercent() {

		LocalDate startDate = course.getStartDate();
		LocalDate endDate = course.getEndDate();
		LocalDate now = LocalDate.now(); 

		Period period = Period.between(now, endDate);
		int diffAll = period.getDays();

		System.out.println(diffAll);
		period = Period.between(startDate, now);
		int difftoday = period.getDays();
		
		diffAll += difftoday;

		if (diffAll < 1) {
			return 0;
		}

		return Math.round((difftoday * 100) / diffAll);

	}

	@Override
	public String toString() {
		return "Journal [user=" + user + ", " + "course=" + course + "," + " rating=" + rating + ", " + "status="
				+ status + "]";
	}

}
