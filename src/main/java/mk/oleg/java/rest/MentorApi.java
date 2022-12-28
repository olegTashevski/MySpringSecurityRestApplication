package mk.oleg.java.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mk.oleg.java.dto.MentorPost;
import mk.oleg.java.service.MentorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentor")
public class MentorApi {
	
	private final MentorService mentorService;
	
	@PreAuthorize("hasAuthority('MentorInsert')")
	@PostMapping("/addMentor")
	ResponseEntity<String> addMentor(@RequestBody MentorPost mentor){
		
		return ResponseEntity.ok().body(mentorService.addMentor(mentor));
	}
	
	@PreAuthorize("hasAuthority('MentorDelete')")
	@DeleteMapping("/deleteMentor")
	ResponseEntity<String> deleteMentor(@RequestParam String username){
		return ResponseEntity.ok().body(mentorService.deleteMentor(username));
	}

}
