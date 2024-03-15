package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
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
 * This class contains unit tests for the DestinationService class. It tests the functionality of methods
 * such as adding, retrieving, updating, and deleting destinations.
 */
@SpringBootTest
public class testDestinationService {

    @InjectMocks
    private DestinationService destinationService;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testAddDestination() {
        DestinationDTO destinationDTO = new DestinationDTO();
        destinationDTO.setName("Test Destination");

        when(destinationRepository.save(any())).thenReturn(new Destination(destinationDTO.getName()));

        Destination savedDestination = destinationService.addDestination(destinationDTO);

        assertNotNull(savedDestination);
        assertEquals("Test Destination", savedDestination.getName());
    }

    @Test
    void testGetDestinationById() {
        Long id = 1L;
        when(destinationRepository.findById(id)).thenReturn(Optional.of(new Destination()));

        Destination destination = destinationService.getDestinationById(id);

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
        activitiesId.add(1L);
        destinationDTO.setActivitiesID(activitiesId);

        Destination existingDestination = new Destination();
        existingDestination.setId(id);

        when(destinationRepository.findById(id)).thenReturn(Optional.of(existingDestination));
        when(activityRepository.findAllById(activitiesId)).thenReturn(new ArrayList<>());
        when(destinationRepository.save(any())).thenReturn(existingDestination);

        Destination updatedDestination = destinationService.updateDestination(id, destinationDTO);

        assertNotNull(updatedDestination);
        assertEquals("Updated Destination", updatedDestination.getName());
    }

    @Test
    void testDeleteDestination() {
        Long id = 1L;
        when(destinationRepository.findById(id)).thenReturn(Optional.of(new Destination()));

        boolean result = destinationService.deleteDestination(id);

        assertTrue(result);
        verify(destinationRepository, times(1)).deleteById(id);
    }
}
