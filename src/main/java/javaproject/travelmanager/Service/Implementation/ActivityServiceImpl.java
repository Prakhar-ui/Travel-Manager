package javaproject.travelmanager.Service.Implementation;

import jakarta.validation.constraints.*;
import javaproject.travelmanager.DTO.*;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.*;
import javaproject.travelmanager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.*;
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


    private final DestinationRepository destinationRepository;
    private final ActivityRepository activityRepository;

    /**
     * Constructs a new DestinationServiceImpl with the provided repositories.
     *
     * @param destinationRepository The repository for managing destination entities.
     * @param activityRepository    The repository for managing activity entities.
     */
    @Autowired
    public ActivityServiceImpl(DestinationRepository destinationRepository, ActivityRepository activityRepository) {
        this.destinationRepository = destinationRepository;
        this.activityRepository = activityRepository;
    }

    /**
     * Adds a new activity.
     *
     * @param activityDTO The DTO containing activity details.
     * @return The newly created activity.
     * @throws IllegalArgumentException if any of the required parameters are missing or invalid.
     */
    @Override
    public Activity createActivity(@Valid @NotNull ActivityDTO activityDTO) {
        Activity newActivity = new Activity(activityDTO.getName(), activityDTO.getDescription(), activityDTO.getCost(), activityDTO.getCapacity());

        if (activityDTO.getDestinationId() != null) {
            Optional<Destination> destinationOptional = destinationRepository.findById(activityDTO.getDestinationId());
            Destination destination = destinationOptional.orElseThrow(() -> new IllegalArgumentException("Destination with ID " + activityDTO.getDestinationId() + " not found."));
            newActivity.setDestination(destination);
        }

        return activityRepository.save(newActivity);

    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param id The ID of the activity to retrieve.
     * @return The activity if found, otherwise null.
     */
    @Override
    public Optional<Activity> getActivity(@NotNull Long id) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Activity with ID " + id + " not found.");
        }
        return activityRepository.findById(id);
    }

    /**
     * Retrieves all activities.
     *
     * @return A list of all activities.
     */
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Updates an existing activity.
     *
     * @param id          The ID of the activity to update.
     * @param activityDTO The DTO containing updated activity details.
     * @return The updated activity if found, otherwise null.
     * @throws IllegalArgumentException if the provided DestinationDTO is null.
     */
    public Optional<Activity> updateActivity(@NotNull Long id, @Valid @NotNull ActivityDTO activityDTO) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Activity with ID " + id + " not found.");
        }
        return activityRepository.findById(id).map(existingActivity -> {
            existingActivity.setName(activityDTO.getName());
            existingActivity.setDescription(activityDTO.getDescription());
            existingActivity.setCost(activityDTO.getCost());
            existingActivity.setCapacity(activityDTO.getCapacity());

            if (activityDTO.getDestinationId() != null) {
                Destination destination = destinationRepository.findById(activityDTO.getDestinationId())
                        .orElseThrow(() -> new IllegalArgumentException("Destination with ID " + activityDTO.getDestinationId() + " not found."));
                existingActivity.setDestination(destination);
            }

            return activityRepository.save(existingActivity);
        });
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param id The ID of the activity to delete.
     */
    @Override
    public void deleteActivity(@NotNull Long id) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Activity with ID " + id + " not found.");
        }
        activityRepository.deleteById(id);
    }
}
