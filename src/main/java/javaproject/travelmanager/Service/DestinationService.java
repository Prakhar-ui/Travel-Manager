package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.TravelPackage;

import java.util.List;

/**
 * Service interface responsible for handling operations related to destinations.
 */
public interface DestinationService {

    Destination createDestination(DestinationDTO destinationDTO);

    Destination updateDestination(Long destinationId, DestinationDTO destinationDTO);

    void addActivityToDestination(Long destinationId, Long activityId);

    void setTravelPackageToDestination(Long destinationId, Long travelPackageId);

    Destination getDestination(Long destinationId);

    List<Destination> getAllDestinations();

    List<Activity> getAllActivitiesFromDestination(Long destinationId);

    TravelPackage getTravelPackageFromDestination(Long destinationId);

    void removeActivityFromDestination(Long destinationId, Long activityId);

    void removeTravelPackageFromDestination(Long destinationId);

    void deleteDestination(Long destinationId);

}