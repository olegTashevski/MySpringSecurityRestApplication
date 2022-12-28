package mk.oleg.java.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mk.oleg.java.dto.CourseGet;
import mk.oleg.java.dto.CoursePost;
import mk.oleg.java.service.CourseService;

@RestController
@RequestMapping("/api/course")
public class CourseApi {
//	/hasRole('ROLe_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')
	
	@Autowired
	private CourseService courseService;
	@PreAuthorize("hasAuthority('CourseCreate')")
	@PostMapping("/addCourse")
	public String addCourse(@RequestBody CoursePost course) {
		courseService.addCourse(course);
		return "Successfully added course";
	}
	
	@PreAuthorize("hasAnyRole('STUDENT','MENTOR','DIRECTOR')") 
	@GetMapping("/getCourse")
	public CourseGet getCourse(@RequestParam Long id) {
		return courseService.getCourse(id);
	}
	
	@PreAuthorize("hasAnyRole('STUDENT','MENTOR','DIRECTOR')") 
	@GetMapping("/getAllCourses")
	public Set<CourseGet> getAll(){
		return courseService.getAll();
	}
	
	
	
	@PreAuthorize("hasAuthority('CourseChangeMentor')")
	@PutMapping("/changeMentor")
	public ResponseEntity<String> changeMentor(@RequestParam Long courseId,@RequestParam String username) {
		
		return ResponseEntity.ok().body(courseService.changeMentor(courseId,username));
	}
	
	@PreAuthorize("hasAuthority('CourseDelete')")
	@DeleteMapping("/deleteCourse")
	public String deleteCourse(@RequestParam Long id) {
		return courseService.deleteCourse(id);
	}

}
