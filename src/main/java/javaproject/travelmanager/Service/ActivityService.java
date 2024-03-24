package javaproject.travelmanager.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;

import java.util.List;
import java.util.Optional;

/**
 * Service interface responsible for handling operations related to activities.
 */
public interface ActivityService {
    /**
     * Creates a new activity based on the provided activity DTO.
     * @param activityDTO The DTO containing information about the activity.
     * @return The created activity.
     */
    Activity createActivity(ActivityDTO activityDTO);

    /**
     * Updates an existing activity with the provided ID using information from the activity DTO.
     * @param activityId The ID of the activity to update.
     * @param activityDTO The DTO containing updated information about the activity.
     * @return The updated activity.
     */
    Activity updateActivity(Long activityId, ActivityDTO activityDTO);

    /**
     * Associates a destination with the specified activity.
     * @param activityId The ID of the activity.
     * @param destinationId The ID of the destination to associate with the activity.
     */
    void setDestinationToActivity(Long activityId, Long destinationId);

    /**
     * Retrieves the activity with the specified ID.
     * @param activityId The ID of the activity to retrieve.
     * @return The activity corresponding to the provided ID.
     * @throws IllegalArgumentException if no activity is found with the provided ID.
     */
    Activity getActivity(Long activityId);

    /**
     * Retrieves all activities.
     * @return A list of all activities.
     */
    List<Activity> getAllActivities();

    /**
     * Retrieves the destination associated with the specified activity.
     * @param activityId The ID of the activity.
     * @return The destination associated with the activity.
     * @throws IllegalArgumentException if no activity is found with the provided ID.
     */
    Destination getDestinationFromActivity(Long activityId);

    /**
     * Removes the associated destination from the specified activity.
     * @param activityId The ID of the activity from which to remove the destination.
     */
    void removeDestinationFromActivity(Long activityId);

    /**
     * Deletes the activity with the specified ID.
     * @param activityId The ID of the activity to delete.
     */
    void deleteActivity(Long activityId);
}
