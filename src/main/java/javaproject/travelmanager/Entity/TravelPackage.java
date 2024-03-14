package javaproject.travelmanager.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int passengerCapacity;

    @ManyToMany
    @JoinTable(
            name = "travel_package_destinations",
            joinColumns = @JoinColumn(name = "travel_package_id"),
            inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private List<Destination> destinations;

    @ManyToMany(mappedBy = "travelPackages")
    private List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.destinations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        this.destinations.add(destination);
    }

    public void removeDestination(Destination destination) {
        this.destinations.remove(destination);
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }

    public void addPassengers(List<Passenger> passengers) {
        this.passengers.addAll(passengers);
    }

    public void removePassengerById(Long passengerId) {
        this.passengers.removeIf(passenger -> passenger.getId().equals(passengerId));
    }

    public void addActivitiesToPassenger(Long passengerId, List<Activity> activities) {
        Optional<Passenger> optionalPassenger = passengers.stream()
                .filter(passenger -> passenger.getId().equals(passengerId))
                .findFirst();
        optionalPassenger.ifPresent(passenger -> passenger.addActivities(activities));
    }

    public void removeActivityFromPassenger(Long passengerId, Long activityId) {
        Optional<Passenger> optionalPassenger = passengers.stream()
                .filter(passenger -> passenger.getId().equals(passengerId))
                .findFirst();
        optionalPassenger.ifPresent(passenger -> passenger.removeActivityById(activityId));
    }

    public void addDestinations(List<Destination> destinations) {
        this.destinations.addAll(destinations);
    }

    public void removeDestinationById(Long destinationId) {
        this.destinations.removeIf(destination -> destination.getId().equals(destinationId));
    }
    // Method to print the itinerary of the travel package
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

    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        System.out.println("Passenger List:");
        for (Passenger passenger : passengers) {
            System.out.println("- Name: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    public void printPassengerDetails() {
        System.out.println("Travel Package: " + name);
        for (Passenger passenger : passengers) {
            System.out.println("Passenger Name: " + passenger.getName());
            System.out.println("Passenger Number: " + passenger.getPassengerNumber());
            System.out.println("Balance: " + passenger.getBalance());
            System.out.println("Activities:");
            for (Activity activity : passenger.getActivities()) {
                System.out.println("  - Activity Name: " + activity.getName());
                System.out.println("    Destination: " + activity.getDestination().getName());
                System.out.println("    Price Paid: " + activity.getCost());
            }
        }
    }
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