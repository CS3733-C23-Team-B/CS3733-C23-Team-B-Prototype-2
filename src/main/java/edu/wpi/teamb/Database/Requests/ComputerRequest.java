package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "computerrequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "computerRequestID",
    foreignKey = @ForeignKey(name = "computerRequestIDKey_iter4"))
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
