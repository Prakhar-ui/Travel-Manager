package javaproject.travelmanager.Service;

import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Destination getDestinationById(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        return optionalDestination.orElse(null);
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination updateDestination(Long id, Destination destinationDetails) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isPresent()) {
            Destination existingDestination = optionalDestination.get();
            existingDestination.setName(destinationDetails.getName());
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
