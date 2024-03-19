package javaproject.travelmanager.Service;


import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the TravelPackageService class. It utilizes Mockito for mocking dependencies and Spring Boot's @SpringBootTest
 * annotation for integration testing.
 */
@SpringBootTest
public class testTravelPackageServiceImpl {

    @InjectMocks
    private TravelPackageService travelPackageService;
    @Mock
    private TravelPackageRepository travelPackageRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testAddActivityToPassenger() {
        Long travelPackageId = 1L;
        Long passengerId = 1L;
        Long activityId = 1L;

        TravelPackage mockTravelPackage = new TravelPackage();
        Passenger mockPassenger = new Passenger();
        Activity mockActivity = new Activity();

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(mockActivity));

        mockTravelPackage.getPassengers().add(mockPassenger);
        mockPassenger.setId(passengerId);

        TravelPackage result = travelPackageService.addActivityToPassenger(travelPackageId, passengerId, activityId);

        assertNotNull(result);
        assertTrue(result.getPassengers().contains(mockPassenger));
        assertTrue(mockPassenger.getActivities().contains(mockActivity));
     }

    @Test
    void testAddDestinationToTravelPackage() {


        TravelPackage mockTravelPackage = new TravelPackage("Test Travel Package",10);

        Destination mockDestination = new Destination("Test Destination");
        mockTravelPackage.setId(1L);
        mockDestination.setId(1L);
        Long travelPackageId = mockTravelPackage.getId();
        Long destinationId = mockDestination.getId();

        when(travelPackageRepository.findById(any())).thenReturn(Optional.of(mockTravelPackage));

        when(destinationRepository.findById(any())).thenReturn(Optional.of(mockDestination));

        TravelPackage result = travelPackageService.addDestinationToTravelPackage(travelPackageId, destinationId);

        assertNotNull(result);
        assertTrue(result.getDestinations().contains(mockDestination));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }

    @Test
    void testRemoveDestinationFromTravelPackage() {
        Long travelPackageId = 1L;
        Long destinationId = 1L;

        TravelPackage mockTravelPackage = new TravelPackage();
        mockTravelPackage.setId(1L);
        Destination mockDestination = new Destination();
        mockDestination.setId(1L);
        mockTravelPackage.addDestination(mockDestination);

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        TravelPackage result = travelPackageService.removeDestinationFromTravelPackage(travelPackageId, destinationId);

        assertNotNull(result);
        assertFalse(result.getDestinations().contains(mockDestination));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }

    @Test
    void testRemoveActivityFromPassenger() {
        Long travelPackageId = 1L;
        Long passengerId = 1L;
        Long activityId = 1L;

        TravelPackage mockTravelPackage = new TravelPackage();
        mockTravelPackage.setId(1L);
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        Activity mockActivity = new Activity();
        mockActivity.setId(1L);
        mockPassenger.addActivity(mockActivity);
        mockTravelPackage.addPassenger(mockPassenger);

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        TravelPackage result = travelPackageService.removeActivityFromPassenger(travelPackageId, passengerId, activityId);

        assertNotNull(result);
        assertFalse(result.getPassengers().getFirst().getActivities().contains(mockActivity));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }

    @Test
    void testAddDestinationsToTravelPackage_TravelPackageNotFound() {
        Long travelPackageId = 1L;
        List<Long> destinationIds = Arrays.asList(1L, 2L, 3L);

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.empty());

        TravelPackage result = travelPackageService.addDestinationsToTravelPackage(travelPackageId, destinationIds);

        assertNull(result);
        verify(travelPackageRepository, never()).save(any());
    }


    @Test
    void testAddActivitiesToPassenger() {
        Long travelPackageId = 1L;
        Long passengerId = 1L;
        List<Long> activitiesId = Arrays.asList(1L, 2L, 3L);

        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity1.setId(2L);
        Activity activity3 = new Activity();
        activity1.setId(3L);

        TravelPackage mockTravelPackage = new TravelPackage();
        mockTravelPackage.setId(1L);
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        mockTravelPackage.addPassenger(mockPassenger);
        List<Activity> activities = Arrays.asList(activity1, activity2, activity3);

        when(travelPackageRepository.findById(mockTravelPackage.getId())).thenReturn(Optional.of(mockTravelPackage));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(mockPassenger));
        when(activityRepository.findAllById(activitiesId)).thenReturn(activities);

        TravelPackage result = travelPackageService.addActivitiesToPassenger(mockTravelPackage.getId(), passengerId, activitiesId);

        assertNotNull(result);
        assertEquals(activities.size(), mockPassenger.getActivities().size());
        assertTrue(mockPassenger.getActivities().containsAll(activities));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }


    @Test
    void testAddPassengerToTravelPackage() {
        Long travelPackageId = 1L;
        Long passengerId = 1L;

        TravelPackage mockTravelPackage = new TravelPackage();
        Passenger mockPassenger = new Passenger();

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(mockPassenger));

        TravelPackage result = travelPackageService.addPassengerToTravelPackage(travelPackageId, passengerId);

        assertNotNull(result);
        assertTrue(result.getPassengers().contains(mockPassenger));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }

    @Test
    void testRemovePassengerFromTravelPackage() {
        Long travelPackageId = 1L;
        Long passengerId = 1L;

        TravelPackage mockTravelPackage = new TravelPackage();
        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        TravelPackage result = travelPackageService.removePassengerFromTravelPackage(travelPackageId, passengerId);

        assertNotNull(result);
        assertFalse(result.getPassengers().contains(passenger));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }

    @Test
    void testAddPassengersToTravelPackage() {
        Long travelPackageId = 1L;
        List<Long> passengersId = Arrays.asList(1L, 2L);

        TravelPackage mockTravelPackage = new TravelPackage();
        Passenger passenger1 = new Passenger();
        passenger1.setId(1L);
        Passenger passenger2 = new Passenger();
        passenger2.setId(2L);

        // Mock optional travel package
        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));
        // Mock passenger repository
        when(passengerRepository.findAllById(passengersId)).thenReturn(Arrays.asList(passenger1, passenger2));

        // Call the method to be tested
        TravelPackage result = travelPackageService.addPassengersToTravelPackage(travelPackageId, passengersId);

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.getPassengers().size());
        assertTrue(result.getPassengers().contains(passenger1));
        assertTrue(result.getPassengers().contains(passenger2));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }


    @Test
    void testGetAllTravelPackages() {
        // Mock data
        List<TravelPackage> mockTravelPackages = new ArrayList<>();
        mockTravelPackages.add(new TravelPackage());
        mockTravelPackages.add(new TravelPackage());

        // Mock behavior of travelPackageRepository
        when(travelPackageRepository.findAll()).thenReturn(mockTravelPackages);

        // Call the method to be tested
        List<TravelPackage> result = travelPackageService.getAllTravelPackages();

        // Assertions
        assertNotNull(result);
        assertEquals(mockTravelPackages.size(), result.size());
        assertEquals(mockTravelPackages, result);
        verify(travelPackageRepository, times(1)).findAll();
    }

    @Test
    void testPrintItinerary() {
        Long travelPackageId = 1L;
        TravelPackage mockTravelPackage = new TravelPackage();
        mockTravelPackage.setId(travelPackageId);

        // Mock behavior of travelPackageRepository
        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        // Call the method to be tested
        travelPackageService.printItinerary(travelPackageId);

        // Verify behavior
        verify(travelPackageRepository, times(1)).findById(travelPackageId);
    }

    @Test
    void testPrintPassengerList() {
        Long travelPackageId = 1L;
        TravelPackage mockTravelPackage = new TravelPackage();
        mockTravelPackage.setId(travelPackageId);

        // Mock behavior of travelPackageRepository
        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        // Call the method to be tested
        travelPackageService.printPassengerList(travelPackageId);

        // Verify behavior
        verify(travelPackageRepository, times(1)).findById(travelPackageId);
    }

    @Test
    void testPrintAvailableActivities() {
        Long travelPackageId = 1L;
        TravelPackage mockTravelPackage = new TravelPackage();
        mockTravelPackage.setId(travelPackageId);

        // Mock behavior of travelPackageRepository
        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));

        // Call the method to be tested
        travelPackageService.printAvailableActivities(travelPackageId);

        // Verify behavior
        verify(travelPackageRepository, times(1)).findById(travelPackageId);

    }

    @Test
    void testAddTravelPackage() {
        TravelPackageDTO travelPackageDTO = new TravelPackageDTO();
        travelPackageDTO.setName("Test Travel Package");
        travelPackageDTO.setPassengerCapacity(10);
        List<Long> destinationsId = List.of(1L, 2L);
        List<Long> passengersId = List.of(3L, 4L);
        travelPackageDTO.setDestinationsId(destinationsId);
        travelPackageDTO.setPassengersId(passengersId);

        TravelPackage mockTravelPackage = new TravelPackage();
        // Mock behavior of destinationRepository and passengerRepository
        when(destinationRepository.findAllById(destinationsId)).thenReturn(List.of(new Destination(), new Destination()));
        when(passengerRepository.findAllById(passengersId)).thenReturn(List.of(new Passenger(), new Passenger()));

        // Call the method to be tested
        travelPackageService.addTravelPackage(travelPackageDTO);

        // Verify behavior
        verify(destinationRepository, times(1)).findAllById(destinationsId);
        verify(passengerRepository, times(1)).findAllById(passengersId);
        verify(travelPackageRepository, times(1)).save(any(TravelPackage.class));
    }


    @Test
    void testUpdateTravelPackage_WithDestinationsAndPassengers() {
        Long travelPackageId = 1L;
        TravelPackageDTO travelPackageDTO = new TravelPackageDTO();
        travelPackageDTO.setName("Updated Travel Package");
        travelPackageDTO.setPassengerCapacity(20);
        List<Long> destinationsId = List.of(1L, 2L);
        List<Long> passengersId = List.of(3L, 4L);
        travelPackageDTO.setDestinationsId(destinationsId);
        travelPackageDTO.setPassengersId(passengersId);

        TravelPackage existingTravelPackage = new TravelPackage();
        existingTravelPackage.setId(travelPackageId);
        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(existingTravelPackage));
        when(destinationRepository.findAllById(destinationsId)).thenReturn(List.of(new Destination(), new Destination()));
        when(passengerRepository.findAllById(passengersId)).thenReturn(List.of(new Passenger(), new Passenger()));

        travelPackageService.updateTravelPackage(travelPackageId, travelPackageDTO);

        verify(travelPackageRepository, times(1)).findById(travelPackageId);
        verify(destinationRepository, times(1)).findAllById(destinationsId);
        verify(passengerRepository, times(1)).findAllById(passengersId);


    }
}
