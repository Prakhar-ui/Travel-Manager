package javaproject.travelmanager.Entity;

/**
 * Entity class representing a Standard passenger.
 */
public class StandardPassenger extends Passenger{

    public StandardPassenger(String name, String passengerNumber, PassengerType passengerType, double balance) {
        super(name, passengerNumber, passengerType, balance);
    }

    /**
     * Adds an activity to the standard passenger
     *
     * @param activity The activity to add.
     */
    @Override
    public void signUpForActivity(Activity activity) {
        if (getBalance() >= activity.getCost()) {
            double newBalance = getBalance() - activity.getCost();
            setBalance(newBalance);
            getActivities().add(activity);
        } else {
            System.out.println("Insufficient balance to sign up for the activity.");
        }
    }

    /**
     * Removes an activity to the standard passenger
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
            double newBalance = getBalance() + activityToRemove.getCost();
            setBalance(newBalance);
            getActivities().remove(activityToRemove);
        } else {
            System.out.println("The passenger is not signed up for this activity.");
        }
    }
}
