package javaproject.travelmanager.Service;


import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
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

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public TravelPackage addActivityToPassenger(Long travelPackageId, Long passengerId, Long activityId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);

        if (optionalTravelPackage.isPresent() && optionalActivity.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            Activity activity = optionalActivity.get();

            Optional<Passenger> optionalPassenger = travelPackage.getPassengers().stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();

            if (optionalPassenger.isPresent()) {
                Passenger passenger = optionalPassenger.get();
                // Add the retrieved activity to the passenger
                passenger.addActivity(activity);
                travelPackageRepository.save(travelPackage);
                return travelPackage;
            }
        }
        return null;
    }



    public TravelPackage addDestinationToTravelPackage(Long travelPackageId, Long destinationId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        Optional<Destination> optionalDestination = destinationRepository.findById(destinationId);

        if (optionalTravelPackage.isPresent() && optionalDestination.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            Destination destination = optionalDestination.get();

            // Add the retrieved destination to the travel package
            travelPackage.addDestination(destination);
            travelPackageRepository.save(travelPackage);
            return travelPackage;
        }

        return null;
    }


    public TravelPackage removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.removeDestinationById(destinationId);
            travelPackageRepository.save(travelPackage);
            return travelPackage;
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
                travelPackageRepository.save(travelPackage);
                return travelPackage;
            }
        }
        return null;
    }

    public TravelPackage addDestinationsToTravelPackage(Long travelPackageId, List<Long> destinationIds) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            // Retrieve Destination entities by their IDs
            List<Destination> destinations = destinationRepository.findAllById(destinationIds);
            // Add retrieved destinations to the travel package
            travelPackage.addDestinations(destinations);
            travelPackageRepository.save(travelPackage);
            return travelPackage;
        }
        return null;
    }


    public TravelPackage addActivitiesToPassenger(Long travelPackageId, Long passengerId, List<Long> activitiesId) {

        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);

        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();

            Optional<Passenger> optionalPassenger = travelPackage.getPassengers().stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();

            if (optionalPassenger.isPresent()) {
                // Step 5: Passenger is present, retrieve it
                Passenger passenger = optionalPassenger.get();

                List<Activity> activities = activityRepository.findAllById(activitiesId);

                passenger.setActivities(activities);

                travelPackageRepository.save(travelPackage);

                return travelPackage;
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
            if (optionalPassenger.isEmpty()) {
                Optional<Passenger> passenger = passengerRepository.findById(passengerId);
                passenger.ifPresent(travelPackage::addPassenger);
                travelPackageRepository.save(travelPackage);
                return travelPackage;
            }
        }
        return null;
    }

    public TravelPackage removePassengerFromTravelPackage(Long travelPackageId, Long passengerId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.removePassengerById(passengerId);
            travelPackageRepository.save(travelPackage);
            return travelPackage;
        }
        return null;
    }

    public TravelPackage addPassengersToTravelPackage(Long travelPackageId, List<Long> passengersId) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            // Retrieve Passenger entities by their IDs
            List<Passenger> passengers = passengerRepository.findAllById(passengersId);
            // Add retrieved passengers to the travel package
            travelPackage.setPassengers(passengers);
            travelPackageRepository.save(travelPackage);
            return travelPackage;
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

    public TravelPackage addTravelPackage(TravelPackageDTO travelPackageDTO) {
        TravelPackage travelPackage = new TravelPackage(travelPackageDTO.getName(), travelPackageDTO.getPassengerCapacity());

        // Add destinations to the travel package if destinationsId is not null and not empty
        if (travelPackageDTO.getDestinationsId() != null && !travelPackageDTO.getDestinationsId().isEmpty()) {
            List<Destination> destinations = destinationRepository.findAllById(travelPackageDTO.getDestinationsId());
            travelPackage.addDestinations(destinations);
        }

        // Add passengers to the travel package if passengersId is not null and not empty
        if (travelPackageDTO.getPassengersId() != null && !travelPackageDTO.getPassengersId().isEmpty()) {
            List<Passenger> passengers = passengerRepository.findAllById(travelPackageDTO.getPassengersId());
            travelPackage.setPassengers(passengers);
        }

        travelPackageRepository.save(travelPackage);
        return travelPackage;
    }



    public TravelPackage updateTravelPackage(Long travelPackageId, TravelPackageDTO travelPackageDTO) {
        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findById(travelPackageId);
        if (optionalTravelPackage.isPresent()) {
            TravelPackage travelPackage = optionalTravelPackage.get();
            travelPackage.setName(travelPackageDTO.getName());
            travelPackage.setPassengerCapacity(travelPackageDTO.getPassengerCapacity());

            // Update destinations if destinationsId is not empty
            if (travelPackageDTO.getDestinationsId() != null && !travelPackageDTO.getDestinationsId().isEmpty()) {
                List<Destination> destinations = destinationRepository.findAllById(travelPackageDTO.getDestinationsId());
                travelPackage.setDestinations(destinations);
            }

            // Update passengers if passengersId is not empty
            if (travelPackageDTO.getPassengersId() != null && !travelPackageDTO.getPassengersId().isEmpty()) {
                List<Passenger> passengers = passengerRepository.findAllById(travelPackageDTO.getPassengersId());
                travelPackage.setPassengers(passengers);
            }

            travelPackageRepository.save(travelPackage);
            return travelPackage;
        } else {
            return null;
        }
    }

}
