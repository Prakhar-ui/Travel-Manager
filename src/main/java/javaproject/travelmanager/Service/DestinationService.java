package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.TravelPackage;

import java.util.List;

/**
 * Service interface responsible for handling operations related to destinations.
 */
public interface DestinationService {

    /**
     * Creates a new destination based on the provided destination DTO.
     * @param destinationDTO The DTO containing information about the destination.
     * @return The created destination.
     */
    Destination createDestination(DestinationDTO destinationDTO);

    /**
     * Updates an existing destination with the provided ID using information from the destination DTO.
     * @param destinationId The ID of the destination to update.
     * @param destinationDTO The DTO containing updated information about the destination.
     * @return The updated destination.
     */
    Destination updateDestination(Long destinationId, DestinationDTO destinationDTO);

    /**
     * Associates an activity with the specified destination.
     * @param destinationId The ID of the destination.
     * @param activityId The ID of the activity to associate with the destination.
     */
    void addActivityToDestination(Long destinationId, Long activityId);

    /**
     * Associates a travel package with the specified destination.
     * @param destinationId The ID of the destination.
     * @param travelPackageId The ID of the travel package to associate with the destination.
     */
    void setTravelPackageToDestination(Long destinationId, Long travelPackageId);

    /**
     * Retrieves the destination with the specified ID.
     * @param destinationId The ID of the destination to retrieve.
     * @return The destination corresponding to the provided ID.
     */
    Destination getDestination(Long destinationId);

    /**
     * Retrieves all destinations.
     * @return A list of all destinations.
     */
    List<Destination> getAllDestinations();

    /**
     * Retrieves all activities associated with the specified destination.
     * @param destinationId The ID of the destination.
     * @return A list of all activities associated with the destination.
     */
    List<Activity> getAllActivitiesFromDestination(Long destinationId);

    /**
     * Retrieves the travel package associated with the specified destination.
     * @param destinationId The ID of the destination.
     * @return The travel package associated with the destination.
     */
    TravelPackage getTravelPackageFromDestination(Long destinationId);

    /**
     * Removes the association of an activity from the specified destination.
     * @param destinationId The ID of the destination.
     * @param activityId The ID of the activity to remove from the destination.
     */
    void removeActivityFromDestination(Long destinationId, Long activityId);

    /**
     * Removes the association of a travel package from the specified destination.
     * @param destinationId The ID of the destination.
     */
    void removeTravelPackageFromDestination(Long destinationId);

    /**
     * Deletes the destination with the specified ID.
     * @param destinationId The ID of the destination to delete.
     */
    void deleteDestination(Long destinationId);

}