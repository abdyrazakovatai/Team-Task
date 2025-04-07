package peaksoft.java.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.java.enums.Role;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    Long id;
    @Column(name = "username")
    String userName;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(name = "created_at")
    LocalDate createdAt;
    @Column(name = "updated_at")
    LocalDate updatedAt;

    public User(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public String getUserName() {
        return userName;
    }
}
