package javaproject.travelmanager.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Exception.ActivityNotFoundException;
import javaproject.travelmanager.Exception.InsufficientActivityCapacityException;
import javaproject.travelmanager.Exception.InsufficientBalanceException;

import java.util.List;

/**
 * Service interface responsible for handling operations related to passengers.
 */
public interface PassengerService {


    Passenger createPassenger(PassengerDTO passengerDTO);

    Passenger updatePassenger(Long passengerId, PassengerDTO passengerDTO);

    void addActivityToPassenger(Long passengerId, Long activityId);

    void setTravelPackageToPassenger(Long passengerId, Long travelPackageId);

    Passenger getPassenger(Long passengerId);

    List<Passenger> getAllPassengers();

    List<Activity> getAllActivitiesFromPassenger(Long passengerId);

    TravelPackage getTravelPackageFromPassenger(Long passengerId);

    void removeActivityFromPassenger(Long passengerId, Long activityId) ;

    void removeTravelPackageFromPassenger(Long passengerId);

    void deletePassenger(Long passengerId);
}