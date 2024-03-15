package javaproject.travelmanager.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityDTO {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private Long destinationId;
}
