package javaproject.travelmanager.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class for representing activity information.
 */
@Getter
@Setter
public class ActivityDTO {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private Long destinationId;
}
