package javaproject.travelmanager.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DestinationDTO {
    private String name;
    private List<Long> activitiesID;
}
