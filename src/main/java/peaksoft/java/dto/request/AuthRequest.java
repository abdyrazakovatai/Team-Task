package peaksoft.java.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthRequest(
        @NotBlank(message = "Username is not blank")
        String username,
        @Email(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$",
                message = "Invalid email format. Only end with .com")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.")
        String password,
        @NotBlank(message = "firstname is not blank")
        String firstName,
        @NotBlank(message = "lastname is not blank")
        String lastName
) {
}
