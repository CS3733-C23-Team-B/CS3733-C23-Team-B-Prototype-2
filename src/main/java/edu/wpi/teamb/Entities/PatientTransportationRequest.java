package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class PatientTransportationRequest extends GeneralRequest {
  @Getter @Setter private String equipmentNeeded;
  @Getter @Setter private String patientCurrentLocation;
  @Getter @Setter private String patientDestinationLocation;
  @Getter @Setter private String patientIDField;
  private static PatientTransportationRequest instance = null;

  public static PatientTransportationRequest getInstance() {
    if (instance == null) {
      instance = new PatientTransportationRequest();
    }
    return instance;
  }
}
