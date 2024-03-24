package javaproject.travelmanager.Contoller;
import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.TravelPackage;
import javaproject.travelmanager.Service.DestinationService;
import javaproject.travelmanager.Service.PassengerService;
import javaproject.travelmanager.Service.TravelPackagePrintService;
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

    @Autowired
    private TravelPackagePrintService travelPackagePrintService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private DestinationService destinationService;

    /**
     * Endpoint to create a new travel package.
     * @param travelPackageDTO The DTO (Data Transfer Object) representing the travel package to be created.
     * @return ResponseEntity containing the created TravelPackage object and HTTP status code 201 (CREATED).
     */
    @PostMapping("/add")
    public ResponseEntity<TravelPackage> createTravelPackage(@RequestBody TravelPackageDTO travelPackageDTO) {
        TravelPackage createdTravelPackage = travelPackageService.createTravelPackage(travelPackageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTravelPackage);
    }

    /**
     * Endpoint to retrieve a travel package by its ID.
     * @param id The ID of the travel package to retrieve.
     * @return ResponseEntity containing the TravelPackage object with HTTP status code 200 (OK), or status code 404 (NOT FOUND) if the travel package does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TravelPackage> getTravelPackageById(@PathVariable Long id) {
        TravelPackage travelPackage = travelPackageService.getTravelPackage(id);
        if (travelPackage != null) {
            return ResponseEntity.status(HttpStatus.OK).body(travelPackage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint to update an existing travel package.
     * @param travelPackageId The ID of the travel package to update.
     * @param travelPackageDTO The DTO representing the updated travel package data.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<TravelPackage> updateTravelPackage(@PathVariable Long travelPackageId, @RequestBody TravelPackageDTO travelPackageDTO) {
        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(travelPackageId, travelPackageDTO);
        return ResponseEntity.ok(updatedTravelPackage);
    }

    /**
     * Endpoint to retrieve all travel packages.
     * @return ResponseEntity containing a list of all travel packages with HTTP status code 200 (OK).
     */
    @GetMapping("/all")
    public ResponseEntity<List<TravelPackage>> getAllTravelPackages() {
        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages();
        return ResponseEntity.ok(travelPackages);
    }

    /**
     * Endpoint to delete a passenger by its ID.
     * @param id The ID of the passenger to delete.
     * @return ResponseEntity with no content and HTTP status code 204 (NO CONTENT) upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to add an activity to a passenger within a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to add.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/add-activity-to-passenger/{passengerId}/{activityId}")
    public ResponseEntity<TravelPackage> addActivityToPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @PathVariable Long activityId)  {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        passengerService.addActivityToPassenger(passengerId, activityId);
        return ResponseEntity.ok(travelPackage);
    }

    /**
     * Endpoint to remove an activity from a passenger within a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activityId The ID of the activity to remove.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/remove-activity-from-passenger/{passengerId}/{activityId}")
    public ResponseEntity<TravelPackage> removeActivityFromPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @PathVariable Long activityId){

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        return new ResponseEntity<>(travelPackage,HttpStatus.OK);

    }

    /**
     * Endpoint to add activities to a passenger within a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activitiesIds List of activity IDs to add.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/add-activities-to-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> addActivitiesToPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @RequestBody List<Long> activitiesIds){

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for (Long activityId : activitiesIds){
                passengerService.addActivityToPassenger(passengerId, activityId);
            }
        }
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }

    /**
     * Endpoint to remove activities from a passenger within a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger.
     * @param activitiesIds List of activity IDs to remove.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/remove-activities-to-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> removeActivitiesToPassenger(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId,
            @RequestBody List<Long> activitiesIds){

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        if (activitiesIds != null && !activitiesIds.isEmpty()) {
            for (Long activityId : activitiesIds){
                passengerService.removeActivityFromPassenger(passengerId, activityId);
            }
        }
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }

    /**
     * Endpoint to add a destination to a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param destinationId The ID of the destination to add.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/add-destination/{destinationId}")
    public ResponseEntity<TravelPackage> addDestinationToTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long destinationId) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        travelPackageService.addDestinationToTravelPackage(travelPackageId, destinationId);
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }

    /**
     * Endpoint to remove a destination from a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param destinationId The ID of the destination to remove.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/remove-destination/{destinationId}")
    public ResponseEntity<TravelPackage> removeDestinationFromTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long destinationId) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        travelPackageService.removeDestinationFromTravelPackage(travelPackageId, destinationId);
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }

    /**
     * Endpoint to add multiple destinations to a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param destinationsIds List of destination IDs to add.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/add-destinations")
    public ResponseEntity<TravelPackage> addDestinationsToTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody List<Long> destinationsIds) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        if (destinationsIds != null && !destinationsIds.isEmpty()) {
            for (Long destinationId : destinationsIds){
                travelPackageService.addDestinationToTravelPackage(travelPackageId, destinationId);
            }
        }
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }

    /**
     * Endpoint to remove multiple destinations from a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param destinationsIds List of destination IDs to remove.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/remove-destinations")
    public ResponseEntity<TravelPackage> removeDestinationsFromTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody List<Long> destinationsIds) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        if (destinationsIds != null && !destinationsIds.isEmpty()) {
            for (Long destinationId : destinationsIds){
                travelPackageService.removeDestinationFromTravelPackage(travelPackageId, destinationId);
            }
        }
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }

    /**
     * Endpoint to add a passenger to a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger to add.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/add-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> addPassengerToTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        travelPackageService.addPassengerToTravelPackage(travelPackageId, passengerId);
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);
    }
    /**
     * Endpoint to remove a passenger from a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger to remove.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/remove-passenger/{passengerId}")
    public ResponseEntity<TravelPackage> removePassengerFromTravelPackage(
            @PathVariable Long travelPackageId,
            @PathVariable Long passengerId) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        travelPackageService.removePassengerFromTravelPackage(travelPackageId,passengerId);
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);

    }

    /**
     * Endpoint to add multiple passengers to a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengersIds List of passenger IDs to add.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/add-passengers")
    public ResponseEntity<TravelPackage> addPassengersToTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody  List<Long> passengersIds) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        if (passengersIds != null && !passengersIds.isEmpty()) {
            for (Long passengerId : passengersIds){
                travelPackageService.addPassengerToTravelPackage(travelPackageId, passengerId);
            }
        }
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);

    }

    /**
     * Endpoint to remove multiple passengers from a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengersIds List of passenger IDs to remove.
     * @return ResponseEntity containing the updated TravelPackage object with HTTP status code 200 (OK).
     */
    @PostMapping("/{travelPackageId}/remove-passengers")
    public ResponseEntity<TravelPackage> removePassengersToTravelPackage(
            @PathVariable Long travelPackageId,
            @RequestBody  List<Long> passengersIds) {

        TravelPackage travelPackage = travelPackageService.getTravelPackage(travelPackageId);
        if (passengersIds != null && !passengersIds.isEmpty()) {
            for (Long passengerId : passengersIds){
                travelPackageService.removePassengerFromTravelPackage(travelPackageId, passengerId);
            }
        }
        return new ResponseEntity<>(travelPackage, HttpStatus.OK);

    }

    /**
     * Endpoint to print the itinerary of a travel package.
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity containing a success message with HTTP status code 200 (OK).
     */
    @GetMapping("/{travelPackageId}/print-itinerary")
    public ResponseEntity<String> printItinerary(@PathVariable Long travelPackageId) {
        travelPackagePrintService.printItinerary(travelPackageId);
        return ResponseEntity.ok("Itinerary printed.");
    }

    /**
     * Endpoint to print the passenger list of a travel package.
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity containing a success message with HTTP status code 200 (OK).
     */
    @GetMapping("/{travelPackageId}/print-passenger-list")
    public ResponseEntity<String> printPassengerList(@PathVariable Long travelPackageId) {
        travelPackagePrintService.printPassengerList(travelPackageId);
        return ResponseEntity.ok("Passenger List printed.");
    }

    /**
     * Endpoint to print the details of passengers in a travel package.
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity containing a success message with HTTP status code 200 (OK).
     */
    @GetMapping("/{travelPackageId}/print-passenger-details")
    public ResponseEntity<String> printPassengerDetails(@PathVariable Long travelPackageId) {
        travelPackagePrintService.printPassengerDetails(travelPackageId);
        return ResponseEntity.ok("Passenger Details printed.");
    }

    /**
     * Endpoint to print the available activities in a travel package.
     * @param travelPackageId The ID of the travel package.
     * @return ResponseEntity containing a success message with HTTP status code 200 (OK).
     */
    @GetMapping("/{travelPackageId}/print-available-activities")
    public ResponseEntity<String> printAvailableActivities(@PathVariable Long travelPackageId) {
        travelPackagePrintService.printAvailableActivities(travelPackageId);
        return ResponseEntity.ok("Available Activities printed.");
    }
}

