//package peaksoft.java.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "team_members")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class TeamMembers {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_gen")
//    @SequenceGenerator(name = "teams_gen", sequenceName = "teams_seq", allocationSize = 1)
//    Long id;
//    @Column(name = "joined_at")
//    LocalDate joinedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    Team team;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    User user;
//}
