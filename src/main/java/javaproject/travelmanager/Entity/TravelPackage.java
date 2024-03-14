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
}
