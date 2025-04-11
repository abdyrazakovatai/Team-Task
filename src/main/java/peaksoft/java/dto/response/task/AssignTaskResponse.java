package peaksoft.java.dto.response.task;

import peaksoft.java.enums.Status;

import java.time.LocalDate;

public record AssignTaskResponse(
        Long userId,
        String username,
        String email,
        Long taskId,
        String title,
        String category,
        Status status,
        LocalDate deadline
) {
}
