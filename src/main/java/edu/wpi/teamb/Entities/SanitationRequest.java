package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sanitationrequest")
@PrimaryKeyJoinColumn(
    name = "sanitationRequestID",
    foreignKey = @ForeignKey(name = "SanitationRequestIDKey"))
public class SanitationRequest extends GeneralRequest implements IORM {
  @Column(name = "cleanuplocation", length = 80)
  @Getter
  @Setter
  private String cleanUpLocation;

  @Column(name = "typeofcleanup", length = 80)
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

  @Override
  public String getSearchStr() {
    return "FROM SanitationRequest WHERE id = " + getId();
  }
}
