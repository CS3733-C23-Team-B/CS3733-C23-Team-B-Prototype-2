package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class ComputerRequest extends GeneralRequest {
    @Getter @Setter private String typeofRequest;
    @Getter @Setter private String repairLocation;

    private static ComputerRequest instance = null;

    public static ComputerRequest getInstance() {
        if (instance == null) {
            instance = new ComputerRequest();
        }
        return instance;
    }
}
