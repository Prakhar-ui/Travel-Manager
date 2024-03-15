package javaproject.travelmanager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing an activity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    /**
     * The unique identifier of the activity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the activity.
     */
    private String name;

    /**
     * The description of the activity.
     */
    private String description;

    /**
     * The cost of the activity.
     */
    private double cost;

    /**
     * The capacity of the activity.
     */
    private int capacity;

    /**
     * The remaining capacity of the activity.
     */
    private int remaining;

    /**
     * The destination where the activity takes place.
     */
    @ManyToOne
    @JoinColumn(name = "destination_id")
    @JsonIgnore
    private Destination destination;

    /**
     * Constructor to create an activity with specified attributes.
     *
     * @param name        The name of the activity.
     * @param description The description of the activity.
     * @param cost        The cost of the activity.
     * @param capacity    The capacity of the activity.
     */
    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.destination = null;
        this.remaining = capacity;
    }

    /**
     * Method to decrease the remaining capacity of the activity when selected by a passenger.
     */
    public void selectActivity() {
        if (remaining > 0) {
            this.remaining--;
        }
    }

    /**
     * Method to increase the remaining capacity of the activity when unselected by a passenger.
     */
    public void unSelectActivity() {
        if (remaining < capacity) {
            this.remaining++;
        }
    }

    /**
     * Method to add this activity to a destination.
     *
     * @param destination The destination to add the activity to.
     */
    public void addToDestination(Destination destination) {
        this.destination = destination;
        destination.addActivity(this);
    }

    /**
     * Method to remove this activity from a destination.
     */
    public void removeFromDestination() {
        if (this.destination != null) {
            this.destination.removeActivity(this);
            this.destination = null;
        }
    }
}

