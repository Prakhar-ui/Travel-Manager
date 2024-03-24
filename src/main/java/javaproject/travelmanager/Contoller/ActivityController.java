package javaproject.travelmanager.Contoller;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing activities.
 */
@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * Endpoint to create a new activity.
     * @param activityDTO The DTO (Data Transfer Object) representing the activity to be created.
     * @return ResponseEntity containing the created Activity object and HTTP status code 201 (CREATED).
     */
    @PostMapping("/add")
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO activityDTO) {
        Activity createdActivity = activityService.createActivity(activityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
    }

    /**
     * Endpoint to retrieve an activity by its ID.
     * @param id The ID of the activity to retrieve.
     * @return ResponseEntity containing the Activity object with HTTP status code 200 (OK), or status code 404 (NOT FOUND) if the activity does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivity(id);
        if (activity != null) {
            return new ResponseEntity<>(activity, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve all activities.
     * @return ResponseEntity containing a list of all activities with HTTP status code 200 (OK), or status code 204 (NO CONTENT) if there are no activities.
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
     * Endpoint to update an existing activity.
     * @param id The ID of the activity to update.
     * @param activityDTO The DTO representing the updated activity data.
     * @return ResponseEntity containing the updated Activity object with HTTP status code 200 (OK).
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activityDTO) {
        Activity updatedActivity = activityService.updateActivity(id, activityDTO);
        return ResponseEntity.ok(updatedActivity);
    }

    /**
     * Endpoint to delete an activity by its ID.
     * @param id The ID of the activity to delete.
     * @return ResponseEntity with no content and HTTP status code 204 (NO CONTENT) upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}

