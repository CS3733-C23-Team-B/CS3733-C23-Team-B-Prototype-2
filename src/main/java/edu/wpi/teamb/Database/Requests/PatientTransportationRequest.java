package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patienttransportationrequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "PatientTransportationRequestID",
    foreignKey = @ForeignKey(name = "PatientTransportationRequestIDKey_iter4"))
public class PatientTransportationRequest extends GeneralRequest {
  @Column(name = "equipmentNeeded", length = 60)
  @Getter
  @Setter
  private String equipmentNeeded;

  @Column(name = "patientDestination", length = 60)
  @Getter
  @Setter
  private String patientDestinationLocation;

  @Column(name = "patientID", length = 60)
  @Getter
  @Setter
  private String patientID;

  public PatientTransportationRequest() {}
}
