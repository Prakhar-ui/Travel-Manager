package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.DTO.PassengerDTO;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.ActivityRepository;
import javaproject.travelmanager.Repository.DestinationRepository;
import javaproject.travelmanager.Repository.PassengerRepository;
import javaproject.travelmanager.Repository.TravelPackageRepository;
import javaproject.travelmanager.Service.Implementation.PassengerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the PassengerService class. It tests the functionality of methods
 * such as adding, retrieving, updating, and deleting passengers.
 */
@SpringBootTest
public class testPassengerServiceImpl {

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private TravelPackageRepository travelPackageRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testAddPassenger() {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("Test Passenger");
        passengerDTO.setPassengerNumber("12345");
        passengerDTO.setPassengerType(PassengerType.STANDARD);
        passengerDTO.setBalance(2000);

        when(passengerRepository.save(any())).thenReturn(new StandardPassenger(passengerDTO.getName(),passengerDTO.getPassengerNumber(),passengerDTO.getPassengerType(),passengerDTO.getBalance()));

        Passenger savedPassenger = passengerService.createPassenger(passengerDTO);

        assertNotNull(savedPassenger);
        assertEquals("Test Passenger", savedPassenger.getName());
        assertEquals("12345", savedPassenger.getPassengerNumber());
        assertEquals(PassengerType.STANDARD, savedPassenger.getPassengerType());
        assertEquals(2000, savedPassenger.getBalance());
        // Add more assertions for other properties
    }

    @Test
    void testGetPassengerById() {
        Long id = 1L;
        Passenger newPassenger = new StandardPassenger("Test Passenger","12345",PassengerType.STANDARD,2000);
        newPassenger.setId(id);
        when(passengerRepository.findById(id)).thenReturn(Optional.of(newPassenger));

        Optional<Passenger> passenger = passengerService.getPassenger(id);

        assertNotNull(passenger);
        assertEquals("Test Passenger", passenger.get().getName());
        assertEquals("12345", passenger.get().getPassengerNumber());
        assertEquals(PassengerType.STANDARD, passenger.get().getPassengerType());
        assertEquals(2000, passenger.get().getBalance());
    }

    @Test
    void testGetAllPassengers() {
        List<Passenger> passengerList = new ArrayList<>();
        Passenger newPassenger1 = new StandardPassenger("Test Passenger","12345",PassengerType.STANDARD,2000);
        Passenger newPassenger2 = new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);

        passengerList.add(newPassenger1);
        passengerList.add(newPassenger2);

        when(passengerRepository.findAll()).thenReturn(passengerList);

        List<Passenger> passengers = passengerService.getAllPassengers();

        assertNotNull(passengers);
        assertEquals(2, passengers.size());
    }
    @Test
    void testUpdatePassenger() {
        // Mock data
        Long passengerId = 1L;
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("Updated Name");
        passengerDTO.setPassengerNumber("54321");
        passengerDTO.setPassengerType(PassengerType.valueOf("STANDARD"));
        passengerDTO.setBalance(1000.0);
        List<Long> travelPackageIds = new ArrayList<>();
        travelPackageIds.add(1L); // Assuming there is a travel package with ID 1
        List<Long> activityIds = new ArrayList<>();
        activityIds.add(1L); // Assuming there is an activity with ID 1

        // Mock optional passenger
        Passenger existingPassenger = new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);
        existingPassenger.setId(passengerId);

        // Mock travel packages and activities
        List<TravelPackage> travelPackages = new ArrayList<>();
        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId(1L);
        travelPackages.add(travelPackage);
        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1L);
        activities.add(activity);

        // Mock repository calls
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(existingPassenger));
        when(travelPackageRepository.findAllById(travelPackageIds)).thenReturn(travelPackages);
        when(activityRepository.findAllById(activityIds)).thenReturn(activities);
        when(passengerRepository.save(existingPassenger)).thenReturn(existingPassenger);

        // Call the method to be tested
        Optional<Passenger> updatedPassenger = passengerService.updatePassenger(passengerId, passengerDTO);

        // Assertions
        assertNotNull(updatedPassenger);
        assertEquals(passengerId, updatedPassenger.get().getId());
        assertEquals("Updated Name", updatedPassenger.get().getName());
        assertEquals("54321", updatedPassenger.get().getPassengerNumber());
        assertEquals(PassengerType.valueOf("STANDARD"), updatedPassenger.get().getPassengerType());
        assertEquals(1000.0, updatedPassenger.get().getBalance());
    }


@Test
void testDeletePassenger() {
    // Mock data
    Long passengerId = 1L;

    // Mock optional passenger
    Passenger existingPassenger = new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);
    existingPassenger.setId(passengerId);

    // Mock repository call
    when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(existingPassenger));
    when(passengerRepository.existsById(passengerId)).thenReturn(true);

    // Call the method to be tested
    passengerService.deletePassenger(passengerId);

    // Assertions
    verify(passengerRepository, times(1)).deleteById(passengerId);
}

    @Test
    void testAddActivityToPassenger() {
        Long passengerId = 1L;
        Long activityId = 1L;

        Passenger mockPassenger = new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);
        Activity mockActivity = new Activity();
        mockPassenger.signUpForActivity(mockActivity);


        when(activityRepository.findById(activityId)).thenReturn(Optional.of(mockActivity));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(mockPassenger));
        when(passengerRepository.save(any())).thenReturn(mockPassenger);

        Passenger result = passengerService.addActivityToPassenger( passengerId, activityId);

        assertNotNull(result);
        assertTrue(mockPassenger.getActivities().contains(mockActivity));
    }
    @Test
    void testRemoveActivityFromPassenger() {
        Long passengerId = 1L;
        Long activityId = 1L;

        Passenger mockPassenger = new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);
        Activity mockActivity = new Activity();
        mockActivity.setId(activityId);
        mockPassenger.signUpForActivity(mockActivity);

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(mockActivity));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(mockPassenger));
        mockPassenger.removeActivity(activityId);
        when(passengerRepository.save(any())).thenReturn(mockPassenger);


        Passenger result = passengerService.removeActivityFromPassenger( passengerId, activityId);

        assertNotNull(result);
        assertFalse(result.getActivities().contains(mockActivity));
        verify(passengerRepository, times(1)).save(mockPassenger);
    }
}

