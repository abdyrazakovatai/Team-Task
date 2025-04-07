package peaksoft.java.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.java.dto.request.TeamRequest;
import peaksoft.java.dto.response.AssignTeamResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.TeamResponse;
import peaksoft.java.dto.response.TeamsResponse;
import peaksoft.java.entity.Team;
import peaksoft.java.entity.TeamMembers;
import peaksoft.java.entity.User;
import peaksoft.java.exception.AlreadyExistException;
import peaksoft.java.exception.NotFoundException;
import peaksoft.java.repository.TeamMembersRepository;
import peaksoft.java.repository.TeamRepository;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    final TeamRepository teamRepository;
    final UserRepository userRepository;
    final TeamMembersRepository teamMembersRepository;

    public SimpleResponse newTeam(TeamRequest teamRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findUserByEmail(currentUserEmail);

        teamRepository.save(Team.builder()
                .name(teamRequest.name())
                .description(teamRequest.description())
                .createdAt(LocalDate.now())
                .createdBy(currentUser)
                .build());

        return new SimpleResponse(
                HttpStatus.OK,
                "New team created"
        );
    }

    public List<TeamsResponse> teams() {
        List<Team> teams = teamRepository.findAll();

        List<TeamsResponse> teamsResponses = teams.stream().map(
                team -> new TeamsResponse(
                        team.getId(),
                        team.getName(),
                        team.getDescription())
        ).toList();
        return teamsResponses;
    }

    public TeamResponse team(Long id) {
        Team team = teamRepository.getTeamById(id);
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription()
        );
    }

    public TeamResponse update(Long id, TeamRequest teamRequest) {
        Team team = teamRepository.getTeamById(id);

        team.setName(teamRequest.name());
        team.setDescription(teamRequest.description());
        team.setUpdatedAt(LocalDate.now());
        Team save = teamRepository.save(team);
        return new TeamResponse(
                save.getId(),
                save.getName(),
                save.getDescription()
        );
    }

    @Transactional
    public SimpleResponse delete(Long id) {
        teamRepository.deleteTeamMembersByTeamId(id);
        teamRepository.delete(id);
        return new SimpleResponse(
                HttpStatus.OK,
                "Team deleted"
        );
    }

    public AssignTeamResponse assign(Long id, Long userId) {
        Team team = teamRepository.getTeamById(id);
        User user = userRepository.getUserById(userId);

        boolean exists = teamMembersRepository.existsByTeamIdAndUser_Id(team.getId(), userId);

        if (exists) {
            throw new AlreadyExistException("User is already a member of this team");
        }

        TeamMembers newTeam = TeamMembers.builder()
                .team(team)
                .user(user)
                .joinedAt(LocalDate.now())
                .build();
        String teamName = newTeam.getTeam().getName();

        teamMembersRepository.save(newTeam);

        return new AssignTeamResponse(
                team.getId(),
                teamName,
                user.getId(),
                user.getUserName()
        );
    }

    public SimpleResponse deleteMember(Long teamId, Long userId) {
        Team team = teamRepository.getTeamById(teamId);

        TeamMembers teamMember = teamMembersRepository.findByTeamIdAndUserId(team.getId(), userId);
        teamMembersRepository.delete(teamMember);

        return new SimpleResponse(
                HttpStatus.OK,
                "Team members deleted"
        );
    }
}
