package javaproject.travelmanager.Service;

/**
 * Service interface responsible for printing various details related to travel packages.
 */
public interface TravelPackagePrintService {

    /**
     * Prints the itinerary for the specified travel package.
     * @param travelPackageId The ID of the travel package for which the itinerary will be printed.
     */
    void printItinerary(Long travelPackageId);

    /**
     * Prints the list of passengers associated with the specified travel package.
     * @param travelPackageId The ID of the travel package for which the passenger list will be printed.
     */
    void printPassengerList(Long travelPackageId);

    /**
     * Prints detailed information about passengers associated with the specified travel package.
     * @param travelPackageId The ID of the travel package for which passenger details will be printed.
     */
    void printPassengerDetails(Long travelPackageId);

    /**
     * Prints the list of available activities for the specified travel package.
     * @param travelPackageId The ID of the travel package for which available activities will be printed.
     */
    void printAvailableActivities(Long travelPackageId);
}
