package peaksoft.java.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import peaksoft.java.enums.Status;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        Status status,
        @Max(5) @Min(1)
        int priority,
        String category,
        LocalDate deadline
) {
}
