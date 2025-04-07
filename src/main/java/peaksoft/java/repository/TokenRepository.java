package peaksoft.java.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.RefreshToken;
import peaksoft.java.entity.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser_Id(Long userId);

    void deleteByUser(User user);

    void deleteByUser_Id(Long userId);
}
