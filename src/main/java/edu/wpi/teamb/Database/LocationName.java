package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "locationname")
public class LocationName {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "longname", length = 70)
  private String longName;

  @Column(name = "shortName", length = 20)
  private String shortName;

  @Column(name = "locationType", length = 20)
  private String locationType;

  public LocationName() {}

  public LocationName(String longName, String shortName, String locationType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = locationType;
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO locationname (longname, shortname, locationtype)" + "VALUES (?,?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, longName);
    ps.setString(2, shortName);
    ps.setString(3, locationType);

    ps.executeUpdate();
  }

  public static Map<String, LocationName> getAll() throws SQLException {
    HashMap<String, LocationName> locations = new HashMap<String, LocationName>();
    String sql = "SELECT * FROM locationName;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      locations.put(
          rs.getString("longname"),
          new LocationName(
              rs.getString("longname"), rs.getString("shortname"), rs.getString("locationtype")));
    }
    return locations;
  }

  public void updateLN(String newName) throws SQLException {
    String old = longName;
    longName = newName;
    String sql =
        "UPDATE LocationName SET longName = '" + longName + "' WHERE longName = '" + old + "';";
    Bdb.processUpdate(sql);
  }

  public void update() throws SQLException {
    String sql =
        "UPDATE locationname "
            + "SET longName = ?, shortName = ?, locationType = ? "
            + "WHERE longName = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, longName);
    ps.setString(2, shortName);
    ps.setString(3, locationType);
    ps.setString(4, longName);
    ps.executeUpdate();
  }

  public void delete() throws SQLException {
    String sql = "DELETE FROM locationname WHERE longName = ?;";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, longName);
    ps.executeUpdate();
  }
}
