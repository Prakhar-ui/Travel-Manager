package javaproject.travelmanager.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setDestination(this);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setDestination(null);
    }
}
