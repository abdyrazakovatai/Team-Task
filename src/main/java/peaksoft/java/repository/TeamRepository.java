package peaksoft.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.Team;
import peaksoft.java.exception.NotFoundException;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findById(Long id);

    @Modifying
    @Query("DELETE FROM TeamMembers tm WHERE tm.team.id = :id")
    void deleteTeamMembersByTeamId(Long id);

    @Modifying
    @Query("DELETE FROM Team t WHERE t.id = :id")
    void delete(Long id);

    @Modifying
    @Query("DELETE FROM TeamMembers t WHERE t.user.id = :id")
    void deleteUserById(Long id);

    default Team getTeamById(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Team with id: " + id + "not found"));
    }
}
