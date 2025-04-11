package peaksoft.java.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_gen")
    @SequenceGenerator(name = "team_gen", sequenceName = "team_seq",allocationSize = 1)
    Long id;
    String name;
    String description;

    @ManyToOne
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    User createdBy;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamMembers> teamMembers;

    @Column(name = "createdAt")
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

}