package peaksoft.java.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "resfresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_gen")
    @SequenceGenerator(name = "token_gen", sequenceName = "token_seq", allocationSize = 1, initialValue = 1)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    String token;
    LocalDate expiryDate;
    boolean isRevoked = false;
}
