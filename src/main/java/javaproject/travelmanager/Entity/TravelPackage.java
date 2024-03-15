package javaproject.travelmanager.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

/**
 * Entity class representing a travel package.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelPackage {
    /**
     * Represents the unique identifier of the travel package.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Represents the name of the travel package.
     */
    private String name;

    /**
     * Represents the passenger capacity of the travel package.
     */
    private int passengerCapacity;

    /**
     * Represents the list of destinations included in the travel package.
     * Each travel package can have multiple destinations.
     */
    @ManyToMany
    @JoinTable(
            name = "travel_package_destinations",
            joinColumns = @JoinColumn(name = "travel_package_id"),
            inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private List<Destination> destinations = new ArrayList<>();

    /**
     * Represents the list of passengers enrolled in the travel package.
     * Each travel package can have multiple passengers.
     */
    @ManyToMany(mappedBy = "travelPackages")
    private List<Passenger> passengers = new ArrayList<>();

    /**
     * Constructs a TravelPackage object with a name and passenger capacity.
     *
     * @param name             The name of the travel package.
     * @param passengerCapacity The passenger capacity of the travel package.
     */
    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.destinations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    /**
     * Adds a destination to the travel package.
     *
     * @param destination The destination to add.
     */
    public void addDestination(Destination destination) {
        this.destinations.add(destination);
    }

    /**
     * Removes a destination from the travel package.
     *
     * @param destination The destination to remove.
     */
    public void removeDestination(Destination destination) {
        this.destinations.remove(destination);
    }

    /**
     * Adds a passenger to the travel package.
     *
     * @param passenger The passenger to add.
     */
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Removes a passenger from the travel package.
     *
     * @param passenger The passenger to remove.
     */
    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }


    /**
     * Removes a passenger from the travel package by ID.
     *
     * @param passengerId The ID of the passenger to remove.
     */
    public void removePassengerById(Long passengerId) {
        this.passengers.removeIf(passenger -> passenger.getId().equals(passengerId));
    }

    /**
     * Adds activities to a specific passenger in the travel package.
     *
     * @param passengerId The ID of the passenger to whom activities are added.
     * @param activities  The list of activities to add to the passenger.
     */
    public void addActivitiesToPassenger(Long passengerId, List<Activity> activities) {
            Optional<Passenger> optionalPassenger = passengers.stream()
                    .filter(passenger -> passenger.getId().equals(passengerId))
                    .findFirst();
            optionalPassenger.ifPresent(passenger -> passenger.setActivities(activities));

    }

    /**
     * Removes a specific activity from a passenger in the travel package.
     *
     * @param passengerId The ID of the passenger from whom the activity is removed.
     * @param activityId  The ID of the activity to remove from the passenger.
     */
    public void removeActivityFromPassenger(Long passengerId, Long activityId) {
        Optional<Passenger> optionalPassenger = passengers.stream()
                .filter(passenger -> passenger.getId().equals(passengerId))
                .findFirst();
        optionalPassenger.ifPresent(passenger -> passenger.removeActivityById(activityId));
    }

    /**
     * Adds destinations to the travel package.
     *
     * @param destinations The list of destinations to add to the travel package.
     */
    public void addDestinations(List<Destination> destinations) {
            this.destinations.addAll(destinations);


    }

    /**
     * Removes a destination from the travel package by its ID.
     *
     * @param destinationId The ID of the destination to remove from the travel package.
     */
    public void removeDestinationById(Long destinationId) {
        this.destinations.removeIf(destination -> destination.getId().equals(destinationId));
    }

    /**
     * Prints the itinerary of the travel package, including destinations and activities.
     */
    public void printItinerary() {
        System.out.println("Travel Package: " + name);
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
    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
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
    public void printPassengerDetails() {
        System.out.println("Travel Package: " + name);
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
    public void printAvailableActivities() {
        System.out.println("Travel Package: " + name);
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