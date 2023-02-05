package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patienttransportationrequest")
@PrimaryKeyJoinColumn(
    name = "PatientTransportationRequestID",
    foreignKey = @ForeignKey(name = "PatientTransportationRequestIDKey"))
public class PatientTransportationRequest extends GeneralRequest implements IORM{
  @Column(name = "equipmentNeeded", length = 20)
  @Getter
  @Setter
  private String equipmentNeeded;

  @Column(name = "patientLocation", length = 20)
  @Getter
  @Setter
  private String patientLocation;

  @Column(name = "patientDestination", length = 20)
  @Getter
  @Setter
  private String patientDestination;

  @Column(name = "patientID", length = 20)
  @Getter
  @Setter
  private String patientID;

  public PatientTransportationRequest() {}
}
