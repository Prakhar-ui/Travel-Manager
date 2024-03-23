package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.*;
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
    private ActivityService activityService;

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

        Destination destination = destinationService.getDestination(id);

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
        destinationDTO.setActivitiesIds(activitiesId);

        Destination existingDestination = new Destination();
        existingDestination.setId(id);

        when(destinationRepository.findById(any())).thenReturn(Optional.of(existingDestination));
        when(destinationRepository.save(any())).thenReturn(existingDestination);

        Destination updatedDestination = destinationService.updateDestination(id, destinationDTO);

        assertNotNull(updatedDestination);
        assertEquals("Updated Destination", updatedDestination.getName());
    }

    @Test
    void testDeleteDestination() {
        Long id = 1L;

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

        when(activityService.getActivity(any())).thenReturn(mockActivity);
        when(destinationRepository.findById(any())).thenReturn(Optional.of(mockDestination));


        destinationService.addActivityToDestination( destinationId, activityId);

        assertTrue(mockDestination.getActivities().contains(mockActivity));
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

        when(activityService.getActivity(activityId)).thenReturn(mockActivity);
        when(destinationRepository.findById(any())).thenReturn(Optional.of(mockDestination));

        destinationService.removeActivityFromDestination( destinationId, activityId);

        assertFalse(mockDestination.getActivities().contains(mockActivity));
    }
}
