package javaproject.travelmanager.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import javaproject.travelmanager.Entity.Activity;
import javaproject.travelmanager.Entity.PassengerType;
import javaproject.travelmanager.Entity.TravelPackage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) class for representing passenger information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {
    private String name;
    private String passengerNumber;
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
    private double balance;
    private Long travelPackageId;
    private List<Long> activitiesIds;
}
