package javaproject.travelmanager.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Destination.
 * Used for transferring destination data between the client and the server.
 */
@Getter
@Setter
public class DestinationDTO {
    private String name;
    private List<Long> activitiesID;
}
