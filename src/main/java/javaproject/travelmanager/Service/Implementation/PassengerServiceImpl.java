package javaproject.travelmanager.Service.Implementation;

import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import javaproject.travelmanager.Service.ActivityService;
import javaproject.travelmanager.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private final ActivityService activityService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                TravelPackageRepository travelPackageRepository,
                                ActivityService activityService) {
        this.passengerRepository = passengerRepository;
        this.travelPackageRepository = travelPackageRepository;
        this.activityService = activityService;
    }

    /**
     * Creates a new passenger based on the provided DTO.
     * @param passengerDTO The DTO containing information to create the passenger.
     * @return The newly created passenger.
     */
    @Override
    public Passenger createPassenger(PassengerDTO passengerDTO) {
        String name = passengerDTO.getName();
        String passengerNumber = passengerDTO.getPassengerNumber();
        PassengerType passengerType = passengerDTO.getPassengerType();
        double balance = passengerDTO.getBalance();
        Long travelPackageId = passengerDTO.getTravelPackageId();
        List<Long> activitiesIds = passengerDTO.getActivitiesIds();

        Passenger passenger = new Passenger();
        passenger.setName(name);
        passenger.setPassengerNumber(passengerNumber);
        passenger.setPassengerType(passengerType);
        passenger.setBalance(balance);

        if (travelPackageId != null) {
            TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
            passenger.setTravelPackage(travelPackage);
        }
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for(Long activityId: activitiesIds){
                Activity activity = activityService.getActivity(activityId);
                passenger.addActivity(activity);
                switch (passengerType) {
                    case STANDARD -> {
                        passenger.setBalance(balance-activity.getCost());
                    }
                    case GOLD -> {
                        passenger.setBalance(balance-(activity.getCost()*0.9));
                    }
                }
            }
        }

        return passengerRepository.save(passenger);
    }

    /**
     * Updates an existing passenger with the provided DTO.
     * @param passengerId The ID of the passenger to update.
     * @param passengerDTO The DTO containing updated information for the passenger.
     * @return The updated passenger.
     */
    @Override
    public Passenger updatePassenger(Long passengerId, PassengerDTO passengerDTO) {
        String name = passengerDTO.getName();
        String passengerNumber = passengerDTO.getPassengerNumber();
        PassengerType passengerType = passengerDTO.getPassengerType();
        double balance = passengerDTO.getBalance();
        Long travelPackageId = passengerDTO.getTravelPackageId();
        List<Long> activitiesIds = passengerDTO.getActivitiesIds();

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
        passenger.setName(name);
        passenger.setPassengerNumber(passengerNumber);
        passenger.setPassengerType(passengerType);
        passenger.setBalance(balance);

        if (travelPackageId != null) {
            setTravelPackageToPassenger(passengerId,travelPackageId);
        }
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for(Long activityId: activitiesIds){
                addActivityToPassenger(passengerId,activityId);
            }
        }

        return passengerRepository.save(passenger);
    }

    /**
     * Adds an activity to the specified passenger.
     * @param passengerId The ID of the passenger to which the activity will be added.
     * @param activityId The ID of the activity to add.
     */
    @Override
    public void addActivityToPassenger(Long passengerId, Long activityId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));

        PassengerType passengerType = passenger.getPassengerType();
        double balance = passenger.getBalance();
        Activity activity = activityService.getActivity(activityId);
        double cost = activity.getCost();
        int capacity = activity.getCapacity();

        if (capacity <= 0) {
            throw new IllegalStateException("Activity is at full capacity");
        }

        switch (passengerType) {
            case STANDARD:
                if (balance < cost) {
                    throw new IllegalStateException("Insufficient balance!");
                }
                break;
            case GOLD:
                if (balance < (cost * 0.9)) {
                    throw new IllegalStateException("Insufficient balance!");
                }
                break;
            default:
                break;
        }

        if (passengerType != PassengerType.PREMIUM) {
            passenger.setBalance(balance - (passengerType == PassengerType.GOLD ? cost * 0.9 : cost));
        }
        passenger.addActivity(activity);
        activity.setCapacity(capacity - 1);
    }

    /**
     * Sets the travel package for the specified passenger.
     * @param passengerId The ID of the passenger to which the travel package will be set.
     * @param travelPackageId The ID of the travel package to set.
     */
    @Override
    public void setTravelPackageToPassenger(Long passengerId, Long travelPackageId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));

        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        if (passenger.getTravelPackage() != null) {
            throw new IllegalStateException("Passenger is already assigned to another travel package");
        }

        passenger.setTravelPackage(travelPackage);
    }

    /**
     * Retrieves the passenger with the specified ID.
     * @param passengerId The ID of the passenger to retrieve.
     * @return The passenger with the specified ID, if found.
     */
    @Override
    public Passenger getPassenger(Long passengerId) {
        return passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
    }

    /**
     * Retrieves all passengers.
     * @return A list of all passengers.
     */
    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    /**
     * Retrieves all activities associated with the specified passenger.
     * @param passengerId The ID of the passenger.
     * @return A list of all activities associated with the specified passenger.
     */
    @Override
    public List<Activity> getAllActivitiesFromPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
        return passenger.getActivities();
    }

    /**
     * Retrieves the travel package associated with the specified passenger.
     * @param passengerId The ID of the passenger.
     * @return The travel package associated with the specified passenger, if any.
     */
    @Override
    public TravelPackage getTravelPackageFromPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
        return passenger.getTravelPackage();
    }

    /**
     * Removes an activity from the specified passenger.
     * @param passengerId The ID of the passenger from which the activity will be removed.
     * @param activityId The ID of the activity to remove.
     */
    @Override
    public void removeActivityFromPassenger(Long passengerId, Long activityId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));

        PassengerType passengerType = passenger.getPassengerType();
        Activity activity = activityService.getActivity(activityId);
        double cost = activity.getCost();
        int capacity = activity.getCapacity();

        if (!passenger.getActivities().contains(activity)) {
            throw new IllegalStateException("Passenger is not registered for this activity");
        }

        switch (passengerType) {
            case STANDARD:
                passenger.setBalance(passenger.getBalance() + cost);
                break;
            case GOLD:
                passenger.setBalance(passenger.getBalance() + (cost * 0.9));
                break;
            default:
                break;
        }
        passenger.removeActivity(activity);
        activity.setCapacity(capacity + 1);
    }

    /**
     * Removes the travel package from the specified passenger.
     * @param passengerId The ID of the passenger from which the travel package will be removed.
     */
    @Override
    public void removeTravelPackageFromPassenger(Long passengerId) {
        // Retrieve the passenger
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));

        if (passenger.getTravelPackage() == null) {
            throw new IllegalStateException("Passenger does not have an associated travel package");
        }

        passenger.setTravelPackage(null);
    }

    /**
     * Deletes the passenger with the specified ID.
     * @param passengerId The ID of the passenger to delete.
     */
    @Override
    public void deletePassenger(Long passengerId) {
        passengerRepository.deleteById(passengerId);
    }
}