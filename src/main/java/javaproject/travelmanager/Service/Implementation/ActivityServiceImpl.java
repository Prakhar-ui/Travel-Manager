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

    @Override
    public void setDestinationToActivity(Long activityId, Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination Not present"));
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isPresent()){
            activity.get().setDestination(destination);
            activityRepository.save(activity.get());
        }
    }

    @Override
    public Activity getActivity(Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        return activity.get();
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Destination getDestinationFromActivity(Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        return activity.get().getDestination();
    }

    @Override
    public void removeDestinationFromActivity(Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isPresent()){
            activity.get().setDestination(null);
            activityRepository.save(activity.get());
        }
    }

    @Override
    public void deleteActivity(Long activityId) {
        activityRepository.deleteById(activityId);
    }
}
