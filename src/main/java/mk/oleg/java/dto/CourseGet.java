package mk.oleg.java.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseGet {

	private Long id;
	
	private String nameCourse;
	
	private GetMentor mentor;
	
	private Set<StudentGet> students;
}
