package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Destination addDestination(DestinationDTO destination) {
        // Create a new Destination entity
        Destination newDestination = new Destination();
        newDestination.setName(destination.getName());
        // Save the destination entity
        return destinationRepository.save(newDestination);
    }
    public Destination getDestinationById(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        return optionalDestination.orElse(null);
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination updateDestination(Long id, DestinationDTO destinationDetails) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isPresent()) {
            Destination existingDestination = optionalDestination.get();
            existingDestination.setName(destinationDetails.getName());

            // Check if activitiesID is not null and not empty before processing
            if (destinationDetails.getActivitiesID() != null && !destinationDetails.getActivitiesID().isEmpty()) {
                // Retrieve activities from the repository based on IDs
                List<Activity> activities = activityRepository.findAllById(destinationDetails.getActivitiesID());
                // Set activities to the existing destination
                existingDestination.setActivities(activities);
            }

            return destinationRepository.save(existingDestination);
        } else {
            return null;
        }
    }

    public boolean deleteDestination(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isPresent()) {
            destinationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
