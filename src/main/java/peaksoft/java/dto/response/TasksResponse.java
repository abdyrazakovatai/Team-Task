package peaksoft.java.dto.response;

import peaksoft.java.enums.Status;

import java.time.LocalDate;

public record TasksResponse(
        String title,
        String description,
        Status status,
        int priority,
        String category,
        LocalDate deadline,
        LocalDate createdAt
) {
}
