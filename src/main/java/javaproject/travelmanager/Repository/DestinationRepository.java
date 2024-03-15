package javaproject.travelmanager.Repository;

import javaproject.travelmanager.Entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Destination entities in the database.
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
