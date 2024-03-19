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
import javaproject.travelmanager.Service.Implementation.PassengerServiceImpl;
import javaproject.travelmanager.Service.Implementation.TravelPackageServiceImpl;
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
    private TravelPackageServiceImpl travelPackageService;
    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private TravelPackageRepository travelPackageRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private DestinationRepository destinationRepository;




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
        when(travelPackageRepository.save(any())).thenReturn(mockTravelPackage);


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

        when(travelPackageRepository.save(any())).thenReturn(mockTravelPackage);
        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.of(mockDestination));


        TravelPackage result = travelPackageService.removeDestinationFromTravelPackage(travelPackageId, destinationId);

        assertNotNull(result);
        assertFalse(result.getDestinations().contains(mockDestination));
        verify(travelPackageRepository, times(1)).save(mockTravelPackage);
    }






    @Test
    void testAddPassengerToTravelPackage() {
        Long travelPackageId = 1L;
        Long passengerId = 1L;

        TravelPackage mockTravelPackage = new TravelPackage();
        Passenger mockPassenger =  new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(mockPassenger));
        when(travelPackageRepository.save(any())).thenReturn(mockTravelPackage);

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
        Passenger passenger =  new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);
        passenger.setId(passengerId);

        when(travelPackageRepository.findById(travelPackageId)).thenReturn(Optional.of(mockTravelPackage));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(travelPackageRepository.save(any())).thenReturn(mockTravelPackage);

        TravelPackage result = travelPackageService.removePassengerFromTravelPackage(travelPackageId, passengerId);

        assertNotNull(result);
        assertFalse(result.getPassengers().contains(passenger));
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
    void testAddTravelPackage() {
        TravelPackageDTO travelPackageDTO = new TravelPackageDTO();
        travelPackageDTO.setName("Test Travel Package");
        travelPackageDTO.setPassengerCapacity(10);
        List<Long> destinationsId = List.of(1L, 2L);
        Destination destination1 = new Destination("name1");
        Destination destination2 = new Destination("name2");
        destination1.setId(1L);
        destination2.setId(2L);
        List<Long> passengersId = List.of(3L, 4L);
        Passenger passenger1 =  new StandardPassenger("Test Passenger1","12345",PassengerType.STANDARD,20000);
        Passenger passenger2 =  new StandardPassenger("Test Passenger2","123456",PassengerType.GOLD,10000);
        passenger1.setId(3L);
        passenger2.setId(4L);
        travelPackageDTO.setDestinationsIds(destinationsId);
        travelPackageDTO.setPassengersIds(passengersId);

        TravelPackage mockTravelPackage = new TravelPackage();
        // Mock behavior of destinationRepository and passengerRepository
        when(destinationRepository.findById(1L)).thenReturn(Optional.of(destination1));
        when(destinationRepository.findById(2L)).thenReturn(Optional.of(destination2));
        when(passengerRepository.findById(3L)).thenReturn(Optional.of(passenger1));
        when(passengerRepository.findById(4L)).thenReturn(Optional.of(passenger2));
        when(travelPackageRepository.save(any())).thenReturn(mockTravelPackage);

        // Call the method to be tested
        travelPackageService.createTravelPackage(travelPackageDTO);

        // Verify behavior
        verify(destinationRepository, times(2)).findById(any());
        verify(passengerRepository, times(2)).findById(any());
        verify(travelPackageRepository, times(1)).save(any(TravelPackage.class));
    }

}
