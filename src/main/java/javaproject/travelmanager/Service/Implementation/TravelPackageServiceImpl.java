package javaproject.travelmanager.Service.Implementation;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import javaproject.travelmanager.DTO.TravelPackageDTO;
import javaproject.travelmanager.Entity.*;
import javaproject.travelmanager.Repository.*;
import javaproject.travelmanager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to travel packages.
 * This service manages CRUD operations for travel packages, including creating new travel package,
 * retrieving travel packages by ID or all travel packages, updating existing travel packages,
 * and deleting travel packages.
 */
@Service
@Transactional
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;

    private final PassengerService passengerService;

    private final DestinationService destinationService;

    @Autowired
    public TravelPackageServiceImpl(TravelPackageRepository travelPackageRepository,
                                    PassengerService passengerService,
                                    DestinationService destinationService
    ) {
        this.travelPackageRepository = travelPackageRepository;
        this.passengerService = passengerService;
        this.destinationService = destinationService;
    }

    /**
     * Creates a new travel package.
     * @param travelPackageDTO The DTO containing information about the new travel package.
     * @return The newly created travel package.
     */
    @Override
    public TravelPackage createTravelPackage(TravelPackageDTO travelPackageDTO) {
        String name = travelPackageDTO.getName();
        int passengerCapacity = travelPackageDTO.getPassengerCapacity();
        List<Long> destinationsIds = travelPackageDTO.getDestinationsIds();
        List<Long> passengersIds = travelPackageDTO.getPassengersIds();

        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setName(name);
        travelPackage.setPassengerCapacity(passengerCapacity);

        if (destinationsIds != null && !destinationsIds.isEmpty()) {
            for(Long destinationId: destinationsIds){
               Destination destination = destinationService.getDestination(destinationId);
               travelPackage.addDestination(destination);
            }
        }
        if (passengersIds != null && !passengersIds.isEmpty()) {
            for(Long passengerId: passengersIds){
                Passenger passenger = passengerService.getPassenger(passengerId);
                travelPackage.addPassenger(passenger);
            }
        }
        return travelPackageRepository.save(travelPackage);
    }

    /**
     * Updates an existing travel package.
     * @param travelPackageId The ID of the travel package to be updated.
     * @param travelPackageDTO The DTO containing updated information for the travel package.
     * @return The updated travel package.
     */
    @Override
    public TravelPackage updateTravelPackage(Long travelPackageId, TravelPackageDTO travelPackageDTO) {
        String name = travelPackageDTO.getName();
        int passengerCapacity = travelPackageDTO.getPassengerCapacity();
        List<Long> destinationsIds = travelPackageDTO.getDestinationsIds();
        List<Long> passengersIds = travelPackageDTO.getPassengersIds();

        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        travelPackage.setName(name);
        travelPackage.setPassengerCapacity(passengerCapacity);

        if (destinationsIds != null && !destinationsIds.isEmpty()) {
            for(Long destinationId: destinationsIds){
                addDestinationToTravelPackage(travelPackageId,destinationId);
            }
        }
        if (passengersIds != null && !passengersIds.isEmpty()) {
            for(Long passengerId: passengersIds){
                addPassengerToTravelPackage(travelPackageId,passengerId);

            }
        }
        return travelPackageRepository.save(travelPackage);
    }

    /**
     * Adds a destination to a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param destinationId The ID of the destination to be added.
     */
    @Override
    public void addDestinationToTravelPackage(Long travelPackageId, Long destinationId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        Destination destination = destinationService.getDestination(destinationId);

        if (destination == null) {
            throw new IllegalArgumentException("Destination Not Found");
        }

        if (travelPackage.getDestinations().contains(destination)) {
            throw new IllegalStateException("Destination is already added to this travel package");
        }

        travelPackage.addDestination(destination);
    }

    /**
     * Adds a passenger to a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger to be added.
     */
    @Override
    public void addPassengerToTravelPackage(Long travelPackageId, Long passengerId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        int passengerCapacity = travelPackage.getPassengerCapacity();

        if (passengerCapacity <= 0) {
            throw new IllegalStateException("Travel package is at full capacity");
        }

        Passenger passenger = passengerService.getPassenger(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException("Passenger Not Found");
        }

        if (travelPackage.getPassengers().contains(passenger)) {
            throw new IllegalStateException("Passenger is already added to this travel package");
        }

        travelPackage.addPassenger(passenger);
        travelPackage.setPassengerCapacity(passengerCapacity - 1);
    }

    /**
     * Retrieves a travel package by ID.
     * @param travelPackageId The ID of the travel package to retrieve.
     * @return The travel package with the specified ID.
     */
    @Override
    public TravelPackage getTravelPackage(Long travelPackageId) {
        return travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
    }

    /**
     * Retrieves all travel packages.
     * @return A list of all travel packages.
     */
    @Override
    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAll();
    }

    /**
     * Retrieves all passengers enrolled in a travel package.
     * @param travelPackageId The ID of the travel package.
     * @return A list of all passengers enrolled in the travel package.
     */
    @Override
    public List<Passenger> getAllPassengers(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        return travelPackage.getPassengers();
    }

    /**
     * Retrieves all destinations included in a travel package.
     * @param travelPackageId The ID of the travel package.
     * @return A list of all destinations included in the travel package.
     */
    @Override
    public List<Destination> getAllDestinations(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        return travelPackage.getDestinations();
    }

    /**
     * Removes a destination from a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param destinationId The ID of the destination to be removed.
     */
    @Override
    public void removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        Destination destination = destinationService.getDestination(destinationId);

        if (destination == null) {
            throw new IllegalArgumentException("Destination Not Found");
        }

        if (!travelPackage.getDestinations().contains(destination)) {
            throw new IllegalStateException("Destination is not part of this travel package");
        }

        travelPackage.removeDestination(destination);
    }

    /**
     * Removes a passenger from a travel package.
     * @param travelPackageId The ID of the travel package.
     * @param passengerId The ID of the passenger to be removed.
     */
    @Override
    public void removePassengerFromTravelPackage(Long travelPackageId, Long passengerId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

        Passenger passenger = passengerService.getPassenger(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException("Passenger Not Found");
        }

        if (!travelPackage.getPassengers().contains(passenger)) {
            throw new IllegalStateException("Passenger is not part of this travel package");
        }

        int passengerCapacity = travelPackage.getPassengerCapacity();
        travelPackage.removePassenger(passenger);
        travelPackage.setPassengerCapacity(passengerCapacity + 1);
    }

    /**
     * Deletes a travel package by ID.
     * @param travelPackageId The ID of the travel package to be deleted.
     */
    @Override
    public void deleteTravelPackage(Long travelPackageId) {
        travelPackageRepository.deleteById(travelPackageId);
    }
}
