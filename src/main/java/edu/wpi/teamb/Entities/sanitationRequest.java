package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class sanitationRequest extends generalRequest {
  @Getter @Setter private String cleanUpLocation;
  @Getter @Setter private String typeOfCleanUp;

  private static sanitationRequest instance = null;

  public void sanitationRequest() {}

  public static sanitationRequest getInstance() {
    if (instance == null) {
      instance = new sanitationRequest();
    }
    return instance;
  }
}
