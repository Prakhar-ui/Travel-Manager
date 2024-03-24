package javaproject.travelmanager.Service.Implementation;

import javaproject.travelmanager.DTO.*;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.*;
import javaproject.travelmanager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to activities.
 * This service manages CRUD operations for activities, including creating new activities,
 * retrieving activities by ID or all activities, updating existing activities,
 * and deleting activities.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {


    private final ActivityRepository activityRepository;
    private final DestinationRepository destinationRepository;

    @Autowired
    public ActivityServiceImpl( ActivityRepository activityRepository, DestinationRepository destinationRepository) {
        this.activityRepository = activityRepository;
        this.destinationRepository = destinationRepository;
    }


    /**
     * Creates a new activity based on the provided activity DTO.
     * @param activityDTO The DTO containing information about the activity.
     * @return The created activity.
     * @throws IllegalArgumentException if the specified destination ID in the DTO does not exist.
     */
    @Override
    public Activity createActivity(ActivityDTO activityDTO) {
        String name = activityDTO.getName();
        String description = activityDTO.getDescription();
        int capacity = activityDTO.getCapacity();
        double cost = activityDTO.getCost();
        Long destinationId = activityDTO.getDestinationId();

        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setCapacity(capacity);
        activity.setCost(cost);
        if (destinationId != null){
            Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
            activity.setDestination(destination);
        }
        return activityRepository.save(activity);
    }

    /**
     * Updates an existing activity with the provided ID using information from the activity DTO.
     * @param activityId The ID of the activity to update.
     * @param activityDTO The DTO containing updated information about the activity.
     * @return The updated activity.
     * @throws IllegalArgumentException if the activity with the specified ID is not found.
     */
    @Override
    public Activity updateActivity(Long activityId, ActivityDTO activityDTO) {
        String name = activityDTO.getName();
        String description = activityDTO.getDescription();
        int capacity = activityDTO.getCapacity();
        double cost = activityDTO.getCost();
        Long destinationId = activityDTO.getDestinationId();

        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new IllegalArgumentException("Activity Not present"));

        activity.setName(name);
        activity.setDescription(description);
        activity.setCapacity(capacity);
        activity.setCost(cost);
        if (destinationId != null){
            setDestinationToActivity(activity.getId(),destinationId);
        }
        return activityRepository.save(activity);
    }

    /**
     * Associates a destination with the specified activity.
     * @param activityId The ID of the activity.
     * @param destinationId The ID of the destination to associate with the activity.
     * @throws IllegalArgumentException if either the activity or destination with the specified IDs is not found.
     */
    @Override
    public void setDestinationToActivity(Long activityId, Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination Not Found"));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity Not Found"));

        activity.setDestination(destination);
        activityRepository.save(activity);
    }

    /**
     * Retrieves the activity with the specified ID.
     * @param activityId The ID of the activity to retrieve.
     * @return The activity corresponding to the provided ID.
     * @throws IllegalArgumentException if no activity is found with the provided ID.
     */
    @Override
    public Activity getActivity(Long activityId) {
        return  activityRepository.findById(activityId).orElseThrow(() -> new IllegalArgumentException("Activity Not present"));
    }

    /**
     * Retrieves all activities.
     * @return A list of all activities.
     */
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Retrieves the destination associated with the specified activity.
     * @param activityId The ID of the activity.
     * @return The destination associated with the activity.
     * @throws IllegalArgumentException if no activity is found with the provided ID.
     */
    @Override
    public Destination getDestinationFromActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new IllegalArgumentException("Activity Not present"));
        return activity.getDestination();
    }

    /**
     * Removes the associated destination from the specified activity.
     * @param activityId The ID of the activity from which to remove the destination.
     * @throws IllegalArgumentException if no activity is found with the provided ID.
     */
    @Override
    public void removeDestinationFromActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity Not Found"));

        activity.setDestination(null);
        activityRepository.save(activity);
    }

    /**
     * Deletes the activity with the specified ID.
     * @param activityId The ID of the activity to delete.
     */
    @Override
    public void deleteActivity(Long activityId) {
        activityRepository.deleteById(activityId);
    }
}
