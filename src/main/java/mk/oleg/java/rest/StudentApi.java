package mk.oleg.java.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mk.oleg.java.dto.StudentGet;
import mk.oleg.java.dto.StudentPost;
import mk.oleg.java.model.Student;
import mk.oleg.java.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentApi {
	
	@Autowired
	private StudentService studentService;
	
	@PreAuthorize("hasAuthority('StudentIsert') && #student.username == authentication.principal.username")
	@PostMapping("/addStudent")
	public String addStudent(@RequestBody StudentPost student) {
		studentService.addStudent(student);
		return "Successfully added student";
	}
	
	@PreAuthorize("hasAnyRole('STUDENT','MENTOR','DIRECTOR')")
	@GetMapping("/getStudent")
	public StudentGet getStudent(@RequestParam String username) {
		return studentService.getStudent(username);
	}
	
	@PreAuthorize("hasAnyRole('STUDENT','MENTOR','DIRECTOR')")
	@GetMapping("/getAll")
	public Set<StudentGet> getAll(){
		return studentService.getAll();
	}
	
	@PreAuthorize("hasAuthority('StudentEnroll') && "
			+ "#student.username == authentication.principal.username")
	@PutMapping("/addStudentToCourse")
	public String addStudentToCourse(@RequestParam String username , @RequestParam Long courseId) {
		studentService.addStudentToCourse(username,courseId);
		return "Successfully added";
	}
	
	@PreAuthorize("(hasAuthority('StudentDisenroll') && "
			+ "#student.username == authentication.principal.username)"
			+ "||hasRole('MENTOR')")
	@DeleteMapping("/deleteStudent")
	public String deleteStudent(@RequestParam String username) {
		studentService.deleteStudent(username);
		return "Successfully deleted student";
	}
	
	@PreAuthorize("hasAuthority('StudentDisenroll') && "
			+ "#student.username == authentication.principal.username")
	@PutMapping("/removeStudentFromCourse")
	public String removeStudentFromCourse(@RequestParam Long courseId ,@RequestParam String username) {
		studentService.removeStudent(courseId,username);
		return "Successfully student removed";
	}
	
}
