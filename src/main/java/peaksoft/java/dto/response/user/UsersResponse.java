package peaksoft.java.dto.response.user;

public record UsersResponse(
        Long userId,
        String username,
        String email,
        String firstName,
        String lastName
) {
}
