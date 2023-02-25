package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medicalequipmentdeliveryrequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "MedicalEquipmentDeliveryRequestID",
    foreignKey = @ForeignKey(name = "MedicalEquipmentDeliveryRequestIDKey_iter4"))
public class MedicalEquipmentDeliveryRequest extends GeneralRequest {

  @Column(name = "equipmenttype", length = 40)
  @Getter
  @Setter
  private String equipmentType;

  public MedicalEquipmentDeliveryRequest() {};
}
