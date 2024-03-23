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
    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destination> destinations = new ArrayList<>();

    /**
     * Represents the list of passengers enrolled in the travel package.
     * Each travel package can have multiple passengers.
     */
    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
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
     * Removes a destination from the travel package by ID.
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
     * Removes a passenger from the travel package by ID.
     *
     * @param passenger The passenger to remove.
     */
    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }

}