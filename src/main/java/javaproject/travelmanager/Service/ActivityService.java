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
    Activity createActivity(ActivityDTO activityDTO);

    Activity updateActivity(Long activityId, ActivityDTO activityDTO);

    void setDestinationToActivity(Long activityId, Long destinationId);

    Activity getActivity(Long activityId);

    List<Activity> getAllActivities();

    Destination getDestinationFromActivity(Long activityId);
    
    void removeDestinationFromActivity(Long activityId);

    void deleteActivity(Long activityId);

}