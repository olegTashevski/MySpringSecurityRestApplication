package mk.oleg.java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsernameAndPasswordAuthenticationRequest {
	
	private String username;
	private String password;

}
