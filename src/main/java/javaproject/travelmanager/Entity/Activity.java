package javaproject.travelmanager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private int remaining;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @JsonIgnore
    private Destination destination;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.destination = null;
        this.remaining = capacity;
    }

    public void selectActivity() {
        if (remaining > 0){
            this.remaining--;
        }
    }

    public void unSelectActivity() {
        if (remaining < capacity){
            this.remaining++;
        }
    }

    // Method to add this activity to a destination
    public void addToDestination(Destination destination) {
        this.destination = destination;
        destination.addActivity(this);
    }

    // Method to remove this activity from a destination
    public void removeFromDestination() {
        if (this.destination != null) {
            this.destination.removeActivity(this);
            this.destination = null;
        }
    }
}
