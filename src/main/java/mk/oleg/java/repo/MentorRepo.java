package mk.oleg.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mk.oleg.java.model.Mentor;
@Repository
public interface MentorRepo extends JpaRepository<Mentor, String> {

}
