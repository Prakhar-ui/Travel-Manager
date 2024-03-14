package javaproject.travelmanager.Service;

import javaproject.travelmanager.Entity.Passenger;
import javaproject.travelmanager.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public Passenger addPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public Passenger getPassengerById(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        return optionalPassenger.orElse(null);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            Passenger existingPassenger = optionalPassenger.get();
            existingPassenger.setName(passengerDetails.getName());
            existingPassenger.setPassengerNumber(passengerDetails.getPassengerNumber());
            existingPassenger.setPassengerType(passengerDetails.getPassengerType());
            existingPassenger.setBalance(passengerDetails.getBalance());
            return passengerRepository.save(existingPassenger);
        } else {
            return null;
        }
    }

    public boolean deletePassenger(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            passengerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

