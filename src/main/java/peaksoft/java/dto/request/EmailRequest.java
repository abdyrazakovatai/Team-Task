package peaksoft.java.dto.request;

import jakarta.validation.constraints.Email;

public record EmailRequest(
        @Email(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$",
                message = "Invalid email format. Only end with .com")
        String email
)  {
}
