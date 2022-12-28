package mk.oleg.java.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.oleg.java.dto.StudentGet;
import mk.oleg.java.dto.StudentPost;
import mk.oleg.java.model.Course;
import mk.oleg.java.model.Student;

import mk.oleg.java.repo.CustomRepo;
import mk.oleg.java.repo.StudentRepo;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private CustomRepo  customRepo;

	public void addStudent(StudentPost student) {
		
		Student studentNew = Student.builder()
				.fullName(student.getFullName())
				.username(student.getUsername())
				.address(student.getAddress())
				.build();
		studentRepo.save(studentNew);
		
	}

	public StudentGet getStudent(String studentUsername) {
		// TODO Auto-generated method stub
		return studentRepo.getStudent(studentUsername);
	}

	public Set<StudentGet> getAll() {
		// TODO Auto-generated method stub
		return studentRepo.getAll();
	}

	public void addStudentToCourse(String studentUsername, Long courseId) {
		customRepo.addStudentToCourse(studentUsername, courseId);
		
	}

	public void deleteStudent(String studentUsername) {
		Set<Course> courses = studentRepo.getStudentCourses(studentUsername);
		Student student = studentRepo.findById(studentUsername).orElseThrow();
		courses.forEach(co->{
			co.getStudents().remove(student);
		});
		studentRepo.delete(student);
		
		
	}

	public void removeStudent(Long courseId, String username) {
		customRepo.removeStudent(courseId, username);
		
	}
}
