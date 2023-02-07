package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "computerrequest")
@PrimaryKeyJoinColumn(
    name = "computerRequestID",
    foreignKey = @ForeignKey(name = "computerRequestIDKey"))
public class ComputerRequest extends GeneralRequest {
  @Column(name = "typeofrepair", length = 60)
  @Getter
  @Setter
  private String typeOfRepair;

  @Column(name = "device", length = 60)
  @Getter
  @Setter
  private String device;

  @Column(name = "repairlocation", length = 60)
  @Getter
  @Setter
  private String repairLocation;

  public void ComputerRequest() {}
}
