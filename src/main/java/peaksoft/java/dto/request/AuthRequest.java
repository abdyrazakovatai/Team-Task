package peaksoft.java.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Username is not blank")
        String username,
        
        @Email(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$",
                message = "Invalid email format. Only end with .com")
        String email,
        
        // Password
        String password,
        @NotBlank(message = "firstname is not blank")
        String firstName,
        @NotBlank(message = "lastname is not blank")
        String lastName
) {
}
