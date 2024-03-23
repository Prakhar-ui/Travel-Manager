package javaproject.travelmanager.Config;


import javaproject.travelmanager.DTO.*;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.*;
import javaproject.travelmanager.Service.*;
import javaproject.travelmanager.Service.Implementation.TravelPackageServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {
    private final TravelPackageService travelPackageService;
    private final DestinationService destinationService;
    private final ActivityService activityService;
    private final PassengerService passengerService;

    private final TravelPackagePrintService travelPackagePrintService;

    public DataLoader(TravelPackageService travelPackageService,
                      DestinationService destinationService,
                      ActivityService activityService,
                      PassengerService passengerService,
                      TravelPackagePrintService travelPackagePrintService) {
        this.travelPackageService = travelPackageService;
        this.destinationService = destinationService;
        this.activityService = activityService;
        this.passengerService = passengerService;
        this.travelPackagePrintService = travelPackagePrintService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Create sample destinations
        DestinationDTO destination1 = new DestinationDTO();
        DestinationDTO destination2 = new DestinationDTO();
        DestinationDTO destination3 = new DestinationDTO();
        destination1.setName("Goa");
        destination2.setName("Jaipur");
        destination3.setName("Kerala");

        Destination savedDestination1 = destinationService.createDestination(destination1);
        Destination savedDestination2 = destinationService.createDestination(destination2);
        Destination savedDestination3 = destinationService.createDestination(destination3);

        // Create sample activities
        ActivityDTO activity1 = new ActivityDTO();
        activity1.setName("Beach Party");
        activity1.setDescription("Enjoy beach party");
        activity1.setCost(2000.0);
        activity1.setCapacity(10);

        ActivityDTO activity2 = new ActivityDTO();
        activity2.setName("Scuba Diving");
        activity2.setDescription("Explore underwater world");
        activity2.setCost(5000.0);
        activity2.setCapacity(4);

        ActivityDTO activity3 = new ActivityDTO();
        activity3.setName("City Tour");
        activity3.setDescription("Explore historical monuments");
        activity3.setCost(1500.0);
        activity3.setCapacity(5);

        ActivityDTO activity4 = new ActivityDTO();
        activity4.setName("Puppet Show");
        activity4.setDescription("Enjoy puppet show");
        activity4.setCost(1000.0);
        activity4.setCapacity(7);

        ActivityDTO activity5 = new ActivityDTO();
        activity5.setName("Backwater Cruise");
        activity5.setDescription("Experience backwater cruise");
        activity5.setCost(4000.0);
        activity5.setCapacity(3);

        ActivityDTO activity6 = new ActivityDTO();
        activity6.setName("Ayurvedic Massage");
        activity6.setDescription("Relax with ayurvedic massage");
        activity6.setCost(2000.0);
        activity6.setCapacity(5);

        Activity savedActivity1 = activityService.createActivity(activity1);
        Activity savedActivity2 = activityService.createActivity(activity2);
        Activity savedActivity3 = activityService.createActivity(activity3);
        Activity savedActivity4 = activityService.createActivity(activity4);
        Activity savedActivity5 = activityService.createActivity(activity5);
        Activity savedActivity6 = activityService.createActivity(activity6);

        // Fetch destinations from the database
        destinationService.addActivityToDestination(savedDestination1.getId(),savedActivity1.getId());
        destinationService.addActivityToDestination(savedDestination1.getId(),savedActivity2.getId());
        destinationService.addActivityToDestination(savedDestination2.getId(),savedActivity3.getId());
        destinationService.addActivityToDestination(savedDestination2.getId(),savedActivity4.getId());
        destinationService.addActivityToDestination(savedDestination3.getId(),savedActivity5.getId());
        destinationService.addActivityToDestination(savedDestination3.getId(),savedActivity6.getId());

        // Create sample passengers
        PassengerDTO passenger1 = new PassengerDTO();
        passenger1.setName("Rahul");
        passenger1.setPassengerNumber("1234567890");
        passenger1.setPassengerType(PassengerType.STANDARD);
        passenger1.setBalance(50000.0);
        PassengerDTO passenger2 = new PassengerDTO();
        passenger2.setName("Sonia");
        passenger2.setPassengerNumber("9876543210");
        passenger2.setPassengerType(PassengerType.GOLD);
        passenger2.setBalance(50000.0);
        PassengerDTO passenger3 = new PassengerDTO();
        passenger3.setName("Amit");
        passenger3.setPassengerNumber("5678901234");
        passenger3.setPassengerType(PassengerType.PREMIUM);
        passenger3.setBalance(0);


        Passenger savedPassenger1 = passengerService.createPassenger(passenger1);
        Passenger savedPassenger2 = passengerService.createPassenger(passenger2);
        Passenger savedPassenger3 = passengerService.createPassenger(passenger3);


        // Create sample travel packages
        TravelPackageDTO package1 = new TravelPackageDTO();
        package1.setName("Trip 1");
        package1.setPassengerCapacity(3);

        TravelPackage savedTravelPackage = travelPackageService.createTravelPackage(package1);

        travelPackageService.addDestinationToTravelPackage(savedTravelPackage.getId(),savedDestination1.getId());
        travelPackageService.addDestinationToTravelPackage(savedTravelPackage.getId(),savedDestination2.getId());
        travelPackageService.addDestinationToTravelPackage(savedTravelPackage.getId(),savedDestination3.getId());

        travelPackageService.addPassengerToTravelPackage(savedTravelPackage.getId(),savedPassenger1.getId());
        travelPackageService.addPassengerToTravelPackage(savedTravelPackage.getId(),savedPassenger2.getId());
        travelPackageService.addPassengerToTravelPackage(savedTravelPackage.getId(),savedPassenger3.getId());

        passengerService.setTravelPackageToPassenger(savedPassenger1.getId(),savedTravelPackage.getId());
        passengerService.setTravelPackageToPassenger(savedPassenger2.getId(),savedTravelPackage.getId());
        passengerService.setTravelPackageToPassenger(savedPassenger3.getId(),savedTravelPackage.getId());

        destinationService.setTravelPackageToDestination(savedDestination1.getId(),savedTravelPackage.getId());
        destinationService.setTravelPackageToDestination(savedDestination2.getId(),savedTravelPackage.getId());
        destinationService.setTravelPackageToDestination(savedDestination3.getId(),savedTravelPackage.getId());

        passengerService.addActivityToPassenger(savedPassenger1.getId(),savedActivity1.getId());
        passengerService.addActivityToPassenger(savedPassenger1.getId(),savedActivity2.getId());
        passengerService.addActivityToPassenger(savedPassenger2.getId(),savedActivity3.getId());
        passengerService.addActivityToPassenger(savedPassenger2.getId(),savedActivity4.getId());
        passengerService.addActivityToPassenger(savedPassenger3.getId(),savedActivity5.getId());
        passengerService.addActivityToPassenger(savedPassenger3.getId(),savedActivity6.getId());

        System.out.println("----------------------------------------------------");
        System.out.println("Itinerary - ");
        travelPackagePrintService.printItinerary(savedTravelPackage.getId());
        System.out.println("----------------------------------------------------");
        System.out.println("Passenger List - ");
        travelPackagePrintService.printPassengerList(savedTravelPackage.getId());
        System.out.println("----------------------------------------------------");
        System.out.println("Passenger Details - ");
        travelPackagePrintService.printPassengerDetails(savedTravelPackage.getId());
        System.out.println("----------------------------------------------------");
        System.out.println("Available activities - ");
        travelPackagePrintService.printAvailableActivities(savedTravelPackage.getId());
        System.out.println("----------------------------------------------------");

    }
}