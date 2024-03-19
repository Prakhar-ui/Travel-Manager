package javaproject.travelmanager.Service.Implementation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import javaproject.travelmanager.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling operations related to passengers.
 * This service manages CRUD operations for passengers, including creating new passengers,
 * retrieving passengers by ID or all passengers, updating existing passengers,
 * and deleting passengers.
 */
@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {


    private final PassengerRepository passengerRepository;

    private final TravelPackageRepository travelPackageRepository;

    private final ActivityRepository activityRepository;

    /**
     * Constructs a new PassengerServiceImpl with the provided repositories.
     *
     * @param travelPackageRepository The repository for managing travel package entities.
     * @param passengerRepository The repository for managing passenger entities.
     * @param activityRepository    The repository for managing activity entities.
     */
    @Autowired
    public PassengerServiceImpl(TravelPackageRepository travelPackageRepository, PassengerRepository passengerRepository, ActivityRepository activityRepository) {
        this.travelPackageRepository = travelPackageRepository;
        this.passengerRepository = passengerRepository;
        this.activityRepository = activityRepository;
    }

    /**
     * Adds a new passenger.
     *
     * @param passengerDTO The DTO containing passenger information.
     * @return The newly created passenger.
     */
    @Override
    public Passenger createPassenger(@Valid @NotNull PassengerDTO passengerDTO) {
            Passenger passenger = switch (passengerDTO.getPassengerType()) {
                case STANDARD -> new StandardPassenger(passengerDTO.getName(), passengerDTO.getPassengerNumber(),
                        passengerDTO.getPassengerType(), passengerDTO.getBalance());
                case GOLD -> new GoldPassenger(passengerDTO.getName(), passengerDTO.getPassengerNumber(),
                        passengerDTO.getPassengerType(), passengerDTO.getBalance());
                case PREMIUM -> new PremiumPassenger(passengerDTO.getName(), passengerDTO.getPassengerNumber(),
                        passengerDTO.getPassengerType());
            };
        List<Long> activitiesIds = passengerDTO.getActivitiesIds();
        List<Long> travelPackagesIds = passengerDTO.getTravelPackagesIds();

        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for(Long activityId: activitiesIds){
                Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new IllegalArgumentException("Activity with ID " + activityId + " not found."));
                passenger.signUpForActivity(activity);
            }
        }

        if (travelPackagesIds != null && !travelPackagesIds.isEmpty()) {
            for(Long travelPackageId: travelPackagesIds){
                TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
                passenger.addTravelPackage(travelPackage);
            }
        }
        return passengerRepository.save(passenger);
    }


    /**
     * Retrieves a passenger by its ID.
     *
     * @param passengerId The ID of the passenger to retrieve.
     * @return The passenger, if found; otherwise, null.
     */
    @Override
    public Optional<Passenger> getPassenger(@Valid @NotNull Long passengerId) {
        return Optional.ofNullable(passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found.")));
    }

    /**
     * Retrieves all passengers.
     *
     * @return A list of all passengers.
     */
    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public List<Activity> getAllActivitiesFromPassenger(@Valid @NotNull Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));
        return passenger.getActivities();
    }

    /**
     * Updates an existing passenger.
     *
     * @param passengerId               The ID of the passenger to update.
     * @param passengerDTO The DTO containing updated passenger information.
     * @return The updated passenger, if found; otherwise, null.
     */
    @Override
    public Optional<Passenger> updatePassenger(@Valid @NotNull Long passengerId, @Valid @NotNull PassengerDTO passengerDTO) {
        Passenger existingPassenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));
            existingPassenger.setName(passengerDTO.getName());
            existingPassenger.setPassengerNumber(passengerDTO.getPassengerNumber());
            existingPassenger.setPassengerType(passengerDTO.getPassengerType());
            existingPassenger.setBalance(passengerDTO.getBalance());

            List<Long> activitiesIds = passengerDTO.getActivitiesIds();
            List<Long> travelPackagesIds = passengerDTO.getTravelPackagesIds();

            if (activitiesIds != null && !activitiesIds.isEmpty()) {
                for (Long activityId: activitiesIds){
                    addActivityToPassenger(existingPassenger.getId(),  activityId);
                }
            }

            if (travelPackagesIds != null && !travelPackagesIds.isEmpty()) {
                for (Long travelPackageId: travelPackagesIds){
                    addTravelPackageToPassenger(existingPassenger.getId(),  travelPackageId);
                }
            }

            return Optional.of(passengerRepository.save(existingPassenger));
    }

    /**
     * Adds activities to the given passenger based on their IDs.
     *
     * @param passengerId The passenger to which activities will be added.
     * @param activityId The IDs of the activities to be added to the passenger.
     * @throws IllegalArgumentException if any of the activities with the given IDs are not found.
     */

    @Override
    public void addActivityToPassenger(@Valid @NotNull Long passengerId, @Valid @NotNull Long activityId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity with ID " + activityId + " not found."));

        passenger.signUpForActivity(activity);
    }

    @Override
    public void removeActivityFromPassenger( @Valid @NotNull Long passengerId, @Valid @NotNull Long activityId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity with ID " + activityId + " not found."));

        passenger.removeActivity(activityId);
    }



    /**
     * Adds activities to the given passenger based on their IDs.
     *
     * @param passengerId The passenger to which activities will be added.
     * @param travelPackageId The IDs of the activities to be added to the passenger.
     * @throws IllegalArgumentException if any of the activities with the given IDs are not found.
     */
    @Override
    public void addTravelPackageToPassenger(@Valid @NotNull Long passengerId, @Valid @NotNull Long travelPackageId) {

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));

        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));

        passenger.addTravelPackage(travelPackage);
    }


    @Override
    public void removeTravelPackageFromPassenger(@Valid @NotNull Long passengerId, @Valid @NotNull Long travelPackageId){

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));

        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));

        passenger.removeTravelPackage(travelPackageId);
    }


    /**
     * Deletes a passenger by its ID.
     *
     * @param passengerId The ID of the passenger to delete.
     */
    @Override
    public void deletePassenger(@Valid @NotNull Long passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new IllegalArgumentException("Passenger with ID " + passengerId + " not found.");
        }
        passengerRepository.deleteById(passengerId);
    }
}