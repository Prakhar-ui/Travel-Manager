package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Passenger;

import java.util.List;
import java.util.Optional;

/**
 * Service interface responsible for handling operations related to passengers.
 */
public interface PassengerService {

    /**
     * Creates a new passenger.
     *
     * @param passengerDTO The DTO containing passenger information.
     * @return The newly created passenger.
     */
    Passenger createPassenger(PassengerDTO passengerDTO);

    /**
     * Retrieves a passenger by its ID.
     *
     * @param passengerId The ID of the passenger to retrieve.
     * @return The passenger if found, otherwise empty.
     */
    Optional<Passenger> getPassenger(Long passengerId);

    /**
     * Retrieves all passengers.
     *
     * @return A list of all passengers.
     */
    List<Passenger> getAllPassengers();

    List<Activity> getAllActivitiesFromPassenger(Long passengerId);



    /**
     * Updates an existing passenger.
     *
     * @param passengerId The ID of the passenger to update.
     * @param passengerDTO The DTO containing updated passenger information.
     * @return The updated passenger if found, otherwise empty.
     */
    Optional<Passenger> updatePassenger(Long passengerId, PassengerDTO passengerDTO);

    /**
     * Adds an activity to a passenger.
     *
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to add.
     */
    Passenger addActivityToPassenger(Long passengerId, Long activityId);

    /**
     * Removes an activity from a passenger.
     *
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to remove.
     */
    Passenger removeActivityFromPassenger( Long passengerId, Long activityId);

    /**
     * Adds an activity to a passenger.
     *
     * @param passengerId The ID of the passenger.
     * @param travelPackageId The ID of the activity to add.
     */
    Passenger addTravelPackageToPassenger(Long passengerId, Long travelPackageId);


    /**
     * Removes an activity from a passenger.
     *
     * @param passengerId The ID of the passenger.
     * @param travelPackageId The ID of the activity to remove.
     */
    Passenger removeTravelPackageFromPassenger(Long passengerId, Long travelPackageId);

    /**
     * Deletes a passenger by its ID.
     *
     * @param passengerId The ID of the passenger to delete.
     */
    void deletePassenger(Long passengerId);
}