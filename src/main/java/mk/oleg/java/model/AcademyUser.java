package mk.oleg.java.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AcademyUser {
	
	private String fullName;
	@Id
	@Column(length = 20)
	private String username;
	private String password;
	@ElementCollection(targetClass = Role.class)
	@CollectionTable(name = "roles_of_users",joinColumns = {@JoinColumn(name="username")})
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

}
