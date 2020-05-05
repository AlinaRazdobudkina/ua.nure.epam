package ua.nure.razdobudkina.SummaryTask4.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import ua.nure.razdobudkina.SummaryTask4.db.entity.Course;
import ua.nure.razdobudkina.SummaryTask4.db.entity.Journal;


public class JournalTest {
	
	@Test
	public void testSetStatus() {
		
		Journal journal = new Journal();
		Course course = new Course();
		course.setStartDate(LocalDate.of(2020, 5, 6));
		course.setEndDate(LocalDate.of(2020, 5, 30));
		journal.setCourse(course);
		journal.setStatus();
		assertEquals(-1, journal.getStatus());
		
	}
	
	@Test
	public void testSetStatus2() {
		
		Journal journal = new Journal();
		Course course = new Course();
		course.setStartDate(LocalDate.of(2020, 5, 2));
		course.setEndDate(LocalDate.of(2020, 5, 30));
		journal.setCourse(course);
		journal.setStatus();
		assertEquals(0, journal.getStatus());
		
	}
	
	@Test
	public void testSetStatus3() {
		
		Journal journal = new Journal();
		Course course = new Course();
		course.setStartDate(LocalDate.of(2020, 5, 2));
		course.setEndDate(LocalDate.of(2020, 5, 3));
		journal.setCourse(course);
		journal.setStatus();
		assertEquals(1, journal.getStatus());
		
	}
	
	@Test
	public void testGetProgressCourseInPercent() {
		
		Journal journal = new Journal();
		Course course = new Course();
		course.setStartDate(LocalDate.of(2020, 5, 1));
		course.setEndDate(LocalDate.of(2020, 5, 30));
		journal.setCourse(course);
		assertEquals(13, journal.getProgressCourseInPercent());
		
	}

}
