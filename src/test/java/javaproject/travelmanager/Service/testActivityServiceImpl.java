package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Service.Implementation.ActivityServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the ActivityService class. It tests the functionality of methods
 * such as adding, retrieving, updating, and deleting activities.
 */
@SpringBootTest
public class testActivityServiceImpl {

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @Test
    void testAddActivity() {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("Test Activity");
        activityDTO.setDescription("Test Description");
        activityDTO.setCost(50.0);
        activityDTO.setCapacity(10);

        Destination destination = new Destination();
        destination.setId(1L);

        when(activityRepository.save(any())).thenReturn(new Activity(activityDTO.getName(),activityDTO.getDescription(),activityDTO.getCost(),activityDTO.getCapacity()));

        Activity savedActivity = activityService.createActivity(activityDTO);

        assertNotNull(savedActivity);
        assertEquals("Test Activity", savedActivity.getName());
        assertEquals("Test Description", savedActivity.getDescription());
        assertEquals(50.0, savedActivity.getCost());
        assertEquals(10, savedActivity.getCapacity());
    }

    @Test
    void testGetActivityById() {
        Long id = 1L;
        when(activityRepository.findById(id)).thenReturn(Optional.of(new Activity()));
        when(activityRepository.existsById(id)).thenReturn(true);

        Optional<Activity> activity = activityService.getActivity(id);

        assertNotNull(activity);
    }

    @Test
    void testGetAllActivities() {
        List<Activity> activityList = new ArrayList<>();
        activityList.add(new Activity());
        activityList.add(new Activity());

        when(activityRepository.findAll()).thenReturn(activityList);

        List<Activity> activities = activityService.getAllActivities();

        assertNotNull(activities);
        assertEquals(2, activities.size());
    }

    @Test
    void testUpdateActivity() {
        Long id = 1L;
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("Updated Activity");
        activityDTO.setDescription("Test Description");
        activityDTO.setCost(50.0);
        activityDTO.setCapacity(10);

        Activity existingActivity = new Activity();
        existingActivity.setId(id);

        when(activityRepository.findById(id)).thenReturn(Optional.of(existingActivity));
        when(activityRepository.existsById(id)).thenReturn(true);
        when(activityRepository.save(any(Activity.class))).thenReturn(existingActivity);


        Optional<Activity> updatedActivity = activityService.updateActivity(id, activityDTO);

        assertNotNull(updatedActivity);
        assertEquals("Updated Activity", updatedActivity.get().getName());
        assertEquals("Test Description", updatedActivity.get().getDescription());
        assertEquals(50.0, updatedActivity.get().getCost());
        assertEquals(10, updatedActivity.get().getCapacity());
    }

    @Test
    void testDeleteActivity() {
        Long id = 1L;
        when(activityRepository.findById(id)).thenReturn(Optional.of(new Activity()));
        when(activityRepository.existsById(id)).thenReturn(true);

        activityService.deleteActivity(id);

        verify(activityRepository, times(1)).deleteById(id);
    }
}

