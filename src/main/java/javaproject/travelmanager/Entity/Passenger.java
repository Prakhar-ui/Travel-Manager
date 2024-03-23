package javaproject.travelmanager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import javaproject.travelmanager.Exception.ActivityNotFoundException;
import javaproject.travelmanager.Exception.InsufficientActivityCapacityException;
import javaproject.travelmanager.Exception.InsufficientBalanceException;
import lombok.*;

import java.util.*;

/**
 * Entity class representing a passenger.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Passenger {
    /**
     * The unique identifier of the passenger.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the passenger.
     */
    private String name;

    /**
     * The passenger number of the passenger.
     */
    private String passengerNumber;

    /**
     * The balance of the passenger (applicable for standard and gold passengers).
     */
    private double balance;

    /**
     * The type of the passenger (standard, gold, or premium).
     */
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;

    /**
     * The list of travel packages associated with the passenger.
     */
    @ManyToOne
    @JoinColumn(name = "travel_package_id")
    private TravelPackage travelPackage;

    /**
     * The list of activities associated with the passenger.
     */
    @ManyToMany
    @JoinTable(
            name = "passenger_activities",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activities = new ArrayList<>();

    /**
     * Constructs a passenger with the specified name, passenger number, passenger type, and balance.
     *
     * @param name            The name of the passenger.
     * @param passengerNumber The passenger number of the passenger.
     * @param passengerType   The type of the passenger (standard, gold, or premium).
     * @param balance         The balance of the passenger (applicable for standard and gold passengers).
     */
    public Passenger(String name, String passengerNumber, PassengerType passengerType, double balance) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.passengerType = passengerType;
        this.balance = balance;
    }

    /**
     * Adds a passenger to the travel package.
     *
     * @param activity The passenger to add.
     */
    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }


    /**
     * Removes a activity from the travel package by ID.
     *
     * @param activity The passenger to remove.
     */
    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }
}
