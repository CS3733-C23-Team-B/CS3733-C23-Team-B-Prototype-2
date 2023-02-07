package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patienttransportationrequest")
@PrimaryKeyJoinColumn(
    name = "PatientTransportationRequestID",
    foreignKey = @ForeignKey(name = "PatientTransportationRequestIDKey"))
public class PatientTransportationRequest extends GeneralRequest implements IORM {
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

  public String getPatientDestination() {
    return patientDestinationLocation;
  }

  public void setPatientDestination(String patientDestination) {
    this.patientDestinationLocation = patientDestination;
  }

  @Override
  public String getSearchStr() {
    return "FROM PatientTransportationRequest WHERE id = " + getId();
  }

  public PatientTransportationRequest() {}
}
