package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patienttransportationrequest", schema = "iter3")
@PrimaryKeyJoinColumn(
    name = "PatientTransportationRequestID",
    foreignKey = @ForeignKey(name = "PatientTransportationRequestIDKey_iter3"))
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
