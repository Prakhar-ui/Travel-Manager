package javaproject.travelmanager.Service.Implementation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import javaproject.travelmanager.Service.DestinationService;
import javaproject.travelmanager.Service.PassengerService;
import javaproject.travelmanager.Service.TravelPackagePrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TravelPackagePrintServiceImpl implements TravelPackagePrintService {
    private final TravelPackageRepository travelPackageRepository;

    private final PassengerRepository passengerRepository;

    private final DestinationRepository destinationRepository;


    /**
     * Constructs a new TravelPackageServiceImpl with the provided repositories.
     *
     * @param travelPackageRepository The repository for managing travel package entities.
     * @param passengerRepository     The repository for managing passenger entities.
     * @param destinationRepository  The repository for managing destination entities.
     */
    @Autowired
    public TravelPackagePrintServiceImpl(TravelPackageRepository travelPackageRepository,
                                    PassengerRepository passengerRepository,
                                    DestinationRepository destinationRepository,
                                    ActivityRepository activityRepository,
                                    PassengerService passengerService,
                                    DestinationService destinationService
    ) {
        this.travelPackageRepository = travelPackageRepository;
        this.passengerRepository = passengerRepository;
        this.destinationRepository = destinationRepository;
    }

    /**
     * Prints the itinerary of the travel package, including destinations and activities.
     */
    @Override
    public void printItinerary(@Valid @NotNull Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));

        String travelPackageName = travelPackage.getName();

        List<Destination> destinations = travelPackage.getDestinations();


        System.out.println("Travel Package: " + travelPackageName);
        System.out.println("Destinations:");
        for (Destination destination : destinations) {
            System.out.println("- " + destination.getName());
            System.out.println("  Activities:");
            for (Activity activity : destination.getActivities()) {
                System.out.println("  - Name: " + activity.getName());
                System.out.println("    Description: " + activity.getDescription());
                System.out.println("    Cost: " + activity.getCost());
                System.out.println("    Capacity: " + activity.getCapacity());
            }
        }
    }

    /**
     * Prints the list of passengers enrolled in the travel package.
     */
    @Override
    public void printPassengerList(@Valid @NotNull Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));

        String travelPackageName = travelPackage.getName();

        int passengerCapacity = travelPackage.getPassengerCapacity();

        List<Passenger> passengers = travelPackage.getPassengers();

        System.out.println("Travel Package: " + travelPackageName);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        System.out.println("Passenger List:");
        for (Passenger passenger : passengers) {
            System.out.println("- Name: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    /**
     * Prints details about each passenger in the travel package, including their activities.
     */
    @Override
    public void printPassengerDetails(@Valid @NotNull Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));

        String travelPackageName = travelPackage.getName();

        List<Passenger> passengers = travelPackage.getPassengers();

        System.out.println("Travel Package: " + travelPackageName);
        for (Passenger passenger : passengers) {
            System.out.println("Passenger Name: " + passenger.getName());
            System.out.println("Passenger Number: " + passenger.getPassengerNumber());
            System.out.println("Passenger Type: " + passenger.getPassengerType());
            if (passenger.getPassengerType() != PassengerType.PREMIUM){
                System.out.println("Balance: " + passenger.getBalance());
            }
            System.out.println("Activities:");
            for (Activity activity : passenger.getActivities()) {
                System.out.println("  - Activity Name: " + activity.getName());
                System.out.println("    Destination: " + activity.getDestination().getName());
                if (passenger.getPassengerType() == PassengerType.PREMIUM){
                    System.out.println("    Price Paid: " + "Free for Premium Passengers");
                } else {
                    System.out.println("    Price Paid: " + activity.getCost());
                }
            }
        }
    }

    /**
     * Prints the available activities in the travel package, including the number of available spaces.
     */
    @Override
    public void printAvailableActivities(@Valid @NotNull Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));

        String travelPackageName = travelPackage.getName();

        List<Destination> destinations = travelPackage.getDestinations();

        System.out.println("Travel Package: " + travelPackageName);
        int totalAvailableSpaces = 0;
        for (Destination destination : destinations) {
            for (Activity activity : destination.getActivities()) {
                int availableSpaces = activity.getRemaining();
                if (availableSpaces > 0) {
                    totalAvailableSpaces += availableSpaces;
                    System.out.println("- Activity Name: " + activity.getName());
                    System.out.println("  Destination: " + destination.getName());
                    System.out.println("  Available Spaces: " + availableSpaces);
                }
            }
        }
        System.out.println("Total Available Spaces in the Travel Package: " + totalAvailableSpaces);
    }

}
