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

/**
 * Controller class for managing travel packages.
 */
@RestController
@RequestMapping("/travel-packages")
public class TravelPackageController {

    @Autowired
    private TravelPackageService travelPackageService;

    /**
     * Adds a new travel package.
     *
     * @param travelPackageDTO The DTO representing the travel package to be added.
     * @return ResponseEntity containing the created travel package and HTTP status 201 (Created) if successful,
     * or HTTP status 500 (Internal Server Error) if an error occurs during creation.
     */
    @PostMapping("/{travelPackageId}/add")
    public ResponseEntity<TravelPackage> addTravelPackage(@RequestBody TravelPackageDTO travelPackageDTO) {
        TravelPackage createdTravelPackage = travelPackageService.addTravelPackage(travelPackageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTravelPackage);
    }

    /**
     * Updates an existing travel package.
     *
     * @param travelPackageId  The ID of the travel package to update.
     * @param travelPackageDTO The DTO representing the updated travel package.
     * @return ResponseEntity containing the updated travel package and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the travel package does not exist.
     */
    @PutMapping("/{travelPackageId}/edit")
    public ResponseEntity<TravelPackage> updateTravelPackage(@PathVariable Long travelPackageId, @RequestBody TravelPackageDTO travelPackageDTO) {
        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(travelPackageId, travelPackageDTO);
        if (updatedTravelPackage != null) {
            return ResponseEntity.ok(updatedTravelPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adds an activity to a passenger in a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger.
     * @param activityId      The ID of the activity to add.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package, passenger, or activity does not exist.
     */
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

    /**
     * Retrieves all travel packages.
     *
     * @return ResponseEntity with HTTP status 200 (OK) and a list of all travel packages.
     */
    @GetMapping("/all")
    public ResponseEntity<List<TravelPackage>> getAllTravelPackages() {
        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages();
        return ResponseEntity.ok(travelPackages);
    }

    /**
     * Adds a destination to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId   The ID of the destination to add.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package or destination does not exist.
     */
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

    /**
     * Removes a destination from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId    The ID of the destination to remove.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package or destination does not exist.
     */
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

    /**
     * Removes an activity from a passenger in a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger.
     * @param activityId      The ID of the activity to remove.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package, passenger, or activity does not exist.
     */
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

    /**
     * Adds destinations to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationsId  The IDs of the destinations to add.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package does not exist.
     */
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

    /**
     * Adds activities to a passenger in a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger.
     * @param activitiesId    The IDs of the activities to add.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package or passenger does not exist.
     */
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

    /**
     * Adds a passenger to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger to add.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package or passenger does not exist.
     */
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

    /**
     * Removes a passenger from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger to remove.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package or passenger does not exist.
     */
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

    /**
     * Adds passengers to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengersId    The IDs of the passengers to add.
     * @return ResponseEntity with HTTP status 200 (OK) and the updated travel package if successful,
     * or HTTP status 404 (Not Found) if the travel package does not exist.
     */
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

    /**
     * Prints the itinerary of a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity with HTTP status 200 (OK) and a message indicating the itinerary was printed successfully.
     */
    @GetMapping("/{travelPackageId}/print-itinerary")
    public ResponseEntity<String> printItinerary(@PathVariable Long travelPackageId) {
        travelPackageService.printItinerary(travelPackageId);
        return ResponseEntity.ok("Itinerary printed.");
    }

    /**
     * Prints the passenger list of a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity with HTTP status 200 (OK) and a message indicating the passenger list was printed successfully.
     */
    @GetMapping("/{travelPackageId}/print-passenger-list")
    public ResponseEntity<String> printPassengerList(@PathVariable Long travelPackageId) {
        travelPackageService.printPassengerList(travelPackageId);
        return ResponseEntity.ok("Passenger List printed.");
    }

    /**
     * Prints the details of passengers in a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity with HTTP status 200 (OK) and a message indicating the passenger details were printed successfully.
     */
    @GetMapping("/{travelPackageId}/print-passenger-details")
    public ResponseEntity<String> printPassengerDetails(@PathVariable Long travelPackageId) {
        travelPackageService.printPassengerDetails(travelPackageId);
        return ResponseEntity.ok("Passenger Details printed.");
    }

    /**
     * Prints the details of available activities in a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity with HTTP status 200 (OK) and a message indicating the available activities were printed successfully.
     */
    @GetMapping("/{travelPackageId}/print-available-activities")
    public ResponseEntity<String> printAvailableActivities(@PathVariable Long travelPackageId) {
        travelPackageService.printAvailableActivities(travelPackageId);
        return ResponseEntity.ok("Available Activities printed.");
    }
}

