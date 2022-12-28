package mk.oleg.java.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public enum Role {
	
	STUDENT(new HashSet<>(Arrays.asList(
			Permission.StudentDelete
			,Permission.StudentIsert
			,Permission.StudentEnroll
			,Permission.StudentDisenroll)))
	,MENTOR(new HashSet<>(Arrays.asList(
			Permission.MentorDelete)))
	,DIRECTOR(new HashSet<>(Arrays.asList(
			Permission.MentorInsert
			,Permission.MentorDelete
			,Permission.CourseAppendMentor
			,Permission.CourseChangeMentor
			,Permission.CourseCreate
			,Permission.CourseDelete
			,Permission.StudentDelete
			,Permission.UserMentorInsert)));
	
	
	private final  Set<Permission> permissons;
	
	public Set<? extends GrantedAuthority> getAuthorities(String username){
		Set<GrantedAuthority> authorities = new HashSet<>();
		this.permissons.forEach(per->authorities.add(new SimpleGrantedAuthority(per.name())));
		authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return authorities;
		
	}

}
