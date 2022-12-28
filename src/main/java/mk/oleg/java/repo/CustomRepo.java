package mk.oleg.java.repo;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.oleg.java.model.Course;
import mk.oleg.java.model.Student;

@Repository
public class CustomRepo {
	@PersistenceContext()
	private EntityManager entityManager;
	
	@Transactional
	public void addStudentToCourse(String studentUsername,Long courseId) {
	  Student student = entityManager.find(Student.class, studentUsername);
	  Course course = entityManager.find(Course.class, courseId);
	  student.getCourses().add(course);
	  course.getStudents().add(student);
	  entityManager.merge(student);
	  entityManager.merge(course);
	}

	@Transactional
	public void removeStudent(Long courseId, String studentUsername) {
		 Student student = entityManager.find(Student.class, studentUsername);
		  Course course = entityManager.find(Course.class, courseId);
		  student.getCourses().remove(course);
		  course.getStudents().remove(student);
		  entityManager.merge(student);
		  entityManager.merge(course);
	}

}
