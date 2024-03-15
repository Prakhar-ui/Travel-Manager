package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private DestinationRepository destinationRepository;

    public Activity addActivity(ActivityDTO activityDTO) {
        Activity newActivity = new Activity(activityDTO.getName(),activityDTO.getDescription(),activityDTO.getCost(),activityDTO.getCapacity());
        if (activityDTO.getDestinationId() != null){
            Optional<Destination> destination = destinationRepository.findById(activityDTO.getDestinationId());
            destination.ifPresent(newActivity::setDestination);
        }
        return activityRepository.save(newActivity);
    }

    public Activity getActivityById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.orElse(null);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity updateActivity(Long id, ActivityDTO activityDTO) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity existingActivity = optionalActivity.get();
            existingActivity.setName(activityDTO.getName());
            existingActivity.setDescription(activityDTO.getDescription());
            existingActivity.setCost(activityDTO.getCost());
            existingActivity.setCapacity(activityDTO.getCapacity());
            if (activityDTO.getDestinationId() != null){
                Optional<Destination> destination = destinationRepository.findById(activityDTO.getDestinationId());
                destination.ifPresent(existingActivity::setDestination);
            }
            return activityRepository.save(existingActivity);
        } else {
            return null;
        }
    }

    public boolean deleteActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            activityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

