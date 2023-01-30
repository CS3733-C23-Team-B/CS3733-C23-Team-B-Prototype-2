package edu.wpi.teamb.Entities;

import lombok.Getter;
import lombok.Setter;

public class SanitationRequest extends GeneralRequest {
  @Getter @Setter private String cleanUpLocation;
  @Getter @Setter private String typeOfCleanUp;

  private static SanitationRequest instance = null;

  public void sanitationRequest() {}

  public static SanitationRequest getInstance() {
    if (instance == null) {
      instance = new SanitationRequest();
    }
    return instance;
  }
}
