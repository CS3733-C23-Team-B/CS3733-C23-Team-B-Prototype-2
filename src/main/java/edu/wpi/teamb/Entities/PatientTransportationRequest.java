package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class PatientTransportationRequest extends GeneralRequest {
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
    private static PatientTransportationRequest instance = null;


    public static PatientTransportationRequest getInstance() {
        if (instance == null) {
            instance = new PatientTransportationRequest();
        }
        return instance;
    }

}
