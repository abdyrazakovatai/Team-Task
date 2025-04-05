package peaksoft.java.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "team_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_gen")
    @SequenceGenerator(name = "teams_gen", sequenceName = "teams_seq")
    Long id;
    @Column(name = "joined_at")
    LocalDate joinedAt;

    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    Team team;

    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    List<User> users;

}
