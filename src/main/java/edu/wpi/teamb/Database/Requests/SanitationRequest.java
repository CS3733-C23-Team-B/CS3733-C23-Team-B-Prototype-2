package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sanitationrequest", schema = "iter3Testing")
@PrimaryKeyJoinColumn(
    name = "sanitationRequestID",
    foreignKey = @ForeignKey(name = "SanitationRequestIDKey_iter3Testing"))
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
