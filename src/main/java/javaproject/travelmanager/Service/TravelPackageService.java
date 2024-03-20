package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;

import java.util.List;
import java.util.Optional;

/**
 * Service interface responsible for handling operations related to travel packages.
 */
public interface TravelPackageService {

    /**
     * Creates a new travel package.
     *
     * @param travelPackageDTO The DTO containing travel package information.
     * @return The newly created travel package.
     */
    TravelPackage createTravelPackage(TravelPackageDTO travelPackageDTO);

    /**
     * Retrieves a travel package by its ID.
     *
     * @param travelPackageId The ID of the travel package to retrieve.
     * @return The travel package if found, otherwise empty.
     */
    Optional<TravelPackage> getTravelPackage(Long travelPackageId);

    /**
     * Retrieves all travel packages.
     *
     * @return A list of all travel packages.
     */
    List<TravelPackage> getAllTravelPackages();

    List<Passenger> getAllPassengers(Long travelPackageId);

    List<Destination> getAllDestinations(Long travelPackageId);

    /**
     * Updates an existing travel package.
     *
     * @param travelPackageId  The ID of the travel package to update.
     * @param travelPackageDTO The DTO containing updated travel package information.
     * @return The updated travel package if found, otherwise empty.
     */
    Optional<TravelPackage> updateTravelPackage(Long travelPackageId, TravelPackageDTO travelPackageDTO);

    /**
     * Adds multiple destinations to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId   The ID of the destination to add.
     */
    TravelPackage addDestinationToTravelPackage(Long travelPackageId, Long destinationId);

    /**
     * Removes a destination from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId   The ID of the destination to remove.
     */
    TravelPackage removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId);


    /**
     * Adds a single passenger to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger to add.
     * @return
     */
    TravelPackage addPassengerToTravelPackage(Long travelPackageId, Long passengerId);

    /**
     * Removes a passenger from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger to remove.
     */
    TravelPackage removePassengerFromTravelPackage(Long travelPackageId, Long passengerId);

    /**
     * Deletes a travel package by its ID.
     *
     * @param travelPackageId The ID of the travel package to delete.
     */
    void deleteTravelPackage(Long travelPackageId);

}