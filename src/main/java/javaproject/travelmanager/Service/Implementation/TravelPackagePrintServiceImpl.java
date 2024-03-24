package javaproject.travelmanager.Service.Implementation;

import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class responsible for printing details related to travel packages.
 * This service provides methods to print itinerary, passenger list, passenger details, and available activities.
 */
@Service
public class TravelPackagePrintServiceImpl implements TravelPackagePrintService {
    private final TravelPackageService travelPackageService;

    public TravelPackagePrintServiceImpl(TravelPackageService travelPackageService) {
        this.travelPackageService = travelPackageService;
    }

    /**
     * Prints the itinerary of the travel package, including destinations and activities.
     * @param travelPackageId The ID of the travel package to print the itinerary for.
     */
    @Override
    public void printItinerary(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);

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
     * Prints the list of passengers enrolled in a travel package.
     * @param travelPackageId The ID of the travel package.
     */
    @Override
    public void printPassengerList(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);


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
     * Prints details of passengers, including their activities, in a travel package.
     * @param travelPackageId The ID of the travel package.
     */
    @Override
    public void printPassengerDetails(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);

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
                } else if (passenger.getPassengerType() == PassengerType.GOLD){
                    System.out.println("    Price Paid: " + (activity.getCost()* 0.9));
                } else {
                        System.out.println("    Price Paid: " + activity.getCost());
                }
            }
        }
    }

    /**
     * Prints the available activities in a travel package, along with their destinations and available spaces.
     * @param travelPackageId The ID of the travel package.
     */
    @Override
    public void printAvailableActivities(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);

        String travelPackageName = travelPackage.getName();

        List<Destination> destinations = travelPackage.getDestinations();

        System.out.println("Travel Package: " + travelPackageName);
        int totalAvailableSpaces = 0;
        for (Destination destination : destinations) {
            for (Activity activity : destination.getActivities()) {
                int availableSpaces = activity.getCapacity();
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
