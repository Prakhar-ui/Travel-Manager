package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing destinations.
 */
@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    /**
     * Adds a new destination.
     *
     * @param destinationDTO The DTO representing the destination to be added.
     * @return ResponseEntity containing the created destination and HTTP status 201 (Created) if successful,
     * or HTTP status 500 (Internal Server Error) if an error occurs during creation.
     */
    @PostMapping("/add")
    public ResponseEntity<Destination> createDestination(@RequestBody DestinationDTO destinationDTO) {
        Destination createdDestination = destinationService.createDestination(destinationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDestination);
    }

    /**
     * Retrieves a destination by its ID.
     *
     * @param id The ID of the destination to retrieve.
     * @return ResponseEntity containing the retrieved destination and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the destination does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Optional<Destination> destination = destinationService.getDestination(id);
        return destination.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all destinations.
     *
     * @return ResponseEntity containing a list of all destinations and HTTP status 200 (OK) if destinations exist,
     * or HTTP status 204 (No Content) if no destinations exist.
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
     * Updates an existing destination.
     *
     * @param id               The ID of the destination to update.
     * @param destinationDTO   The DTO representing the updated destination.
     * @return ResponseEntity containing the updated destination and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the destination does not exist.
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody DestinationDTO destinationDTO) {
        Optional<Destination> updatedDestination = destinationService.updateDestination(id, destinationDTO);
        return updatedDestination.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a destination by its ID.
     *
     * @param id The ID of the destination to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) if successful,
     * or HTTP status 404 (Not Found) if the destination does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
            return ResponseEntity.noContent().build();
    }
}
