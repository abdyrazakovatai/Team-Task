package peaksoft.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.Team;
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
