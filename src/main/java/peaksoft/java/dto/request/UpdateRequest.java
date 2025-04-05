package peaksoft.java.dto.request;

import peaksoft.java.enums.Role;

public record UpdateRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
