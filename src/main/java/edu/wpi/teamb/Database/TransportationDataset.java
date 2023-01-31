package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.RequestStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class TransportationDataset {
  private static final String tableName = "transportation";
  @Getter
  String firstname,
      lastname,
      email,
      equipment,
      urgency,
      location,
      destination,
      notes,
      patientid,
      employeeId;
  @Getter RequestStatus status;

  public TransportationDataset(
      String firstname,
      String lastname,
      String employeeId,
      String email,
      String equipment,
      String urgency,
      String location,
      String destination,
      String notes,
      String patientid,
      RequestStatus status) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.equipment = equipment;
    this.urgency = urgency;
    this.location = location;
    this.destination = destination;
    this.notes = notes;
    this.employeeId = employeeId;
    this.patientid = patientid;
    this.status = status;
  }

  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE transportation",
            "(firstname VARCHAR,",
            "lastname VARCHAR,",
            "employeeid VARCHAR,",
            "email VARCHAR,",
            "equipment VARCHAR,",
            "urgency VARCHAR,",
            "currentlocation VARCHAR,",
            "destination VARCHAR,",
            "notes VARCHAR(500),",
            "patientid VARCHAR",
            "status VARCHAR",
            ");");
    Bdb.processUpdate(sql);
  }

  public void insert() throws SQLException {
    String sql =
        "INSERT INTO transportation (firstname, lastname, employeeid, email, equipment, urgency, currentlocation, destination, notes, patientid, status)"
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, firstname);
    ps.setString(2, lastname);
    ps.setString(3, employeeId);
    ps.setString(4, email);
    ps.setString(5, equipment);
    ps.setString(6, urgency);
    ps.setString(7, location);
    ps.setString(8, destination);
    ps.setString(9, notes);
    ps.setString(10, patientid);
    ps.setString(11, status.toString());
    ps.executeUpdate();
  }

  public static List<TransportationDataset> getAll() throws SQLException {
    List<TransportationDataset> requests = new ArrayList<TransportationDataset>();
    String sql = "SELECT * FROM transportation;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      RequestStatus newStatus;
      switch (rs.getString("status")) {
        case ("blank"):
          newStatus = RequestStatus.BLANK;
          break;
        case ("processing"):
          newStatus = RequestStatus.PROCESSING;
          break;
        case ("done"):
          newStatus = RequestStatus.DONE;
          break;
        default:
          newStatus = RequestStatus.BLANK;
          break;
      }
      requests.add(
          new TransportationDataset(
              rs.getString("firstname"),
              rs.getString("lastname"),
              rs.getString("employeeid"),
              rs.getString("email"),
              rs.getString("equipment"),
              rs.getString("urgency"),
              rs.getString("currentlocation"),
              rs.getString("destination"),
              rs.getString("notes"),
              rs.getString("patientid"),
              newStatus));
    }
    return requests;
  }

  public static String getTableName() {
    return tableName.toLowerCase();
  }
}
