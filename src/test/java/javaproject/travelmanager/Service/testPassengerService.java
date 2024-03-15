package javaproject.travelmanager.Service;

import javaproject.travelmanager.DTO.ActivityDTO;
import javaproject.travelmanager.DTO.DestinationDTO;
import javaproject.travelmanager.DTO.PassengerDTO;
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
public class testPassengerService {

    @InjectMocks
    private PassengerService passengerService;

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

        when(passengerRepository.save(any())).thenReturn(new Passenger(passengerDTO.getName(),passengerDTO.getPassengerNumber(),passengerDTO.getPassengerType(),passengerDTO.getBalance()));

        Passenger savedPassenger = passengerService.addPassenger(passengerDTO);

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
        when(passengerRepository.findById(id)).thenReturn(Optional.of(new Passenger()));

        Passenger passenger = passengerService.getPassengerById(id);

        assertNotNull(passenger);
    }

    @Test
    void testGetAllPassengers() {
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(new Passenger());
        passengerList.add(new Passenger());

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
        Passenger existingPassenger = new Passenger();
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
        Passenger updatedPassenger = passengerService.updatePassenger(passengerId, passengerDTO);

        // Assertions
        assertNotNull(updatedPassenger);
        assertEquals(passengerId, updatedPassenger.getId());
        assertEquals("Updated Name", updatedPassenger.getName());
        assertEquals("54321", updatedPassenger.getPassengerNumber());
        assertEquals(PassengerType.valueOf("STANDARD"), updatedPassenger.getPassengerType());
        assertEquals(1000.0, updatedPassenger.getBalance());
    }


@Test
void testDeletePassenger() {
    // Mock data
    Long passengerId = 1L;

    // Mock optional passenger
    Passenger existingPassenger = new Passenger();
    existingPassenger.setId(passengerId);

    // Mock repository call
    when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(existingPassenger));

    // Call the method to be tested
    boolean result = passengerService.deletePassenger(passengerId);

    // Assertions
    assertTrue(result);
    verify(passengerRepository, times(1)).deleteById(passengerId);
}
}

