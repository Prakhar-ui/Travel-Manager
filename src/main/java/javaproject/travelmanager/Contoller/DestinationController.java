package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping("/add")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination) {
        Destination createdDestination = destinationService.addDestination(destination);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDestination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Destination destination = destinationService.getDestinationById(id);
        if (destination != null) {
            return ResponseEntity.ok(destination);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        if (!destinations.isEmpty()) {
            return ResponseEntity.ok(destinations);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destinationDetails) {
        Destination updatedDestination = destinationService.updateDestination(id, destinationDetails);
        if (updatedDestination != null) {
            return ResponseEntity.ok(updatedDestination);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        boolean deleted = destinationService.deleteDestination(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
