package javaproject.travelmanager.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import javaproject.travelmanager.Entity.Destination;
import javaproject.travelmanager.Entity.Passenger;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TravelPackageDTO {
    private String name;
    private int passengerCapacity;
    private List<Long> destinationsId;
    private List<Long> passengersId;
}
