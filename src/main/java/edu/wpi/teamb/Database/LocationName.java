package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.*;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "locationname")
public class LocationName implements IORM {
  @Id
  @Column(name = "longname", length = 70)
  @Getter
  @Setter
  private String longName;

  @Column(name = "shortname", length = 35)
  @Getter
  @Setter
  private String shortName;

  @Column(name = "locationtype", length = 20)
  @Getter
  @Setter
  private String locationType;

  public LocationName() {}

  public LocationName(String longName, String shortName, String locationType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = locationType;
  }

  @Override
  public String getSearchStr() {
    return "FROM LocationName WHERE longName = '" + longName + "'";
  }
}
