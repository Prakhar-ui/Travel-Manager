package javaproject.travelmanager.Service;


import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;
import java.util.List;

/**
 * Service interface responsible for handling operations related to passengers.
 */
public interface PassengerService {

    /**
     * Creates a new passenger based on the provided passenger DTO.
     * @param passengerDTO The DTO containing information about the passenger.
     * @return The created passenger.
     */
    Passenger createPassenger(PassengerDTO passengerDTO);

    /**
     * Updates an existing passenger with the provided ID using information from the passenger DTO.
     * @param passengerId The ID of the passenger to update.
     * @param passengerDTO The DTO containing updated information about the passenger.
     * @return The updated passenger.
     */
    Passenger updatePassenger(Long passengerId, PassengerDTO passengerDTO);

    /**
     * Adds an activity to the specified passenger.
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to add to the passenger.
     */
    void addActivityToPassenger(Long passengerId, Long activityId);

    /**
     * Associates a travel package with the specified passenger.
     * @param passengerId The ID of the passenger.
     * @param travelPackageId The ID of the travel package to associate with the passenger.
     */
    void setTravelPackageToPassenger(Long passengerId, Long travelPackageId);

    /**
     * Retrieves the passenger with the specified ID.
     * @param passengerId The ID of the passenger to retrieve.
     * @return The passenger corresponding to the provided ID.
     */
    Passenger getPassenger(Long passengerId);

    /**
     * Retrieves all passengers.
     * @return A list of all passengers.
     */
    List<Passenger> getAllPassengers();

    /**
     * Retrieves all activities associated with the specified passenger.
     * @param passengerId The ID of the passenger.
     * @return A list of all activities associated with the passenger.
     */
    List<Activity> getAllActivitiesFromPassenger(Long passengerId);

    /**
     * Retrieves the travel package associated with the specified passenger.
     * @param passengerId The ID of the passenger.
     * @return The travel package associated with the passenger.
     */
    TravelPackage getTravelPackageFromPassenger(Long passengerId);

    /**
     * Removes the association of an activity from the specified passenger.
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to remove from the passenger.
     */
    void removeActivityFromPassenger(Long passengerId, Long activityId);

    /**
     * Removes the association of a travel package from the specified passenger.
     * @param passengerId The ID of the passenger.
     */
    void removeTravelPackageFromPassenger(Long passengerId);

    /**
     * Deletes the passenger with the specified ID.
     * @param passengerId The ID of the passenger to delete.
     */
    void deletePassenger(Long passengerId);
}