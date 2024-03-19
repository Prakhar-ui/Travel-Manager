package javaproject.travelmanager.Entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Premium passenger.
 */
@Entity
@NoArgsConstructor
public class PremiumPassenger extends Passenger {

    public PremiumPassenger(String name, String passengerNumber, PassengerType passengerType) {
        super(name, passengerNumber, passengerType, 0); // Premium passengers have no balance
    }

    /**
     * Adds an activity to the premium passenger
     *
     * @param activity The activity to add.
     */
    @Override
    public void signUpForActivity(Activity activity) {
        getActivities().add(activity);
    }

    /**
     * Removes an activity to the premium passenger
     *
     * @param activityId The activity to add.
     */
    @Override
    public void removeActivity(Long activityId) {
        Activity activityToRemove = null;
        for (Activity activity : getActivities()) {
            if (activity.getId().equals(activityId)) {
                activityToRemove = activity;
                break;
            }
        }
        if (activityToRemove != null) {
            getActivities().remove(activityToRemove);
        } else {
            System.out.println("The passenger is not signed up for this activity.");
        }
    }
}
