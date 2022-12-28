package mk.oleg.java.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.oleg.java.model.Mentor;
import mk.oleg.java.model.Student;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentPost {
	private String username;
	private String fullName;
	private String address;
	
}
