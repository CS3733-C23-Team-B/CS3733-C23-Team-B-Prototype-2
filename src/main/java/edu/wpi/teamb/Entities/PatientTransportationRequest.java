package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patienttransportationrequest")
@PrimaryKeyJoinColumn(
    name = "PatientTransportationRequestID",
    foreignKey = @ForeignKey(name = "PatientTransportationRequestIDKey"))
public class PatientTransportationRequest extends GeneralRequest implements IORM {
  @Column(name = "equipmentNeeded", length = 20)
  @Getter
  @Setter
  private String equipment;

  @Column(name = "patientLocation", length = 20)
  @Getter
  @Setter
  private String location;

  @Column(name = "patientDestination", length = 20)
  @Getter
  @Setter
  private String destination;

  @Column(name = "patientID", length = 20)
  @Getter
  @Setter
  private String patientID;

  public String getPatientDestination() {
    return destination;
  }

  public void setPatientDestination(String patientDestination) {
    this.destination = patientDestination;
  }

  @Override
  public String getSearchStr() {
    return "FROM PatientTransportationRequest WHERE id = " + getId();
  }

  public PatientTransportationRequest() {}
}
