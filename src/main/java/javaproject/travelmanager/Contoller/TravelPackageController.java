package javaproject.travelmanager.Contoller;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel-packages")
public class TravelPackageController {

    @Autowired
    private TravelPackageService travelPackageService;

    @PostMapping("/{travelPackageId}/add-activity-to-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> addActivityToPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @RequestBody Activity activity) {

        TravelPackage updatedTravelPackage = travelPackageService.addActivityToPassenger(travelPackageId, passengerId, activity);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/add-destination")
    public ResponseEntity<TravelPackage> addDestinationToTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody Destination destination) {

        TravelPackage updatedTravelPackage = travelPackageService.addDestinationToTravelPackage(travelPackageId, destination);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/remove-destination/{destinationId}")
    public ResponseEntity<TravelPackage> removeDestinationFromTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long destinationId) {

        TravelPackage updatedTravelPackage = travelPackageService.removeDestinationFromTravelPackage(travelPackageId, destinationId);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/remove-activity-from-passenger/{passengerId}/{activityId}")
    public ResponseEntity<TravelPackage> removeActivityFromPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @PathVariable Long activityId) {

        TravelPackage updatedTravelPackage = travelPackageService.removeActivityFromPassenger(travelPackageId, passengerId, activityId);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/add-destinations")
    public ResponseEntity<TravelPackage> addDestinationsToTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody List<Destination> destinations) {

        TravelPackage updatedTravelPackage = travelPackageService.addDestinationsToTravelPackage(travelPackageId, destinations);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/add-activities-to-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> addActivitiesToPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @RequestBody List<Activity> activities) {

        TravelPackage updatedTravelPackage = travelPackageService.addActivitiesToPassenger(travelPackageId, passengerId, activities);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/add-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> addPassengerToTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId) {

        TravelPackage updatedTravelPackage = travelPackageService.addPassengerToTravelPackage(travelPackageId, passengerId);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/remove-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> removePassengerFromTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId) {

        TravelPackage updatedTravelPackage = travelPackageService.removePassengerFromTravelPackage(travelPackageId, passengerId);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/add-passengers")
    public ResponseEntity<TravelPackage> addPassengersToTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody List<Passenger> passengers) {

        TravelPackage updatedTravelPackage = travelPackageService.addPassengersToTravelPackage(travelPackageId, passengers);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
