package javaproject.travelmanager.Service;


import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelPackageService {

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public TravelPackage addActivityToPassenger(Long travelPackageId, Long passengerId, Activity activity) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            Optional<Passenger> optionalPassenger = travelPackage.getPassengers().stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();
            if (optionalPassenger.isPresent()) {
                Passenger passenger = optionalPassenger.get();
                passenger.addActivity(activity);
                return travelPackageRepository.save(travelPackage);
            }
        }
        return null;
    }

    public TravelPackage addDestinationToTravelPackage(Long travelPackageId, Destination destination) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.addDestination(destination);
            return travelPackageRepository.save(travelPackage);
        }
        return null;
    }

    public TravelPackage removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.removeDestinationById(destinationId);
            return travelPackageRepository.save(travelPackage);
        }
        return null;
    }

    public TravelPackage removeActivityFromPassenger(Long travelPackageId, Long passengerId, Long activityId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            Optional<Passenger> optionalPassenger = travelPackage.getPassengers().stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();
            if (optionalPassenger.isPresent()) {
                Passenger passenger = optionalPassenger.get();
                passenger.removeActivityById(activityId);
                return travelPackageRepository.save(travelPackage);
            }
        }
        return null;
    }

    public TravelPackage addDestinationsToTravelPackage(Long travelPackageId, List<Destination> destinations) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.addDestinations(destinations);
            return travelPackageRepository.save(travelPackage);
        }
        return null;
    }

    public TravelPackage addActivitiesToPassenger(Long travelPackageId, Long passengerId, List<Activity> activities) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            Optional<Passenger> optionalPassenger = travelPackage.getPassengers().stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();
            if (optionalPassenger.isPresent()) {
                Passenger passenger = optionalPassenger.get();
                passenger.addActivities(activities);
                return travelPackageRepository.save(travelPackage);
            }
        }
        return null;
    }

    public TravelPackage addPassengerToTravelPackage(Long travelPackageId, Long passengerId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            Optional<Passenger> optionalPassenger = travelPackage.getPassengers().stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();
            if (!optionalPassenger.isPresent()) {
                Optional<Passenger> passenger = passengerRepository.findById(passengerId);
                passenger.ifPresent(travelPackage::addPassenger);
                return travelPackageRepository.save(travelPackage);
            }
        }
        return null;
    }

    public TravelPackage removePassengerFromTravelPackage(Long travelPackageId, Long passengerId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.removePassengerById(passengerId);
            return travelPackageRepository.save(travelPackage);
        }
        return null;
    }

    public TravelPackage addPassengersToTravelPackage(Long travelPackageId, List<Passenger> passengers) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.addPassengers(passengers);
            return travelPackageRepository.save(travelPackage);
        }
        return null;
    }

    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAll();
    }

    public void printItinerary(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printItinerary();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    public void printPassengerList(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printPassengerList();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    public void printPassengerDetails(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printPassengerDetails();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    public void printAvailableActivities(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printAvailableActivities();
        } else {
            System.out.println("Travel Package not found.");
        }
    }
}
