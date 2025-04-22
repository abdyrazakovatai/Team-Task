//package peaksoft.java.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import peaksoft.java.entity.Team;
//import peaksoft.java.entity.TeamMembers;
//import peaksoft.java.entity.User;
//
//@Repository
//public interface TeamMembersRepository extends JpaRepository<TeamMembers, Long> {
//    boolean existsByTeamIdAndUser_Id(Long teamId, Long userId);
//
//    TeamMembers findByTeamIdAndUserId(Long teamId, Long userId);
//
//    @Query("SELECT tm.team FROM TeamMembers tm WHERE tm.user.id = :id")
//    Team findByTeamByUser(Long id);
//}
