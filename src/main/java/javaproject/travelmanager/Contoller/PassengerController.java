package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing passengers.
 */
@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    /**
     * Adds a new passenger.
     *
     * @param passengerDTO The DTO representing the passenger to be added.
     * @return ResponseEntity containing the created passenger and HTTP status 201 (Created) if successful,
     * or HTTP status 500 (Internal Server Error) if an error occurs during creation.
     */
    @PostMapping("/add")
    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        Passenger createdPassenger = passengerService.createPassenger(passengerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    /**
     * Retrieves a passenger by its ID.
     *
     * @param id The ID of the passenger to retrieve.
     * @return ResponseEntity containing the retrieved passenger and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the passenger does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Passenger passenger = passengerService.getPassenger(id);
        return new ResponseEntity<>(passenger,HttpStatus.OK);
    }

    /**
     * Retrieves all passengers.
     *
     * @return ResponseEntity containing a list of all passengers and HTTP status 200 (OK) if passengers exist,
     * or HTTP status 204 (No Content) if no passengers exist.
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
     * Updates an existing passenger.
     *
     * @param id           The ID of the passenger to update.
     * @param passengerDTO The DTO representing the updated passenger.
     * @return ResponseEntity containing the updated passenger and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the passenger does not exist.
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerDTO);
        return new ResponseEntity<>(updatedPassenger,HttpStatus.OK);

    }

    /**
     * Deletes a passenger by its ID.
     *
     * @param id The ID of the passenger to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) if successful,
     * or HTTP status 404 (Not Found) if the passenger does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
            return ResponseEntity.noContent().build();
    }
}

