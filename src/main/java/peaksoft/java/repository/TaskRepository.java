//package peaksoft.java.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import peaksoft.java.entity.Task;
//import peaksoft.java.exception.NotFoundException;
//
//import java.util.Optional;
//
//@Repository
//public interface TaskRepository extends JpaRepository<Task, Long> {
//
//    Optional<Task> findById(Long id);
//
//    default Task getTaskById(Long id) {
//       return findById(id).orElseThrow(
//                () -> new NotFoundException("Task with id: " + id + " not found"));
//    }
//}
