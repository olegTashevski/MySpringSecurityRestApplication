package mk.oleg.java.dto;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.oleg.java.model.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserDto {
	private String fullName;
	
	private String username;
	private String password;

}
