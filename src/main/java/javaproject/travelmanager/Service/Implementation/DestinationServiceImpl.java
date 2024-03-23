package javaproject.travelmanager.Service.Implementation;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Service.ActivityService;
import javaproject.travelmanager.Service.DestinationService;
import javaproject.travelmanager.Service.TravelPackageService;
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
    private final TravelPackageService travelPackageService;


    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository,
                                  ActivityService activityService,
                                  TravelPackageService travelPackageService) {
        this.destinationRepository = destinationRepository;
        this.activityService = activityService;
        this.travelPackageService = travelPackageService;
    }

    @Override
    public Destination createDestination(DestinationDTO destinationDTO) {
        String name = destinationDTO.getName();
        Long travelPackageId = destinationDTO.getTravelPackageId();
        List<Long> activitiesIds = destinationDTO.getActivitiesIds();
        Destination destination = new Destination();
        destination.setName(name);
        if (travelPackageId != null) {
            TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
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

    @Override
    public void addActivityToDestination(Long destinationId, Long activityId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        Activity activity = activityService.getActivity(activityId);
        destination.addActivity(activity);
    }

    @Override
    public void setTravelPackageToDestination(Long destinationId, Long travelPackageId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        destination.setTravelPackage(travelPackage);
    }

    @Override
    public Destination getDestination(Long destinationId) {
        return destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
    }

    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public List<Activity> getAllActivitiesFromDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        return destination.getActivities();
    }

    @Override
    public TravelPackage getTravelPackageFromDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        return destination.getTravelPackage();
    }

    @Override
    public void removeActivityFromDestination(Long destinationId, Long activityId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        Activity activity = activityService.getActivity(activityId);
        destination.removeActivity(activity);
    }

    @Override
    public void removeTravelPackageFromDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        destination.setTravelPackage(null);
    }

    @Override
    public void deleteDestination(Long destinationId) {
        destinationRepository.deleteById(destinationId);
    }
}
