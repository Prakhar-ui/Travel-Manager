package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to destinations.
 */
@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Adds a new destination.
     *
     * @param destination The DTO containing destination information.
     * @return The newly created destination.
     */
    public Destination addDestination(DestinationDTO destination) {
        Destination newDestination = new Destination();
        newDestination.setName(destination.getName());
        return destinationRepository.save(newDestination);
    }

    /**
     * Retrieves a destination by its ID.
     *
     * @param id The ID of the destination to retrieve.
     * @return The destination, if found; otherwise, null.
     */
    public Destination getDestinationById(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        return optionalDestination.orElse(null);
    }

    /**
     * Retrieves all destinations.
     *
     * @return A list of all destinations.
     */
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    /**
     * Updates an existing destination.
     *
     * @param id               The ID of the destination to update.
     * @param destinationDetails The DTO containing updated destination information.
     * @return The updated destination, if found; otherwise, null.
     */
    public Destination updateDestination(Long id, DestinationDTO destinationDetails) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isPresent()) {
            Destination existingDestination = optionalDestination.get();
            existingDestination.setName(destinationDetails.getName());

            if (destinationDetails.getActivitiesID() != null && !destinationDetails.getActivitiesID().isEmpty()) {
                List<Activity> activities = activityRepository.findAllById(destinationDetails.getActivitiesID());
                existingDestination.setActivities(activities);
            }

            return destinationRepository.save(existingDestination);
        } else {
            return null;
        }
    }

    /**
     * Deletes a destination by its ID.
     *
     * @param id The ID of the destination to delete.
     * @return True if the destination was deleted successfully; otherwise, false.
     */
    public boolean deleteDestination(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isPresent()) {
            destinationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

