package javaproject.travelmanager.Repository;
import javaproject.travelmanager.Entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Activity entities.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
