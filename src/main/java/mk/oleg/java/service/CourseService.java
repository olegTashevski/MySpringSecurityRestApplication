package mk.oleg.java.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mk.oleg.java.dto.CourseGet;
import mk.oleg.java.dto.CoursePost;
import mk.oleg.java.dto.GetMentor;
import mk.oleg.java.dto.StudentGet;
import mk.oleg.java.model.Course;
import mk.oleg.java.model.Mentor;
import mk.oleg.java.model.Student;
import mk.oleg.java.repo.CourseRepo;
import mk.oleg.java.repo.CustomRepo;
import mk.oleg.java.repo.MentorRepo;
import mk.oleg.java.repo.StudentRepo;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private CustomRepo customRepo;
	
	@Autowired
	private MentorRepo mentorRepo;
	
	@Autowired
	private StudentRepo studentRepo;

	
	public void addCourse(CoursePost course) {
		Mentor mentor = mentorRepo.findById(course.getMentorUsername()).orElseThrow();
		Course courseNew = Course.builder().mentor(mentor).nameCourse(course.getNameCourse()).build();
		
		mentor.getCourses().add(courseNew);
		courseRepo.save(courseNew);
		mentorRepo.save(mentor);
	}

	public CourseGet getCourse(Long id) {
		Course course = courseRepo.findById(id).orElseThrow();
		Set<StudentGet> students = course.getStudents().stream()
				.map(st->new StudentGet(st.getUsername(), st.getFullName(), st.getAddress()))
				.collect(Collectors.toSet());
		
		return new CourseGet(course.getId(), course.getNameCourse(), new GetMentor(course.getMentor().getUsername(), course.getMentor().getFullName()), students);
	}

	public Set<CourseGet> getAll() {
		Set<Course> courses = courseRepo.getAll();
		Set<CourseGet> coursesGet = courses.stream().map(co->getCourse(co.getId())).collect(Collectors.toSet());
		return coursesGet;
	}



	public String deleteCourse(Long id) {
		Course course = courseRepo.findById(id).orElseThrow();
		Mentor mentor = course.getMentor();
		Set<Student> students = course.getStudents();
		if(!(mentor==null)) {
			mentor.getCourses().remove(course);
		}
		
		if(!(students==null)) {
			students.forEach(st->{
				st.getCourses().remove(course);
				studentRepo.save(st);
			});
		}
		
		courseRepo.delete(course);
		return "Successfully course deleted";
		
	}

	public String changeMentor(Long courseId,String username) {
	Course course =	courseRepo.findById(courseId).orElseThrow();
	Mentor currentMentor = course.getMentor();
	if(!(currentMentor==null)) {
		currentMentor.getCourses().remove(course);
		mentorRepo.save(currentMentor);
	}
	course.setMentor(mentorRepo.findById(username).orElseThrow());
	courseRepo.save(course);
		return "Successfully mentor changed";
	}
	
	
	
	
}
