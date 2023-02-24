package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "computerrequest", schema = "iter3")
@PrimaryKeyJoinColumn(
    name = "computerRequestID",
    foreignKey = @ForeignKey(name = "computerRequestIDKey_iter3"))
public class ComputerRequest extends GeneralRequest {
  @Column(name = "typeofrepair", length = 60)
  @Getter
  @Setter
  private String typeOfRepair;

  @Column(name = "device", length = 60)
  @Getter
  @Setter
  private String device;

  public void ComputerRequest() {}
}
