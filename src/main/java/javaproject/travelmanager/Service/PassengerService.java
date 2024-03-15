package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to passengers.
 */
@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Adds a new passenger.
     *
     * @param passengerDTO The DTO containing passenger information.
     * @return The newly created passenger.
     */
    public Passenger addPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setName(passengerDTO.getName());
        passenger.setPassengerNumber(passengerDTO.getPassengerNumber());
        passenger.setPassengerType(passengerDTO.getPassengerType());
        passenger.setBalance(passengerDTO.getBalance());
        return passengerRepository.save(passenger);
    }

    /**
     * Retrieves a passenger by its ID.
     *
     * @param id The ID of the passenger to retrieve.
     * @return The passenger, if found; otherwise, null.
     */
    public Passenger getPassengerById(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        return optionalPassenger.orElse(null);
    }

    /**
     * Retrieves all passengers.
     *
     * @return A list of all passengers.
     */
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    /**
     * Updates an existing passenger.
     *
     * @param id              The ID of the passenger to update.
     * @param passengerDetails The DTO containing updated passenger information.
     * @return The updated passenger, if found; otherwise, null.
     */
    public Passenger updatePassenger(Long id, PassengerDTO passengerDetails) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            Passenger existingPassenger = optionalPassenger.get();
            existingPassenger.setName(passengerDetails.getName());
            existingPassenger.setPassengerNumber(passengerDetails.getPassengerNumber());
            existingPassenger.setPassengerType(passengerDetails.getPassengerType());
            existingPassenger.setBalance(passengerDetails.getBalance());

            if (passengerDetails.getTravelPackagesId() != null && !passengerDetails.getTravelPackagesId().isEmpty()) {
                List<TravelPackage> travelPackages = travelPackageRepository.findAllById(passengerDetails.getTravelPackagesId());
                existingPassenger.setTravelPackages(travelPackages);
            }

            if (passengerDetails.getActivitiesId() != null && !passengerDetails.getActivitiesId().isEmpty()) {
                List<Activity> activities = activityRepository.findAllById(passengerDetails.getActivitiesId());
                existingPassenger.setActivities(activities);
            }

            return passengerRepository.save(existingPassenger);
        } else {
            return null;
        }
    }

    /**
     * Deletes a passenger by its ID.
     *
     * @param id The ID of the passenger to delete.
     * @return True if the passenger was deleted successfully; otherwise, false.
     */
    public boolean deletePassenger(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            passengerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}