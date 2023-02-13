package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sanitationrequest")
@PrimaryKeyJoinColumn(
    name = "sanitationRequestID",
    foreignKey = @ForeignKey(name = "SanitationRequestIDKey"))
public class SanitationRequest extends GeneralRequest implements IORM {
  @Column(name = "cleanuplocation", length = 80)
  @Getter
  @Setter
  private String cleanUpLocation;

  @Column(name = "typeofcleanup", length = 80)
  @Getter
  @Setter
  private String typeOfCleanUp;

  @Override
  public String getSearchStr() {
    return "FROM SanitationRequest WHERE id = " + getId();
  }

  public SanitationRequest() {}
}

