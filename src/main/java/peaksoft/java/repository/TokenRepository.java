package peaksoft.java.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.RefreshToken;
import peaksoft.java.entity.User;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

}
