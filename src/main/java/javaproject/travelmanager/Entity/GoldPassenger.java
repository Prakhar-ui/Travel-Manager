package javaproject.travelmanager.Entity;

/**
 * Entity class representing a Gold passenger.
 */
public class GoldPassenger extends Passenger {

    public GoldPassenger(String name, String passengerNumber, PassengerType passengerType, double balance) {
        super(name, passengerNumber, passengerType, balance);
    }

    /**
     * Adds an activity to the gold passenger
     *
     * @param activity The activity to add.
     */
    @Override
    public void signUpForActivity(Activity activity) {
        double discountedCost = activity.getCost() * 0.9;
        if (getBalance() >= discountedCost) {
            double newBalance = getBalance() - discountedCost;
            setBalance(newBalance);
            getActivities().add(activity);
        } else {
            System.out.println("Insufficient balance to sign up for the activity.");
        }
    }

    /**
     * Removes an activity to the gold passenger
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
            double discountedCost = activityToRemove.getCost() * 0.9; // 10% discount
            double newBalance = getBalance() + discountedCost;
            setBalance(newBalance);
            getActivities().remove(activityToRemove);
        } else {
            System.out.println("The passenger is not signed up for this activity.");
        }
    }

}