package mk.oleg.java.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mk.oleg.java.dto.MentorPost;
import mk.oleg.java.model.Course;
import mk.oleg.java.model.Mentor;
import mk.oleg.java.repo.MentorRepo;

@Service
@RequiredArgsConstructor
public class MentorService  {
	
	private final MentorRepo mentorRepo;

	public String addMentor(MentorPost mentor) {
		mentorRepo.save(new Mentor(mentor.getUsername(),mentor.getFullName()));
		return "Successfully added mentor";
	}

	public String  deleteMentor(String username) {
		Mentor mentor = mentorRepo.findById(username).orElseThrow();
		Set<Course> courses = mentor.getCourses();
		if(courses!=null) {
		mentor.getCourses().forEach(co->co.setMentor(null));
		}
		mentorRepo.delete(mentor);
		return "mentor is deleted";
	}
	
	

}
