package mk.oleg.java.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mk.oleg.java.model.Course;
@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

	@Query("SELECT co FROM Course co LEFT JOIN FETCH co.students st")
	Set<Course> getAll();

	@Query("SELECT co FROM Course co LEFT JOIN FETCH co.students st WHERE co.id = :id")
	Course getCourse(@Param("id") Long id);

}
