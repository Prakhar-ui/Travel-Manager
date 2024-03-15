package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to activities.
 */
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private DestinationRepository destinationRepository;

    /**
     * Adds a new activity.
     *
     * @param activityDTO The DTO containing activity details.
     * @return The newly created activity.
     */
    public Activity addActivity(ActivityDTO activityDTO) {
        Activity newActivity = new Activity(activityDTO.getName(), activityDTO.getDescription(), activityDTO.getCost(), activityDTO.getCapacity());
        if (activityDTO.getDestinationId() != null) {
            Optional<Destination> destination = destinationRepository.findById(activityDTO.getDestinationId());
            destination.ifPresent(newActivity::setDestination);
        }
        return activityRepository.save(newActivity);
    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param id The ID of the activity to retrieve.
     * @return The activity if found, otherwise null.
     */
    public Activity getActivityById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.orElse(null);
    }

    /**
     * Retrieves all activities.
     *
     * @return A list of all activities.
     */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Updates an existing activity.
     *
     * @param id          The ID of the activity to update.
     * @param activityDTO The DTO containing updated activity details.
     * @return The updated activity if found, otherwise null.
     */
    public Activity updateActivity(Long id, ActivityDTO activityDTO) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity existingActivity = optionalActivity.get();
            existingActivity.setName(activityDTO.getName());
            existingActivity.setDescription(activityDTO.getDescription());
            existingActivity.setCost(activityDTO.getCost());
            existingActivity.setCapacity(activityDTO.getCapacity());
            if (activityDTO.getDestinationId() != null) {
                Optional<Destination> destination = destinationRepository.findById(activityDTO.getDestinationId());
                destination.ifPresent(existingActivity::setDestination);
            }
            return activityRepository.save(existingActivity);
        } else {
            return null;
        }
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param id The ID of the activity to delete.
     * @return True if the activity was deleted successfully, otherwise false.
     */
    public boolean deleteActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            activityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}