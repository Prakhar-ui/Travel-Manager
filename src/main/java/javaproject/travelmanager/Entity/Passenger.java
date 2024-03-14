package javaproject.travelmanager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String passengerNumber;
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
    private double balance;

    @ManyToMany
    @JoinTable(name = "Passenger_travelPackages",
            joinColumns = @JoinColumn(name = "passenger_id"))
    @JsonIgnore
    private List<TravelPackage> travelPackages = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "passenger_activities",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activities;

    public Passenger(String name, String passengerNumber, PassengerType passengerType, double balance) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.passengerType = passengerType;
        this.balance = balance;
        this.activities = new ArrayList<>();
        this.travelPackages = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        activity.selectActivity();
        if (this.passengerType != PassengerType.PREMIUM){
            this.balance -= activity.getCost();
        }
        this.activities.add(activity);
        Destination destination = activity.getDestination();
        destination.removeActivity(activity);
        destination.addActivity(activity);
    }

    public void removeActivity(Activity activity) {
        activity.unSelectActivity();
        this.balance += activity.getCost();
        this.activities.remove(activity);
        Destination destination = activity.getDestination();
        destination.removeActivity(activity);
        destination.addActivity(activity);
    }

    public void addTravelPackage(TravelPackage travelPackage) {
        this.travelPackages.add(travelPackage);
    }

    public void removeTravelPackage(TravelPackage travelPackage) {
        this.travelPackages.remove(travelPackage);
    }

    // Method to add activities to the passenger
    public void addActivities(List<Activity> activities) {
        this.activities.addAll(activities);
    }

    // Method to remove an activity from the passenger by its ID
    public void removeActivityById(Long activityId) {
        this.activities.removeIf(activity -> activity.getId().equals(activityId));
    }
}
