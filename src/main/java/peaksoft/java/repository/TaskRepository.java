package peaksoft.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
