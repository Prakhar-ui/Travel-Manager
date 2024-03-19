package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;

import java.util.List;
import java.util.Optional;

/**
 * Service interface responsible for handling operations related to destinations.
 */
public interface DestinationService {

    /**
     * Creates a new destination.
     *
     * @param destinationDTO The DTO containing destination information.
     * @return The newly created destination.
     */
    Destination createDestination(DestinationDTO destinationDTO);

    /**
     * Retrieves a destination by its ID.
     *
     * @param destinationId The ID of the destination to retrieve.
     * @return The destination if found, otherwise empty.
     */
    Optional<Destination> getDestination(Long destinationId);

    /**
     * Retrieves all destinations.
     *
     * @return A list of all destinations.
     */
    List<Destination> getAllDestinations();

    List<Activity> getAllActivitiesFromDestination(Long destinationId);

    /**
     * Updates an existing destination.
     *
     * @param destinationId The ID of the destination to update.
     * @param destinationDTO The DTO containing updated destination information.
     * @return The updated destination if found, otherwise empty.
     */
    Optional<Destination> updateDestination(Long destinationId, DestinationDTO destinationDTO);

    /**
     * Deletes a destination by its ID.
     *
     * @param destinationId The ID of the destination to delete.
     */
    void deleteDestination(Long destinationId);

    Destination addActivityToDestination(Long destinationId, Long activityId);
    Destination removeActivityFromDestination(Long destinationId,Long activityId);

}