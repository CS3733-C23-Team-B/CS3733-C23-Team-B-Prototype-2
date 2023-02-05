package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sanitationrequest")
@PrimaryKeyJoinColumn(
    name = "sanitationRequestID",
    foreignKey = @ForeignKey(name = "SanitationRequestIDKey"))
public class SanitationRequest extends GeneralRequest {
  @Column(name = "cleanuplocation", length = 20)
  @Getter
  @Setter
  private String cleanUpLocation;

  @Column(name = "typeofcleanup", length = 20)
  @Getter
  @Setter
  private String typeOfCleanUp;

  private static SanitationRequest instance = null;

  public void sanitationRequest() {}

  public static SanitationRequest getInstance() {
    if (instance == null) {
      instance = new SanitationRequest();
    }
    return instance;
  }
}
