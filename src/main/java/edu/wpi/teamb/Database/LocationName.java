package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "locationname")
public class LocationName implements IORM {
  @Id
  @Column(name = "longname", length = 70)
  private String longName;

  @Column(name = "shortname", length = 20)
  private String shortName;

  @Column(name = "locationtype", length = 20)
  private String locationType;

  public LocationName() {}

  public LocationName(String longName, String shortName, String locationType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = locationType;
  }

  public void insert() throws SQLException {}

  public static Map<String, LocationName> getAll() throws SQLException {
    HashMap<String, LocationName> locations = new HashMap<String, LocationName>();

    return locations;
  }

  public void updateLN(String newName) throws SQLException {}

  public void update() throws SQLException {}

  public void delete() throws SQLException {}

  @Override
  public String getSearchStr() {
    return "FROM locationname WHERE longname = '" + longName + "'";
  }
}
