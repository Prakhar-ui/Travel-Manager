package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;

import java.util.List;

/**
 * Service interface responsible for handling operations related to travel packages.
 */
public interface TravelPackageService {

    /**
     * Creates a new travel package based on the provided DTO.
     * @param travelPackageDTO The DTO containing information to create the travel package.
     * @return The newly created travel package.
     */
    TravelPackage createTravelPackage(TravelPackageDTO travelPackageDTO);

    /**
     * Updates an existing travel package with the provided DTO.
     * @param travelPackageId The ID of the travel package to update.
     * @param travelPackageDTO The DTO containing updated information for the travel package.
     * @return The updated travel package.
     */
    TravelPackage updateTravelPackage(Long travelPackageId, TravelPackageDTO travelPackageDTO);

    /**
     * Adds a destination to the specified travel package.
     * @param travelPackageId The ID of the travel package to which the destination will be added.
     * @param destinationId The ID of the destination to add.
     */
    void addDestinationToTravelPackage(Long travelPackageId, Long destinationId);

    /**
     * Adds a passenger to the specified travel package.
     * @param travelPackageId The ID of the travel package to which the passenger will be added.
     * @param passengerId The ID of the passenger to add.
     */
    void addPassengerToTravelPackage(Long travelPackageId, Long passengerId);

    /**
     * Retrieves the travel package with the specified ID.
     * @param travelPackageId The ID of the travel package to retrieve.
     * @return The travel package with the specified ID, if found.
     */
    TravelPackage getTravelPackage(Long travelPackageId);

    /**
     * Retrieves all travel packages.
     * @return A list of all travel packages.
     */
    List<TravelPackage> getAllTravelPackages();

    /**
     * Retrieves all passengers associated with the specified travel package.
     * @param travelPackageId The ID of the travel package.
     * @return A list of all passengers associated with the specified travel package.
     */
    List<Passenger> getAllPassengers(Long travelPackageId);

    /**
     * Retrieves all destinations associated with the specified travel package.
     * @param travelPackageId The ID of the travel package.
     * @return A list of all destinations associated with the specified travel package.
     */
    List<Destination> getAllDestinations(Long travelPackageId);

    /**
     * Removes a destination from the specified travel package.
     * @param travelPackageId The ID of the travel package from which the destination will be removed.
     * @param destinationId The ID of the destination to remove.
     */
    void removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId);

    /**
     * Removes a passenger from the specified travel package.
     * @param travelPackageId The ID of the travel package from which the passenger will be removed.
     * @param passengerId The ID of the passenger to remove.
     */
    void removePassengerFromTravelPackage(Long travelPackageId, Long passengerId);

    /**
     * Deletes the travel package with the specified ID.
     * @param travelPackageId The ID of the travel package to delete.
     */
    void deleteTravelPackage(Long travelPackageId);
}
