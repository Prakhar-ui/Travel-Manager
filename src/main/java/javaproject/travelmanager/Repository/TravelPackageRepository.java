package javaproject.travelmanager.Repository;

import javaproject.travelmanager.Entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing TravelPackage entities.
 */
@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
}
