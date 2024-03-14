package javaproject.travelmanager.Config;


import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class DataLoader implements CommandLineRunner {
    private final TravelPackageRepository travelPackageRepository;
    private final DestinationRepository destinationRepository;
    private final ActivityRepository activityRepository;
    private final PassengerRepository passengerRepository;

    public DataLoader(TravelPackageRepository travelPackageRepository,
                      DestinationRepository destinationRepository,
                      ActivityRepository activityRepository,
                      PassengerRepository passengerRepository) {
        this.travelPackageRepository = travelPackageRepository;
        this.destinationRepository = destinationRepository;
        this.activityRepository = activityRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Create sample destinations
        Destination destination1 = new Destination("Goa");
        Destination destination2 = new Destination("Jaipur");
        Destination destination3 = new Destination("Kerala");

        destinationRepository.saveAll(Arrays.asList(destination1, destination2, destination3));

        // Create sample activities
        Activity activity1 = new Activity("Beach Party", "Enjoy beach party", 2000.0, 10);
        Activity activity2 = new Activity("Scuba Diving", "Explore underwater world", 5000.0, 4);
        Activity activity3 = new Activity("City Tour", "Explore historical monuments", 1500.0, 5);
        Activity activity4 = new Activity("Puppet Show", "Enjoy puppet show", 1000.0, 7);
        Activity activity5 = new Activity("Backwater Cruise", "Experience backwater cruise", 4000.0, 3);
        Activity activity6 = new Activity("Ayurvedic Massage", "Relax with ayurvedic massage", 2000.0, 5);

        activityRepository.saveAll(Arrays.asList(activity1, activity2, activity3, activity4, activity5, activity6));

        // Fetch destinations from the database
        destination1.addActivity(activity1);
        destination1.addActivity(activity2);
        destination2.addActivity(activity3);
        destination2.addActivity(activity4);
        destination3.addActivity(activity5);
        destination3.addActivity(activity6);

        destinationRepository.saveAll(Arrays.asList(destination1, destination2, destination3));

        // Create sample passengers
        Passenger passenger1 = new Passenger("Rahul", "1234567890", PassengerType.STANDARD, 50000.0);
        Passenger passenger2 = new Passenger("Sonia", "9876543210",  PassengerType.GOLD, 10000.0);
        Passenger passenger3 = new Passenger("Amit", "5678901234",  PassengerType.PREMIUM, 20000.0);


        passengerRepository.saveAll(Arrays.asList(passenger1, passenger2, passenger3));

        // Create sample travel packages
        TravelPackage package1 = new TravelPackage("Trip 1", 3);

        travelPackageRepository.save(package1);

        package1.addPassenger(passenger1);
        package1.addPassenger(passenger2);
        package1.addPassenger(passenger3);

        package1.addDestination(destination1);
        package1.addDestination(destination2);
        package1.addDestination(destination3);

        passenger1.addTravelPackage(package1);
        passenger2.addTravelPackage(package1);
        passenger3.addTravelPackage(package1);

        travelPackageRepository.save(package1);

        passengerRepository.saveAll(Arrays.asList(passenger1, passenger2, passenger3));

        List<Passenger> passengers = package1.getPassengers();

        for (Passenger passenger: passengers){
            if (passenger.getName() == passenger1.getName()){
                passenger.addActivity(activity1);
                passenger.addActivity(activity2);
            } else if (passenger.getName() == passenger2.getName()){
                passenger.addActivity(activity3);
                passenger.addActivity(activity4);
            } else {
                passenger.addActivity(activity5);
                passenger.addActivity(activity6);
            }
        }
        package1.setPassengers(passengers);

        passengerRepository.saveAll(Arrays.asList(passenger1, passenger2, passenger3));

    }
}