package mk.oleg.java.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mk.oleg.java.dto.UserDto;
import mk.oleg.java.exception.UsernameExistException;
import mk.oleg.java.model.AcademyUser;
import mk.oleg.java.model.Role;
import mk.oleg.java.repo.UserAcademyRepo;
@Service
@RequiredArgsConstructor
public class AcademyUserService implements UserDetailsService {
	
	private final UserAcademyRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AcademyUser user = getUser(username);
		Set<GrantedAuthority> authories = new HashSet<>();
		user.getRoles().forEach(role->authories.addAll(role.getAuthorities(username)));
		return new User(username, user.getPassword(), authories);
	}
	
	public AcademyUser getUser(String username) {
		// TODO Auto-generated method stub
		return userRepo.getAcademyUser(username).orElseThrow();
	}

	public String addUser(UserDto userDto,Role role) {
		String username = userDto.getUsername();
		if(userRepo.findById(username).isPresent()) {
			throw new UsernameExistException(username);
		}
		userRepo.save(new AcademyUser(userDto.getFullName(), username, passwordEncoder.encode(userDto.getPassword()), Set.of(role)));
		
		return "successfully added";
	}

	public String addRole(String username, String role) {
		AcademyUser academyUser = userRepo.findById(username).orElseThrow();
		academyUser.getRoles().add(convetStringToRole(role));
		return "Successfully added Role";
	}

	private Role convetStringToRole(String roleS) {
		Role role = switch (roleS) {
		case "STUDENT" -> Role.MENTOR;
		case "MENTOR" -> Role.MENTOR;
		case "DIRECTOR" -> Role.DIRECTOR;
		default -> throw new IllegalArgumentException("This String " + roleS + " is not Role");
		
		};
		return role;
	}

	public String removeRole(String username, String role) {
		AcademyUser academyUser = userRepo.findById(username).orElseThrow();
		academyUser.getRoles().remove(convetStringToRole(role));
		return "Successfully removed Role";
	}


}
