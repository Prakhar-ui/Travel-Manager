package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;

import java.util.List;
import java.util.Optional;

/**
 * Service interface responsible for handling operations related to travel packages.
 */
public interface TravelPackageService {

    TravelPackage createTravelPackage(TravelPackageDTO travelPackageDTO);

    TravelPackage updateTravelPackage(Long travelPackageId, TravelPackageDTO travelPackageDTO);

    void addDestinationToTravelPackage(Long travelPackageId, Long destinationId);

    void addPassengerToTravelPackage(Long travelPackageId, Long passengerId);

    TravelPackage getTravelPackage(Long travelPackageId);

    List<TravelPackage> getAllTravelPackages();

    List<Passenger> getAllPassengers(Long travelPackageId);

    List<Destination> getAllDestinations(Long travelPackageId);

    void removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId);

    void removePassengerFromTravelPackage(Long travelPackageId, Long passengerId);

    void deleteTravelPackage(Long travelPackageId);

}