package javaproject.travelmanager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

/**
 * Entity class representing a passenger.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
     * The type of the passenger (standard, gold, or premium).
     */
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;

    /**
     * The balance of the passenger (applicable for standard and gold passengers).
     */
    private double balance;

    /**
     * The list of travel packages associated with the passenger.
     */
    @ManyToMany
    @JoinTable(name = "Passenger_travelPackages",
            joinColumns = @JoinColumn(name = "passenger_id"))
    @JsonIgnore
    private List<TravelPackage> travelPackages = new ArrayList<>();

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
        this.activities = new ArrayList<>();
        this.travelPackages = new ArrayList<>();
    }

    /**
     * Adds an activity to the passenger.
     *
     * @param activity The activity to add.
     */
    public void addActivity(Activity activity) {
        activity.selectActivity();
        if (this.passengerType != PassengerType.PREMIUM){
            this.balance -= activity.getCost();
        }
        this.activities.add(activity);
    }

    /**
     * Removes an activity from the passenger.
     *
     * @param activity The activity to remove.
     */
    public void removeActivity(Activity activity) {
        activity.unSelectActivity();
        if (this.passengerType != PassengerType.PREMIUM){
            this.balance += activity.getCost();
        }
        this.activities.remove(activity);
    }

    /**
     * Adds a travel package to the passenger.
     *
     * @param travelPackage The travel package to add.
     */
    public void addTravelPackage(TravelPackage travelPackage) {
        this.travelPackages.add(travelPackage);
    }

    /**
     * Removes a travel package from the passenger.
     *
     * @param travelPackage The travel package to remove.
     */
    public void removeTravelPackage(TravelPackage travelPackage) {
        this.travelPackages.remove(travelPackage);
    }

    /**
     * Removes an activity from the passenger by its ID.
     *
     * @param activityId The ID of the activity to remove.
     */
    public void removeActivityById(Long activityId) {
        for (Activity destActivity : activities) {
            if (destActivity.getId().equals(activityId)) {
                destActivity.unSelectActivity();
                removeActivity(destActivity);
                return;
            }
        }
    }
}
