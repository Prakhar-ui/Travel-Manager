package javaproject.travelmanager.Entity;

import jakarta.persistence.Entity;
import javaproject.travelmanager.Exception.ActivityNotFoundException;
import javaproject.travelmanager.Exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Gold passenger.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GoldPassenger extends Passenger {

    public GoldPassenger(String name, String passengerNumber, PassengerType passengerType, double balance) {
        super(name, passengerNumber, passengerType,balance);
    }

    /**
     * Adds an activity to the gold passenger
     *
     * @param activity The activity to add.
     */
    @Override
    public void signUpForActivity(Activity activity) throws InsufficientBalanceException {
        double discountedCost = activity.getCost() * 0.9;
        if (getBalance() >= discountedCost) {
            double newBalance = getBalance() - discountedCost;
            setBalance(newBalance);
            getActivities().add(activity);
        } else {
            throw new InsufficientBalanceException("Insufficient balance to sign up for the activity.");
        }
    }

    /**
     * Removes an activity to the gold passenger
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
            double discountedCost = activityToRemove.getCost() * 0.9; // 10% discount
            double newBalance = getBalance() + discountedCost;
            setBalance(newBalance);
            getActivities().remove(activityToRemove);
        } else {
            throw new ActivityNotFoundException("The passenger is not signed up for this activity.");
        }
    }

}