package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Service.Implementation.DestinationServiceImpl;
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
 * This class contains unit tests for the DestinationService class. It tests the functionality of methods
 * such as adding, retrieving, updating, and deleting destinations.
 */
@SpringBootTest
public class testDestinationServiceImpl {

    @InjectMocks
    private DestinationServiceImpl destinationService;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testAddDestination() {
        DestinationDTO destinationDTO = new DestinationDTO();
        destinationDTO.setName("Test Destination");

        when(destinationRepository.save(any())).thenReturn(new Destination(destinationDTO.getName()));

        Destination savedDestination = destinationService.createDestination(destinationDTO);

        assertNotNull(savedDestination);
        assertEquals("Test Destination", savedDestination.getName());
    }

    @Test
    void testGetDestinationById() {
        Long id = 1L;
        when(destinationRepository.findById(id)).thenReturn(Optional.of(new Destination()));
        when(destinationRepository.existsById(id)).thenReturn(true);

        Optional<Destination> destination = destinationService.getDestination(id);

        assertNotNull(destination);
    }


    @Test
    void testGetAllDestinations() {
        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(new Destination());
        destinationList.add(new Destination());

        when(destinationRepository.findAll()).thenReturn(destinationList);

        List<Destination> destinations = destinationService.getAllDestinations();

        assertNotNull(destinations);
        assertEquals(2, destinations.size());
    }

    @Test
    void testUpdateDestination() {
        Long id = 1L;
        DestinationDTO destinationDTO = new DestinationDTO();
        destinationDTO.setName("Updated Destination");
        List<Long> activitiesId = new ArrayList<>();
        activitiesId.add(id);
        Activity activity = new Activity();
        activity.setId(id);
        destinationDTO.setActivitiesIDs(activitiesId);

        Destination existingDestination = new Destination();
        existingDestination.setId(id);

        when(destinationRepository.existsById(id)).thenReturn(true);
        when(activityRepository.existsById(any())).thenReturn(true);
        when(destinationRepository.findById(any())).thenReturn(Optional.of(existingDestination));
        when(activityRepository.findById(any())).thenReturn(Optional.of(activity));
        when(destinationRepository.save(any())).thenReturn(existingDestination);

        Optional<Destination> updatedDestination = destinationService.updateDestination(id, destinationDTO);

        assertNotNull(updatedDestination);
        assertEquals("Updated Destination", updatedDestination.get().getName());
    }

    @Test
    void testDeleteDestination() {
        Long id = 1L;
        when(destinationRepository.findById(id)).thenReturn(Optional.of(new Destination()));
        when(destinationRepository.existsById(id)).thenReturn(true);

        destinationService.deleteDestination(id);

        verify(destinationRepository, times(1)).deleteById(id);
    }

    @Test
    void testAddActivityToDestination() {
        Long destinationId = 1L;
        Long activityId = 1L;

        Destination mockDestination =  new Destination();
        mockDestination.setId(destinationId);
        Activity mockActivity = new Activity();
        mockActivity.setId(activityId);

        when(activityRepository.findById(any())).thenReturn(Optional.of(mockActivity));
        when(destinationRepository.findById(any())).thenReturn(Optional.of(mockDestination));
        when(destinationRepository.save(any())).thenReturn(mockDestination);

        Destination result = destinationService.addActivityToDestination( destinationId, activityId);

        assertNotNull(result);
        assertTrue(result.getActivities().contains(mockActivity));
    }
    @Test
    void testRemoveActivityFromDestination() {
        Long destinationId = 1L;
        Long activityId = 1L;

        Destination mockDestination =  new Destination();
        mockDestination.setId(destinationId);
        Activity mockActivity = new Activity();
        mockActivity.setId(activityId);
        mockDestination.addActivity(mockActivity);

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(mockActivity));
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.of(mockDestination));

        mockDestination.removeActivity(1L);

        when(destinationRepository.save(any())).thenReturn(mockDestination);

        Destination result = destinationService.addActivityToDestination( destinationId, activityId);

        assertNotNull(result);
        assertFalse(result.getActivities().contains(mockActivity));
    }
}
