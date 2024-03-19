package javaproject.travelmanager.Service.Implementation;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to destinations.
 * This service manages CRUD operations for destinations, including creating new destinations,
 * retrieving destinations by ID or all destinations, updating existing destinations,
 * and deleting destinations.
 */
@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final ActivityRepository activityRepository;

    /**
     * Constructs a new DestinationServiceImpl with the provided repositories.
     *
     * @param destinationRepository The repository for managing destination entities.
     * @param activityRepository    The repository for managing activity entities.
     */
    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository, ActivityRepository activityRepository) {
        this.destinationRepository = destinationRepository;
        this.activityRepository = activityRepository;
    }

    /**
     * Creates a new destination with the provided information.
     *
     * @param destinationDTO The DTO containing destination information.
     * @return The newly created destination.
     */
    @Override
    public Destination createDestination(@Valid  @NotNull DestinationDTO destinationDTO) {
        Destination newDestination = new Destination();
        newDestination.setName(destinationDTO.getName());
        List<Long> activitiesIds = destinationDTO.getActivitiesIDs();
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            List<Activity> activities = activityRepository.findAllById(activitiesIds);
            newDestination.setActivities(activities);
        }
        return destinationRepository.save(newDestination);
    }

    /**
     * Retrieves a destination by its ID.
     *
     * @param destinationId The ID of the destination to retrieve.
     * @return An Optional containing the destination if found; otherwise, an empty Optional.
     * @throws IllegalArgumentException if the provided destination ID is null.
     */
    @Override
    public Optional<Destination> getDestination(@NotNull Long destinationId) {
        return Optional.ofNullable(destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found.")));
    }

    /**
     * Retrieves all destinations.
     *
     * @return A list of all destinations.
     */
    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public List<Activity> getAllActivitiesFromDestination(@NotNull Long destinationId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
        Destination destination = destinationOptional.orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found."));
        return destination.getActivities();
    }

    @Override
    public Destination addActivityToDestination(@NotNull Long destinationId, @NotNull Long activityId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
        Destination destination = destinationOptional.orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found."));

        Optional<Activity> activity = activityRepository.findById(activityId);
        Activity activityToAdd = activity.orElseThrow(() -> new IllegalArgumentException("Activity with ID " + activityId + " not found."));

        destination.addActivity(activityToAdd);

        return destinationRepository.save(destination);
    }

    /**
     * Removes an activity association from the destination.
     *
     * @param destinationId The ID of the destination from which to remove the activity.
     * @param activityId    The ID of the activity to remove.
     * @throws IllegalArgumentException if the destination or activity is not found.
     */
    @Override
    public Destination removeActivityFromDestination(@NotNull Long destinationId, @NotNull Long activityId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
        Destination destination = destinationOptional.orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found."));

        Optional<Activity> activity = activityRepository.findById(activityId);
        Activity activityToRemove = activity.orElseThrow(() -> new IllegalArgumentException("Activity with ID " + activityId + " not found."));

        destination.removeActivity(activityId);

        return destinationRepository.save(destination);
    }


    /**
     * Updates an existing destination with the information provided in the DestinationDTO.
     *
     * @param destinationId               The ID of the destination to update.
     * @param destinationDTO   The DTO containing updated destination information.
     * @return The updated destination if found; otherwise, returns an empty Optional.
     * @throws IllegalArgumentException if the provided destination ID is null or if the destination with the given ID does not exist.
     */
    @Override
    public Optional<Destination> updateDestination(@NotNull Long destinationId, @Valid @NotNull DestinationDTO destinationDTO) {
        if (!destinationRepository.existsById(destinationId)) {
            throw new IllegalArgumentException("Destination with ID " + destinationId + " not found.");
        }

        return destinationRepository.findById(destinationId).map(existingDestination -> {
            existingDestination.setName(destinationDTO.getName());

            List<Long> activitiesIds = destinationDTO.getActivitiesIDs();
            if (activitiesIds != null && !activitiesIds.isEmpty()) {
                for (Long activityId : activitiesIds){
                    addActivityToDestination(destinationId, activityId);
                }
            }

            return destinationRepository.save(existingDestination);
        });
    }

    /**
     * Deletes a destination by its ID.
     *
     * @param destinationId The ID of the destination to delete.
     * @throws IllegalArgumentException if the provided destination ID is null or if the destination with the given ID does not exist.
     */
    @Override
    public void deleteDestination(@NotNull Long destinationId) {
        if (!destinationRepository.existsById(destinationId)) {
            throw new IllegalArgumentException("Destination with ID " + destinationId + " not found.");
        }
        destinationRepository.deleteById(destinationId);
    }
}
