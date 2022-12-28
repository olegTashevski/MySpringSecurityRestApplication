package mk.oleg.java.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mk.oleg.java.dto.StudentGet;
import mk.oleg.java.model.Course;
import mk.oleg.java.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

	@Query("SELECT  new mk.oleg.java.dto.StudentGet(st.username,st.fullName,st.address) FROM Student st  WHERE st.username = :id")
	public StudentGet getStudent(@Param("id") String studentUsername);

	@Query("SELECT  new mk.oleg.java.dto.StudentGet(st.username,st.fullName,st.address) FROM Student st")
	public Set<StudentGet> getAll();

	@Query("SELECT co From Student st JOIN  st.courses co WHERE st.username = :id")
	public Set<Course> getStudentCourses(@Param("id") String id);
	
}
