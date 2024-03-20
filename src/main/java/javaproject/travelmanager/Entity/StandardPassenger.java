package javaproject.travelmanager.Entity;

import jakarta.persistence.Entity;
import javaproject.travelmanager.Exception.ActivityNotFoundException;
import javaproject.travelmanager.Exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Standard passenger.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class StandardPassenger extends Passenger{

    public StandardPassenger(String name, String passengerNumber, PassengerType passengerType, double balance) {
        super(name, passengerNumber, passengerType,balance);
    }

    /**
     * Adds an activity to the standard passenger
     *
     * @param activity The activity to add.
     */
    @Override
    public void signUpForActivity(Activity activity) throws InsufficientBalanceException {
        if (getBalance() >= activity.getCost()) {
            double newBalance = getBalance() - activity.getCost();
            setBalance(newBalance);
            getActivities().add(activity);
        } else {
            throw new InsufficientBalanceException("Insufficient balance to sign up for the activity.");
        }
    }

    /**
     * Removes an activity to the standard passenger
     *
     * @param activityId The activity to add.
     */
    @Override
    public void removeActivity(Long activityId) throws ActivityNotFoundException {
        Activity activityToRemove = null;
        for (Activity activity : getActivities()) {
            if (activity.getId().equals(activityId)) {
                activityToRemove = activity;
                break;
            }
        }
        if (activityToRemove != null) {
            double newBalance = getBalance() + activityToRemove.getCost();
            setBalance(newBalance);
            getActivities().remove(activityToRemove);
        } else {
            throw new ActivityNotFoundException("The passenger is not signed up for this activity.");
        }
    }
}
