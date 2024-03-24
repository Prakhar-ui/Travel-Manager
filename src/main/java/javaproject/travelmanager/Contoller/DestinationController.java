package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing destinations.
 */
@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    /**
     * Endpoint to create a new destination.
     * @param destinationDTO The DTO (Data Transfer Object) representing the destination to be created.
     * @return ResponseEntity containing the created Destination object and HTTP status code 201 (CREATED).
     */
    @PostMapping("/add")
    public ResponseEntity<Destination> createDestination(@RequestBody DestinationDTO destinationDTO) {
        Destination createdDestination = destinationService.createDestination(destinationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDestination);
    }

    /**
     * Endpoint to retrieve a destination by its ID.
     * @param id The ID of the destination to retrieve.
     * @return ResponseEntity containing the Destination object with HTTP status code 200 (OK), or status code 404 (NOT FOUND) if the destination does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Destination destination = destinationService.getDestination(id);
        if (destination != null) {
            return new ResponseEntity<>(destination, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve all destinations.
     * @return ResponseEntity containing a list of all destinations with HTTP status code 200 (OK), or status code 204 (NO CONTENT) if there are no destinations.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        if (!destinations.isEmpty()) {
            return ResponseEntity.ok(destinations);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Endpoint to update an existing destination.
     * @param id The ID of the destination to update.
     * @param destinationDTO The DTO representing the updated destination data.
     * @return ResponseEntity containing the updated Destination object with HTTP status code 200 (OK).
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody DestinationDTO destinationDTO) {
        Destination updatedDestination = destinationService.updateDestination(id, destinationDTO);
        return ResponseEntity.ok(updatedDestination);
    }

    /**
     * Endpoint to delete a destination by its ID.
     * @param id The ID of the destination to delete.
     * @return ResponseEntity with no content and HTTP status code 204 (NO CONTENT) upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }
}

