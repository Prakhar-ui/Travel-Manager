package javaproject.travelmanager.Contoller;
import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.DTO.TravelPackageDTO;
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

    @PostMapping("/{travelPackageId}/add")
    public ResponseEntity<TravelPackage> addTravelPackage(@RequestBody TravelPackageDTO travelPackageDTO) {
        TravelPackage createdTravelPackage = travelPackageService.addTravelPackage(travelPackageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTravelPackage);
    }

    @PutMapping("/{travelPackageId}/edit")
    public ResponseEntity<TravelPackage> updateTravelPackage(@PathVariable Long travelPackageId, @RequestBody TravelPackageDTO travelPackageDTO) {
        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(travelPackageId, travelPackageDTO);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{travelPackageId}/add-activity-to-passenger/{passengerId}/{activityId}")
    public ResponseEntity<TravelPackage> addActivityToPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @PathVariable Long activityId) {

        TravelPackage updatedTravelPackage = travelPackageService.addActivityToPassenger(travelPackageId, passengerId, activityId);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<TravelPackage>> getAllTravelPackages() {
        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages();
        return ResponseEntity.ok(travelPackages);
    }

    @PostMapping("/{travelPackageId}/add-destination/{destinationId}")
    public ResponseEntity<TravelPackage> addDestinationToTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long destinationId) {

        TravelPackage updatedTravelPackage = travelPackageService.addDestinationToTravelPackage(travelPackageId, destinationId);
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
            @RequestBody List<Long> destinationsId) {

        TravelPackage updatedTravelPackage = travelPackageService.addDestinationsToTravelPackage(travelPackageId, destinationsId);
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
            @RequestBody List<Long> activitiesId) {

        TravelPackage updatedTravelPackage = travelPackageService.addActivitiesToPassenger(travelPackageId, passengerId, activitiesId);
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
            @RequestBody  List<Long> passengersId) {

        TravelPackage updatedTravelPackage = travelPackageService.addPassengersToTravelPackage(travelPackageId, passengersId);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{travelPackageId}/print-itinerary")
    public ResponseEntity<String> printItinerary(@PathVariable Long travelPackageId) {
        travelPackageService.printItinerary(travelPackageId);
        return ResponseEntity.ok("Itinerary printed.");
    }

    @GetMapping("/{travelPackageId}/print-passenger-list")
    public ResponseEntity<String> printPassengerList(@PathVariable Long travelPackageId) {
        travelPackageService.printPassengerList(travelPackageId);
        return ResponseEntity.ok("Passenger List printed.");
    }

    @GetMapping("/{travelPackageId}/print-passenger-details")
    public ResponseEntity<String> printPassengerDetails(@PathVariable Long travelPackageId) {
        travelPackageService.printPassengerDetails(travelPackageId);
        return ResponseEntity.ok("Passenger Details printed.");
    }

    @GetMapping("/{travelPackageId}/print-available-activities")
    public ResponseEntity<String> printAvailableActivities(@PathVariable Long travelPackageId) {
        travelPackageService.printAvailableActivities(travelPackageId);
        return ResponseEntity.ok("Available Activities printed.");
    }
}

