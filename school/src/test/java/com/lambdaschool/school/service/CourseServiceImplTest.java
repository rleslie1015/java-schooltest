package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.InstructorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplTest
{
	@Autowired
	private CourseService courseService;

	@Autowired
	private InstructorRepository instructorRepository;

	@Before
	public void setUp() throws Exception
	{
		//we need this to set up this allows us to run the test
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void deleteFound()
	{
		courseService.delete(1);
		assertEquals(5, courseService.findAll().size());
	}

	@Test (expected = EntityNotFoundException.class)
	public void deleteNotFound()
	{
		courseService.delete(1000);
		assertEquals(5, courseService.findAll().size());
	}

	@Test
	public void findCourseById()
	{
		assertEquals("JavaScript", courseService.findCourseById(2).getCoursename());
	}

	@Test(expected = EntityNotFoundException.class)
	public void findCourseByIdNo()
	{
		assertEquals("Lambda", courseService.findCourseById(1000).getCoursename());
	}


	@Test
	public void save()
	{
		Course c7 = new Course("Python", instructorRepository.findById(2L).orElseThrow(()-> new EntityNotFoundException(Long.toString(2L))));

		Course addCourse = courseService.save(c7);

		assertNotNull(addCourse);

		Course foundCourse = courseService.findCourseById(addCourse.getCourseid());

		assertEquals(addCourse.getCoursename(), foundCourse.getCoursename());
	}
}