package javaproject.travelmanager.Service;

import org.springframework.stereotype.Service;

public interface TravelPackagePrintService {


    void printItinerary(Long travelPackageId);

    void printPassengerList(Long travelPackageId);

    void printPassengerDetails(Long travelPackageId);

    void printAvailableActivities(Long travelPackageId);
}
