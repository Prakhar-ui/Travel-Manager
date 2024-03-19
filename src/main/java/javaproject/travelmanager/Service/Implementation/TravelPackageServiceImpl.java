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

    private final PassengerRepository passengerRepository;

    private final DestinationRepository destinationRepository;


    /**
     * Constructs a new TravelPackageServiceImpl with the provided repositories.
     *
     * @param travelPackageRepository The repository for managing travel package entities.
     * @param passengerRepository     The repository for managing passenger entities.
     * @param destinationRepository  The repository for managing destination entities.
     */
    @Autowired
    public TravelPackageServiceImpl(TravelPackageRepository travelPackageRepository,
                                    PassengerRepository passengerRepository,
                                    DestinationRepository destinationRepository,
                                    ActivityRepository activityRepository,
                                    PassengerService passengerService,
                                    DestinationService destinationService
    ) {
        this.travelPackageRepository = travelPackageRepository;
        this.passengerRepository = passengerRepository;
        this.destinationRepository = destinationRepository;
    }

    /**
     * Creates a new travel package with the provided information.
     *
     * @param travelPackageDTO The DTO containing travel package information.
     * @return The newly created travel package.
     * @throws IllegalArgumentException if any referenced passenger or destination is not found.
     */
    @Override
    public TravelPackage createTravelPackage(@Valid @NotNull TravelPackageDTO travelPackageDTO) {
        TravelPackage travelPackage = new TravelPackage(travelPackageDTO.getName(),travelPackageDTO.getPassengerCapacity());

        List<Long> passengersIds = travelPackageDTO.getPassengersIds();
        List<Long> destinationsIds = travelPackageDTO.getDestinationsIds();

        if (passengersIds != null && !passengersIds.isEmpty()) {
            for(Long passengerId: passengersIds){
                Passenger passenger = passengerRepository.findById(passengerId)
                        .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));
                travelPackage.addPassenger(passenger);
            }
        }

        if (destinationsIds != null && !destinationsIds.isEmpty()) {
            for(Long destinationId: destinationsIds){
                    Destination destination = destinationRepository.findById(destinationId)
                            .orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found."));
                    travelPackage.addDestination(destination);
            }
        }
        return travelPackageRepository.save(travelPackage);
    }

    /**
     * Retrieves a travel package by its ID.
     *
     * @param travelPackageId The ID of the travel package to retrieve.
     * @return An Optional containing the travel package if found; otherwise, an empty Optional.
     * @throws IllegalArgumentException if the provided travel package ID is null or if the travel package with the given ID does not exist.
     */
    @Override
    public Optional<TravelPackage> getTravelPackage(@Valid @NotNull Long travelPackageId) {
        return Optional.ofNullable(travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found.")));
    }

    /**
     * Retrieves all travel packages.
     *
     * @return A list of all travel packages.
     */
    @Override
    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAll();
    }

    /**
     * Retrieves all passengers associated with a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @return A list of passengers associated with the travel package.
     * @throws IllegalArgumentException if the provided travel package ID is null or if the travel package with the given ID does not exist.
     */
    @Override
    public List<Passenger> getAllPassengers(@Valid @NotNull Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        return travelPackage.getPassengers();
    }

    /**
     * Retrieves all destinations associated with a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @return A list of destinations associated with the travel package.
     * @throws IllegalArgumentException if the provided travel package ID is null or if the travel package with the given ID does not exist.
     */
    @Override
    public List<Destination> getAllDestinations(@Valid @NotNull Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        return travelPackage.getDestinations();
    }

    /**
     * Updates an existing travel package with the information provided in the TravelPackageDTO.
     *
     * @param travelPackageId    The ID of the travel package to update.
     * @param travelPackageDTO   The DTO containing updated travel package information.
     * @return The updated travel package if found; otherwise, returns an empty Optional.
     * @throws IllegalArgumentException if the provided travel package ID is null or if the travel package with the given ID does not exist.
     */
    @Override
    public Optional<TravelPackage> updateTravelPackage(@Valid @NotNull Long travelPackageId,@Valid @NotNull TravelPackageDTO travelPackageDTO) {
        TravelPackage existingTravelPackage = travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        existingTravelPackage.setName(travelPackageDTO.getName());
        existingTravelPackage.setPassengerCapacity(travelPackageDTO.getPassengerCapacity());
        List<Long> passengersIds = travelPackageDTO.getPassengersIds();
        List<Long> destinationsIds = travelPackageDTO.getDestinationsIds();

        if (passengersIds != null && !passengersIds.isEmpty()) {
            for(Long passengerId: passengersIds){
                addPassengerToTravelPackage(travelPackageId,passengerId);
            }
        }

        if (destinationsIds != null && !destinationsIds.isEmpty()) {
            for(Long destinationId: destinationsIds){
                addDestinationToTravelPackage(travelPackageId,destinationId);
            }
        }
        return Optional.of(travelPackageRepository.save(existingTravelPackage));
    }

    /**
     * Adds a destination to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId   The ID of the destination to add.
     * @throws IllegalArgumentException if the provided travel package ID or destination ID is null, or if the travel package or destination with the given IDs does not exist.
     */
    @Override
    public void addDestinationToTravelPackage(@Valid @NotNull Long travelPackageId, @Valid @NotNull Long destinationId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found."));
        travelPackage.addDestination(destination);
    }

    /**
     * Removes a destination from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param destinationId   The ID of the destination to remove.
     * @throws IllegalArgumentException if the provided travel package ID or destination ID is null, or if the travel package or destination with the given IDs does not exist.
     */
    @Override
    public void removeDestinationFromTravelPackage(@Valid @NotNull Long travelPackageId, @Valid @NotNull Long destinationId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination with ID " + destinationId + " not found."));
        travelPackage.removeDestination(destinationId);
    }

    /**
     * Adds a passenger to a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger to add.
     * @return
     * @throws IllegalArgumentException if the provided travel package ID or passenger ID is null, or if the travel package or passenger with the given IDs does not exist.
     */
    @Override
    public TravelPackage addPassengerToTravelPackage(@Valid @NotNull Long travelPackageId, @Valid @NotNull  Long passengerId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));
        travelPackage.addPassenger(passenger);
        return travelPackage;
    }

    /**
     * Removes a passenger from a travel package.
     *
     * @param travelPackageId The ID of the travel package.
     * @param passengerId     The ID of the passenger to remove.
     * @throws IllegalArgumentException if the provided travel package ID or passenger ID is null, or if the travel package or passenger with the given IDs does not exist.
     */
    @Override
    public void removePassengerFromTravelPackage(@Valid @NotNull Long travelPackageId,@Valid @NotNull  Long passengerId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found."));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with ID " + passengerId + " not found."));
        travelPackage.removePassenger(passengerId);
    }

    /**
     * Deletes a travel package by its ID.
     *
     * @param travelPackageId The ID of the travel package to delete.
     * @throws IllegalArgumentException if the provided travel package ID is null, or if the travel package with the given ID does not exist.
     */
    @Override
    public void deleteTravelPackage(@Valid @NotNull Long travelPackageId) {
        if (!travelPackageRepository.existsById(travelPackageId)) {
            throw new IllegalArgumentException("Travel Package with ID " + travelPackageId + " not found.");
        }
        travelPackageRepository.deleteById(travelPackageId);
    }
}
