package peaksoft.java.dto.response;

public record AssignTeamResponse(
        Long teamId,
        String teamName,
        Long userId,
        String username
) {
}
