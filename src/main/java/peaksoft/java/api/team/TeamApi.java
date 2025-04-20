package peaksoft.java.api.team;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.TeamRequest;
import peaksoft.java.dto.response.team.AssignTeamResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.team.TeamResponse;
import peaksoft.java.dto.response.team.TeamsResponse;
import peaksoft.java.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamApi {
    final TeamService teamService;

    @Secured({"ADMIN","MANAGER"})
    @PostMapping
    public SimpleResponse newTeam(@Valid @RequestBody TeamRequest teamRequest) {
        return teamService.newTeam(teamRequest);
    }

    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping
    public List<TeamsResponse> teams() {
        return teamService.teams();
    }

    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping("/{id}")
    public TeamResponse team(@PathVariable Long id) {
        return teamService.team(id);
    }

    @Secured({"ADMIN","MANAGER"})
    @PutMapping("/{id}")
    public TeamResponse updateTeam(@PathVariable Long id,
                                   @Valid @RequestBody TeamRequest teamRequest) {
        return teamService.update(id, teamRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteTeam(@PathVariable Long id) {
        return teamService.delete(id);
    }

    @Secured({"ADMIN","MANAGER"})
    @PostMapping("/{id}")
    public AssignTeamResponse assign(@PathVariable Long id,
                                     @RequestParam Long userId) {
        return teamService.assign(id, userId);
    }

    @Secured({"ADMIN","MANAGER"})
    @DeleteMapping("/{id}/members/{userId}")
    public SimpleResponse deleteAssign(@PathVariable Long id,
                                       @PathVariable Long userId) {
        return teamService.deleteMember(id,userId);
    }
}
