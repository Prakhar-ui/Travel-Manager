package javaproject.travelmanager.Service.Implementation;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import javaproject.travelmanager.Service.ActivityService;
import javaproject.travelmanager.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final ActivityService activityService;
    private final TravelPackageRepository travelPackageRepository;


    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository,
                                  ActivityService activityService,
                                  TravelPackageRepository travelPackageRepository) {
        this.destinationRepository = destinationRepository;
        this.activityService = activityService;
        this.travelPackageRepository = travelPackageRepository;
    }

    /**
     * Creates a new destination based on the provided DTO.
     * @param destinationDTO The DTO containing information to create the destination.
     * @return The newly created destination.
     */
    @Override
    public Destination createDestination(DestinationDTO destinationDTO) {
        String name = destinationDTO.getName();
        Long travelPackageId = destinationDTO.getTravelPackageId();
        List<Long> activitiesIds = destinationDTO.getActivitiesIds();
        Destination destination = new Destination();
        destination.setName(name);
        if (travelPackageId != null) {
            TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
            destination.setTravelPackage(travelPackage);
        }
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for(Long activityId: activitiesIds){
                Activity activity = activityService.getActivity(activityId);
                destination.addActivity(activity);
            }
        }
        return destinationRepository.save(destination);
    }

    /**
     * Updates an existing destination with the provided DTO.
     * @param destinationId The ID of the destination to update.
     * @param destinationDTO The DTO containing updated information for the destination.
     * @return The updated destination.
     */
    @Override
    public Destination updateDestination(Long destinationId, DestinationDTO destinationDTO) {
        String name = destinationDTO.getName();
        Long travelPackageId = destinationDTO.getTravelPackageId();
        List<Long> activitiesIds = destinationDTO.getActivitiesIds();

        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));

        destination.setName(name);

        if (travelPackageId != null) {
            setTravelPackageToDestination(destinationId,travelPackageId);
        }
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for(Long activityId: activitiesIds){
                addActivityToDestination(destinationId,activityId);
            }
        }
        return destinationRepository.save(destination);
    }

    /**
     * Adds an activity to the specified destination.
     * @param destinationId The ID of the destination to which the activity will be added.
     * @param activityId The ID of the activity to add.
     */
    @Override
    public void addActivityToDestination(Long destinationId, Long activityId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination Not Found"));

        Activity activity = activityService.getActivity(activityId);

        if (activity == null) {
            throw new IllegalArgumentException("Activity Not Found");
        }

        destination.addActivity(activity);

        activityService.setDestinationToActivity(activityId, destinationId);
    }

    /**
     * Sets the travel package for the specified destination.
     * @param destinationId The ID of the destination to which the travel package will be set.
     * @param travelPackageId The ID of the travel package to set.
     */
    @Override
    public void setTravelPackageToDestination(Long destinationId, Long travelPackageId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination Not Found"));

        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        if (destination.getTravelPackage() != null) {
            throw new IllegalStateException("Destination already has an associated travel package");
        }

        destination.setTravelPackage(travelPackage);
    }

    /**
     * Retrieves the destination with the specified ID.
     * @param destinationId The ID of the destination to retrieve.
     * @return The destination with the specified ID, if found.
     */
    @Override
    public Destination getDestination(Long destinationId) {
        return destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
    }

    /**
     * Retrieves all destinations.
     * @return A list of all destinations.
     */
    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    /**
     * Retrieves all activities associated with the specified destination.
     * @param destinationId The ID of the destination.
     * @return A list of all activities associated with the specified destination.
     */
    @Override
    public List<Activity> getAllActivitiesFromDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        return destination.getActivities();
    }

    /**
     * Retrieves the travel package associated with the specified destination.
     * @param destinationId The ID of the destination.
     * @return The travel package associated with the specified destination, if any.
     */
    @Override
    public TravelPackage getTravelPackageFromDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        return destination.getTravelPackage();
    }

    /**
     * Removes an activity from the specified destination.
     * @param destinationId The ID of the destination from which the activity will be removed.
     * @param activityId The ID of the activity to remove.
     */
    @Override
    public void removeActivityFromDestination(Long destinationId, Long activityId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination Not Found"));

        Activity activity = activityService.getActivity(activityId);

        if (activity == null) {
            throw new IllegalArgumentException("Activity Not Found");
        }

        if (!destination.getActivities().contains(activity)) {
            throw new IllegalStateException("Activity is not associated with this destination");
        }

        destination.removeActivity(activity);

        activityService.removeDestinationFromActivity(activityId);
    }

    /**
     * Removes the travel package from the specified destination.
     * @param destinationId The ID of the destination from which the travel package will be removed.
     */
    @Override
    public void removeTravelPackageFromDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination Not Found"));

        if (destination.getTravelPackage() == null) {
            throw new IllegalStateException("Destination does not have an associated travel package");
        }

        destination.setTravelPackage(null);
    }

    /**
     * Deletes the destination with the specified ID.
     * @param destinationId The ID of the destination to delete.
     */
    @Override
    public void deleteDestination(Long destinationId) {
        destinationRepository.deleteById(destinationId);
    }
}
