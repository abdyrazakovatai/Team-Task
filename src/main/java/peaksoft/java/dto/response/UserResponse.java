package peaksoft.java.dto.response;

import peaksoft.java.enums.Role;

public record UserResponse(
        Long userId,
        String username,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
