package ua.nure.razdobudkina.SummaryTask4.db.bean;


import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;

public class CourseExtend extends Course {
	
	private static final long serialVersionUID = 604588567212397982L;

	private String categoryName;
	
	private String teacherName;
	
	private int countOfStudents;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getCountOdStudents() {
		return countOfStudents;
	}

	public void setCountOfStudents(int countOfStudents) {
		this.countOfStudents = countOfStudents;
	}

	@Override
	public String toString() {
		return "CourseExtend [categoryName=" + categoryName + 
				", teacherName=" + teacherName +
				", countOfStudents=" + countOfStudents + "]";
	}

	
}
