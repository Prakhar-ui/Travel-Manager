package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing activities.
 */

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * Adds a new activity.
     *
     * @param activityDTO The DTO representing the activity to be added.
     * @return ResponseEntity containing the created activity and HTTP status 201 (Created) if successful,
     * or HTTP status 500 (Internal Server Error) if an error occurs during creation.
     */
    @PostMapping("/add")
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO activityDTO) {
        Activity createdActivity = activityService.createActivity(activityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param id The ID of the activity to retrieve.
     * @return ResponseEntity containing the retrieved activity and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the activity does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivity(id);
        return new ResponseEntity<>(activity,HttpStatus.OK);
    }

    /**
     * Retrieves all activities.
     *
     * @return ResponseEntity containing a list of all activities and HTTP status 200 (OK) if activities exist,
     * or HTTP status 204 (No Content) if no activities exist.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        if (!activities.isEmpty()) {
            return ResponseEntity.ok(activities);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Updates an existing activity.
     *
     * @param id          The ID of the activity to update.
     * @param activityDTO The DTO representing the updated activity.
     * @return ResponseEntity containing the updated activity and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the activity does not exist.
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activityDTO) {
        Activity updatedActivity = activityService.updateActivity(id, activityDTO);
        return new ResponseEntity<>(updatedActivity,HttpStatus.OK);
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param id The ID of the activity to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) if successful,
     * or HTTP status 404 (Not Found) if the activity does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
            return ResponseEntity.noContent().build();
    }
}
