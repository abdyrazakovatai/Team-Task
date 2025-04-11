package peaksoft.java.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import peaksoft.java.enums.Status;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "The title must not be empty.")
        String title,

        @NotBlank(message = "The description cannot be left blank.")
        String description,

        @NotNull(message = "Status is required and cannot be null.")
        Status status,

        @Max(value = 5, message = "Priority must be between 1 and 5.")
        @Min(value = 1, message = "Priority must be between 1 and 5.")
        int priority,

        @NotBlank(message = "Category cannot be empty.")
        String category,
        @NotNull(message = "Deadline cannot be null.")
        LocalDate deadline
) {
}
