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

    @Override
    public Passenger getPassenger(Long passengerId) {
        return passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public List<Activity> getAllActivitiesFromPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
        return passenger.getActivities();
    }

    @Override
    public TravelPackage getTravelPackageFromPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));
        return passenger.getTravelPackage();
    }

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

    @Override
    public void deletePassenger(Long passengerId) {
        passengerRepository.deleteById(passengerId);
    }
}