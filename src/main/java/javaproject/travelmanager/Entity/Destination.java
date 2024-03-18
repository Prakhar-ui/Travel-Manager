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

    /**
     * The list of activities available at the destination.
     */
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Activity> activities;

    /**
     * Constructs a destination with the given name and an empty list of activities.
     *
     * @param name The name of the destination.
     */
    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    /**
     * Adds an activity to the destination.
     *
     * @param activity The activity to add.
     */
    public void addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setDestination(this);
    }

    /**
     * Removes an activity from the destination.
     *
     * @param activityId The activity to remove.
     */
    public void removeActivity(Long activityId) {
        Optional<Activity> optionalActivity = this.activities.stream()
                .filter(activity -> activity.getId().equals(activityId))
                .findFirst();
        optionalActivity.ifPresent(activity -> {
            activity.setDestination(null);
            this.activities.remove(activity);
        });
    }
}
