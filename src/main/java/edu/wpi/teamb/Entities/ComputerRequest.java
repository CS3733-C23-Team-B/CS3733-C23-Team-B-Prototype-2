package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class ComputerRequest extends GeneralRequest {
  @Getter @Setter private String typeOfRepair;
  @Getter @Setter private String repairLocation;

  public void ComputerRequest() {}
}
