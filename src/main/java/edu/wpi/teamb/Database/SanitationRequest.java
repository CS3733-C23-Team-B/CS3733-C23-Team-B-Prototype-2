package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sanitationrequest")
@PrimaryKeyJoinColumn(
    name = "sanitationRequestID",
    foreignKey = @ForeignKey(name = "SanitationRequestIDKey"))
public class SanitationRequest extends GeneralRequest {
  @Column(name = "cleanuplocation", length = 80)
  @Getter
  @Setter
  private String cleanUpLocation;

  @Column(name = "typeofcleanup", length = 80)
  @Getter
  @Setter
  private String typeOfCleanUp;

  public SanitationRequest() {}
}

