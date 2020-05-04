package ua.nure.razdobudkina.SummaryTask4.db.entity;

import java.time.LocalDate;
import java.time.Period;

import ua.nure.razdobudkina.SummaryTask4.db.DBManager;
import ua.nure.razdobudkina.SummaryTask4.exception.DBException;

public class Course extends Entity {
	
	private static final long serialVersionUID = 5140314085388161090L;
	
	private String name;

	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private int length;
	
	private int categoryId;
	
	private int teacherId;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCategoryId() {
		return categoryId;
	}
	
	public LocalDate getCurrentDate() {
		return LocalDate.now();
	}
	
	public LocalDate getSumDateDays(LocalDate date, int countOfDays) {
		return date.plusDays(countOfDays);
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId; 
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Course [name=" + name + ","
				+ " start_date=" + startDate + ","
				+ " end_date=" + endDate +
				", length=" + length
				+ ", category_id=" + categoryId +
				", teacher_id=" + teacherId +
				", description=" + description + "]";
	}
}
