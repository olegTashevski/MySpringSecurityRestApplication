package mk.oleg.java.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mk.oleg.java.model.AcademyUser;
@Repository
public interface UserAcademyRepo extends JpaRepository<AcademyUser, String> {

	@Query("SELECT user FROM AcademyUser user LEFT JOIN FETCH user.roles roles WHERE user.username=:username")
	Optional<AcademyUser> getAcademyUser(@Param("username") String username);
	

}
