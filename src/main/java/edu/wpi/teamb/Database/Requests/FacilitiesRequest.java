package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "facilitiesrequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "FacilitiesRequestID",
    foreignKey = @ForeignKey(name = "FacilitiesRequestIDKey_iter4"))
public class FacilitiesRequest extends GeneralRequest {

  @Column(name = "maintenancetype", length = 40)
  @Getter
  @Setter
  private String maintenanceType;

  @Column(name = "location", length = 60)
  @Getter
  @Setter
  private String location;

  public FacilitiesRequest() {};
}
