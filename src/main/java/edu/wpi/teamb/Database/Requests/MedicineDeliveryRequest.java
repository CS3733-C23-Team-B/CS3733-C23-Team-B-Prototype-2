package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medicinedeliveryrequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "MedicineDeliveryRequestID",
    foreignKey = @ForeignKey(name = "MedicineDeliveryRequestIDKey_iter4"))
public class MedicineDeliveryRequest extends GeneralRequest {
  @Column(name = "medicinetype", length = 60)
  @Getter
  @Setter
  private String medicineType;

  @Column(name = "location", length = 60)
  @Getter
  @Setter
  private String location;

  @Column(name = "dosage", length = 60)
  @Getter
  @Setter
  private String doasage;

  @Column(name = "patientID", length = 60)
  @Getter
  @Setter
  private String patientID;

  public MedicineDeliveryRequest() {}
}
