package peaksoft.java.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.java.enums.Status;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_gen")
    @SequenceGenerator(name = "task_gen", sequenceName = "task_seq")
    Long id;
    String title;
    String description;
    Status status;
    int priority;
    String category;
    LocalDate deadline;
    LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    User assignedTo;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    Team team;
}
