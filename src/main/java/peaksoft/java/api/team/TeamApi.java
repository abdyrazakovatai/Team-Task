package peaksoft.java.api.team;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.TeamRequest;
import peaksoft.java.dto.response.*;
import peaksoft.java.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamApi {
    final TeamService teamService;

    //todo POST /api/teams - Жаңы команда түзүү (ЖЕТЕКЧИ, АДМИН гана)
    @Secured({"ADMIN","MANAGER"})
    @PostMapping
    public SimpleResponse newTeam(@RequestBody TeamRequest teamRequest) {
        return teamService.newTeam(teamRequest);
    }

    //todo GET /api/teams - Командалардын тизмесин алуу
    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping
    public List<TeamsResponse> teams() {
        return teamService.teams();
    }

    //todo GET /api/teams/{id} - Команда жөнүндө маалымат алууv
    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping("/{id}")
    public TeamResponse team(@PathVariable Long id) {
        return teamService.team(id);
    }

    //todo PUT /api/teams/{id} - Команданы жаңыртуу (ЖЕТЕКЧИ, АДМИН гана)
    @Secured({"ADMIN","MANAGER"})
    @PutMapping("/{id}")
    public TeamResponse updateTeam(@PathVariable Long id,
                                   @RequestBody TeamRequest teamRequest) {
        return teamService.update(id, teamRequest);
    }

    //todo DELETE /api/teams/{id} - Команданы өчүрүү (АДМИН гана)
    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteTeam(@PathVariable Long id) {
        return teamService.delete(id);
    }

    //todo POST /api/teams/{id}/members - Командага мүчө кошуу (ЖЕТЕКЧИ, АДМИН гана)
    @Secured({"ADMIN","MANAGER"})
    @PostMapping("/{id}")
    public AssignTeamResponse assign(@PathVariable Long id,
                                     @RequestParam Long userId) {
        return teamService.assign(id, userId);
    }

    //todo DELETE /api/teams/{id}/members/{userId} - Командадан мүчөнү алып салуу (ЖЕТЕКЧИ,АДМИН гана)
    @Secured({"ADMIN","MANAGER"})
    @DeleteMapping("/{id}/members/{userId}")
    public SimpleResponse deleteAssign(@PathVariable Long id,
                                       @PathVariable Long userId) {
        return teamService.deleteMember(id,userId);
    }
}
