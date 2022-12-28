package mk.oleg.java.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mk.oleg.java.dto.UserDto;
import mk.oleg.java.model.AcademyUser;
import mk.oleg.java.model.Role;
import mk.oleg.java.service.AcademyUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {
	
	private final AcademyUserService userService;
	
	@PreAuthorize("hasAnyRole('STUDENT','MENTOR','DIRECTOR')")
	@GetMapping("/getUser")
	ResponseEntity<AcademyUser> getUser(@RequestParam String username){
		
		return ResponseEntity.ok().body(userService.getUser(username));
	}
	
	@PostMapping("/addStudentUser")
	ResponseEntity<String> addStudentUser(@RequestBody UserDto userDto){
		
		return ResponseEntity.ok().body(userService.addUser(userDto,Role.STUDENT));
		
	}
	
	@PreAuthorize("hasAuthority('UserMentorInsert')")
	@PostMapping("/addMentorUser")
	ResponseEntity<String> addMentorUser(@RequestBody UserDto userDto){
		
		return ResponseEntity.ok().body(userService.addUser(userDto,Role.MENTOR));
		
	}
	
	@PutMapping("/addRole")
	ResponseEntity<String> addRoleToUser(@RequestParam String username, @RequestParam String role){
		return ResponseEntity.ok().body(userService.addRole(username,role));
	}
	
	@PutMapping("/removeRole")
	ResponseEntity<String> removeRoleToUser(@RequestParam String username, @RequestParam String role){
		return ResponseEntity.ok().body(userService.removeRole(username,role));
	}
	

}
