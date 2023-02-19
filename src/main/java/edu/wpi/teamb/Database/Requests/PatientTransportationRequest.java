package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patienttransportationrequest", schema = "iter3Testing")
@PrimaryKeyJoinColumn(
    name = "PatientTransportationRequestID",
    foreignKey = @ForeignKey(name = "PatientTransportationRequestIDKey_iter3Testing"))
public class PatientTransportationRequest extends GeneralRequest {
  @Column(name = "equipmentNeeded", length = 60)
  @Getter
  @Setter
  private String equipmentNeeded;

  @Column(name = "patientLocation", length = 60)
  @Getter
  @Setter
  private String patientCurrentLocation;

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
