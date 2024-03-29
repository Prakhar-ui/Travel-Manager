package javaproject.travelmanager.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

/**
 * Entity class representing a destination for travel packages.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    /**
     * The unique identifier for the destination.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the destination.
     */
    private String name;

    @ManyToOne
    @JoinColumn(name = "travel_package_id")
    private TravelPackage travelPackage;

    /**
     * The list of activities available at the destination.
     */
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    /**
     * Constructs a destination with the given name and an empty list of activities.
     *
     * @param name The name of the destination.
     */
    public Destination(String name) {
        this.name = name;
    }

    /**
     * Adds an Activity to the Destination.
     *
     * @param activity The activity to add.
     */
    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }


    /**
     * Removes an activity from the Destination.
     *
     * @param activity The activity to remove.
     */
    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

}
