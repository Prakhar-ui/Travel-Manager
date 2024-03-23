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

    @Override
    public TravelPackage updateTravelPackage(Long travelPackageId, TravelPackageDTO travelPackageDTO) {
        String name = travelPackageDTO.getName();
        int passengerCapacity = travelPackageDTO.getPassengerCapacity();
        List<Long> destinationsIds = travelPackageDTO.getDestinationsIds();
        List<Long> passengersIds = travelPackageDTO.getPassengersIds();

        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));

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

    @Override
    public void addDestinationToTravelPackage(Long travelPackageId, Long destinationId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        Destination destination = destinationService.getDestination(destinationId);
        travelPackage.addDestination(destination);
    }

    @Override
    public void addPassengerToTravelPackage(Long travelPackageId, Long passengerId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        Passenger passenger = passengerService.getPassenger(passengerId);
        travelPackage.addPassenger(passenger);
    }

    @Override
    public TravelPackage getTravelPackage(Long travelPackageId) {
        return travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
    }

    @Override
    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAll();
    }

    @Override
    public List<Passenger> getAllPassengers(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        return travelPackage.getPassengers();
    }

    @Override
    public List<Destination> getAllDestinations(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        return travelPackage.getDestinations();
    }

    @Override
    public void removeDestinationFromTravelPackage(Long travelPackageId, Long destinationId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        Destination destination = destinationService.getDestination(destinationId);
        travelPackage.removeDestination(destination);
    }

    @Override
    public void removePassengerFromTravelPackage(Long travelPackageId, Long passengerId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package Not Found"));
        Passenger passenger = passengerService.getPassenger(passengerId);
        travelPackage.removePassenger(passenger);
    }

    @Override
    public void deleteTravelPackage(Long travelPackageId) {
        travelPackageRepository.deleteById(travelPackageId);
    }
}
