package javaproject.travelmanager.Service;

import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity getActivityById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.orElse(null);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity updateActivity(Long id, Activity activityDetails) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity existingActivity = optionalActivity.get();
            existingActivity.setName(activityDetails.getName());
            existingActivity.setDescription(activityDetails.getDescription());
            existingActivity.setCost(activityDetails.getCost());
            existingActivity.setCapacity(activityDetails.getCapacity());
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

