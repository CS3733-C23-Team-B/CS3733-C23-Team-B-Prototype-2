package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class ComputerRequest extends GeneralRequest {
  @Getter @Setter private String typeOfRepair;
  @Getter @Setter private String repairLocation;

  @Getter @Setter private String device;

  public void ComputerRequest() {}
}
