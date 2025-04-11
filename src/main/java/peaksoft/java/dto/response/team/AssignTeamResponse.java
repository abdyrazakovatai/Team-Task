package peaksoft.java.dto.response.team;

public record AssignTeamResponse(
        Long teamId,
        String teamName,
        Long userId,
        String username
) {
}
