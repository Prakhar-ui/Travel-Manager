package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing passengers.
 */
@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    /**
     * Endpoint to create a new passenger.
     * @param passengerDTO The DTO (Data Transfer Object) representing the passenger to be created.
     * @return ResponseEntity containing the created Passenger object and HTTP status code 201 (CREATED).
     */
    @PostMapping("/add")
    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        Passenger createdPassenger = passengerService.createPassenger(passengerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    /**
     * Endpoint to retrieve a passenger by its ID.
     * @param id The ID of the passenger to retrieve.
     * @return ResponseEntity containing the Passenger object with HTTP status code 200 (OK), or status code 404 (NOT FOUND) if the passenger does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Passenger passenger = passengerService.getPassenger(id);
        if (passenger != null) {
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve all passengers.
     * @return ResponseEntity containing a list of all passengers with HTTP status code 200 (OK), or status code 204 (NO CONTENT) if there are no passengers.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        if (!passengers.isEmpty()) {
            return ResponseEntity.ok(passengers);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Endpoint to update an existing passenger.
     * @param id The ID of the passenger to update.
     * @param passengerDTO The DTO representing the updated passenger data.
     * @return ResponseEntity containing the updated Passenger object with HTTP status code 200 (OK).
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerDTO);
        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
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
}


