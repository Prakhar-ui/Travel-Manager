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

/**
 * Service class responsible for handling operations related to travel packages.
 */

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

    /**
     * Adds an activity to a specific passenger within a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to add.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Adds a destination to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId The ID of the destination to add.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Removes a destination from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId The ID of the destination to remove.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Removes an activity from a specific passenger within a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to remove.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Adds multiple destinations to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationIds The IDs of the destinations to add.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Adds multiple activities to a specific passenger within a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activitiesId The IDs of the activities to add.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Adds a passenger to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger to add.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Removes a passenger from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger to remove.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Adds multiple passengers to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengersId The IDs of the passengers to add.
     * @return The updated travel package if successful, otherwise null.
     */
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

    /**
     * Retrieves all travel packages.
     *
     * @return A list of all travel packages.
     */
    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAll();
    }

    /**
     * Prints the itinerary of a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     */
    public void printItinerary(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printItinerary();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    /**
     * Prints the passenger list of a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     */
    public void printPassengerList(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printPassengerList();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    /**
     * Prints the details of passengers within a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     */
    public void printPassengerDetails(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printPassengerDetails();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    /**
     * Prints the available activities within a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     */
    public void printAvailableActivities(Long travelPackageId) {
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(travelPackageId);
        if (travelPackage.isPresent()) {
            travelPackage.get().printAvailableActivities();
        } else {
            System.out.println("Travel Package not found.");
        }
    }

    /**
     * Adds a new travel package.
     *
     * @param travelPackageDTO The DTO containing travel package information.
     * @return The newly created travel package.
     */
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

    /**
     * Updates an existing travel package.
     *
     * @param travelPackageId The ID of the travel package to update.
     * @param travelPackageDTO The DTO containing updated travel package information.
     * @return The updated travel package if successful, otherwise null.
     */
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
