package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import java.util.List;
import java.util.Optional;

/**
 * Service interface responsible for handling operations related to activities.
 */
public interface ActivityService {

    /**
     * Creates a new activity.
     *
     * @param activityDTO The DTO containing activity details.
     * @return The newly created activity.
     */
    Activity createActivity(ActivityDTO activityDTO);

    /**
     * Retrieves an activity by its ID.
     *
     * @param activityId The ID of the activity to retrieve.
     * @return The activity if found, otherwise empty.
     */
    Optional<Activity> getActivity(Long activityId);

    /**
     * Retrieves all activities.
     *
     * @return A list of all activities.
     */
    List<Activity> getAllActivities();

    /**
     * Updates an existing activity.
     *
     * @param activityId  The ID of the activity to update.
     * @param activityDTO The DTO containing updated activity details.
     * @return The updated activity if found, otherwise empty.
     */
    Optional<Activity> updateActivity(Long activityId, ActivityDTO activityDTO);

    /**
     * Deletes an activity by its ID.
     *
     * @param activityId The ID of the activity to delete.
     */
    void deleteActivity(Long activityId);
}