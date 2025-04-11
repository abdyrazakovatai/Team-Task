package peaksoft.java.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TeamRequest(
        @NotBlank(message = "Name cannot be blank.")
        String name,

        @NotBlank(message = "Description cannot be blank.")
        String description
) {
}
