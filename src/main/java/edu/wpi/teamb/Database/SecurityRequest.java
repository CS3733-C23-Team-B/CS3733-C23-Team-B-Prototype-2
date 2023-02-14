package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "securityrequest")
@PrimaryKeyJoinColumn(
    name = "securityRequestID",
    foreignKey = @ForeignKey(name = "SecurityRequestIDKey"))
public class SecurityRequest extends GeneralRequest {
  @Column(name = "issuetype", length = 40)
  @Getter
  @Setter
  private String issueType;

  @Column(name = "equipmentneeded", length = 80)
  @Getter
  @Setter
  private String equipmentNeeded;

  @Column(name = "numberrequired")
  @Getter
  @Setter
  private int numberRequired;

  public SecurityRequest() {};
}
