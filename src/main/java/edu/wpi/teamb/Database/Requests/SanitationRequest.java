package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sanitationrequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "sanitationRequestID",
    foreignKey = @ForeignKey(name = "SanitationRequestIDKey_iter4"))
public class SanitationRequest extends GeneralRequest {

  @Column(name = "typeofcleanup", length = 80)
  @Getter
  @Setter
  private String typeOfCleanUp;

  public SanitationRequest() {}
}
