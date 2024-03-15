package javaproject.travelmanager.Repository;

import javaproject.travelmanager.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing passengers in the database.
 * Extends JpaRepository to provide basic CRUD operations for the Passenger entity.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
