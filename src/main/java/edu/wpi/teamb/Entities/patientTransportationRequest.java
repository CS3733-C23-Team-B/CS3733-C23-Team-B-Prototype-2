package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class patientTransportationRequest extends generalRequest {
    @Getter @Setter
    private String equipmentNeeded;
    @Getter @Setter
    private String urgency;
    @Getter @Setter
    private String patientLocation;
    @Getter @Setter
    private String patientDestination;
    @Getter @Setter
    private String patientID;
    private static patientTransportationRequest instance = null;


    public static patientTransportationRequest getInstance() {
        if (instance == null) {
            instance = new patientTransportationRequest();
        }
        return instance;
    }

}
