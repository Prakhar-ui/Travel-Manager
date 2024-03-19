package javaproject.travelmanager.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.PassengerType;
import javaproject.travelmanager.Entity.TravelPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) class for representing passenger information.
 */
@Getter
@Setter
public class PassengerDTO {
    private String name;
    private String passengerNumber;
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
    private double balance;
    private List<Long> travelPackagesIds;
    private List<Long> activitiesIds;
}
