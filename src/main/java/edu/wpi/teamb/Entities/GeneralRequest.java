package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class GeneralRequest {
  @Getter @Setter private String firstName;
  @Getter @Setter private String lastName;
  @Getter @Setter private String email;
  @Getter @Setter private String employeeID;
  @Getter @Setter private String urgency;
  @Getter @Setter private String assignedEmployee;
  @Getter @Setter private String notes;
  @Getter @Setter private RequestStatus status;

  public void generalRequest() {}
}
