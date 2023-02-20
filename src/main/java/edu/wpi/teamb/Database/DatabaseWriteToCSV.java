package edu.wpi.teamb.Database;

import edu.wpi.teamb.Database.DAO.LoginDAO;
import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Database.DAO.RequestDAO;
import edu.wpi.teamb.Database.Requests.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class DatabaseWriteToCSV {

  public static void writeCsv(String fileName, String[] data, Boolean first) throws IOException {
    File csvFile = new File("./src/main/resources/edu/wpi/teamb/Iter2Data/" + fileName + ".csv");
    FileWriter fileWriter;
    if (first) {
      fileWriter = new FileWriter(csvFile);
      first = false;
    } else {
      fileWriter = new FileWriter(csvFile, true);
    }
    fileWriter.write(convertToCSV(data));
    fileWriter.close();
  }

  private static String convertToCSV(String[] data) {
    String csv = "";
    String s;
    for (int i = 0; i < data.length; i++) {
      if (data[i] != null) {
        s = data[i];
      } else {
        s = "";
      }

      // Remove special characters
      s = s.replaceAll("\\R", " ");
      if (s.contains(",") || s.contains("\"") || s.contains("'")) {
        s.replace("\"", "\"\"");
        s = "\"" + s + "\"";
      }

      csv = csv + s;
      if (i != data.length - 1) csv = csv + ",";
      else csv = csv + "\n";
    }
    return csv;
  }

  public static void writeEdges() throws IOException {
    String fileName = "edgeWriteTo";
    MapDAO.refreshEdges();
    List<Edge> es = MapDAO.getAllEdges();
    boolean first = true;
    for (Edge e : es) {
      String[] data = new String[2];
      data[0] = e.getNode1().getNodeID();
      data[1] = e.getNode2().getNodeID();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeNodes() throws IOException {
    String fileName = "nodeWriteTo";
    MapDAO.refreshNodes();
    Map<String, Node> ns = MapDAO.getAllNodes();
    boolean first = true;
    for (Map.Entry<String, Node> en : ns.entrySet()) {
      Node n = en.getValue();
      String[] data = new String[5];
      data[0] = n.getNodeID();
      data[1] = Integer.toString(n.getXCoord());
      data[2] = Integer.toString(n.getYCoord());
      data[3] = n.getFloor();
      data[4] = n.getBuilding();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeMoves() throws IOException, ParseException {
    String fileName = "moveWriteTo";
    Map<String, Move> ms =
        MapDAO.getLNMoves(new SimpleDateFormat("yyyy-mm-dd").parse("3000-01-01"));
    boolean first = true;
    for (Map.Entry<String, Move> en : ms.entrySet()) {
      Move m = en.getValue();
      String[] data = new String[3];
      data[0] = m.getNode().getNodeID();
      data[1] = m.getLocationName().getLongName();
      data[2] = m.getMoveDate().toString();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeLocationNames() throws IOException {
    String fileName = "locationNameWriteTo";
    MapDAO.refreshLocationNames();
    Map<String, LocationName> lns = MapDAO.getAllLocationNames();
    boolean first = true;
    for (Map.Entry<String, LocationName> en : lns.entrySet()) {
      LocationName ln = en.getValue();
      String[] data = new String[3];
      data[0] = ln.getLongName();
      data[1] = ln.getShortName();
      data[2] = ln.getLocationType();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeLogins() throws IOException {
    String fileName = "logins";
    LoginDAO.refreshLogins();
    Map<String, Login> ls = LoginDAO.getAllLogins();
    boolean first = true;
    for (Map.Entry<String, Login> en : ls.entrySet()) {
      Login l = en.getValue();
      String[] data = new String[7];
      data[0] = Integer.toString(l.getId());
      data[1] = Boolean.toString(l.getAdmin());
      data[2] = l.getEmail();
      data[3] = l.getFirstname();
      data[4] = l.getLastname();
      data[5] = l.getPassword();
      data[6] = l.getUsername();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writePTRequests() throws IOException {
    String fileName = "ptRequests";
    RequestDAO.refreshRequests();
    List<PatientTransportationRequest> rs = RequestDAO.getAllPTRequests();
    boolean first = true;
    for (PatientTransportationRequest r : rs) {
      String[] data = new String[13];
      data[0] = Integer.toString(r.getId());
      data[1] = r.getFirstname();
      data[2] = r.getLastname();
      data[3] = r.getEmail();
      data[4] = r.getEmployeeID();
      data[5] = r.getUrgency().toString();
      data[6] = r.getAssignedEmployee();
      data[7] = r.getNotes();
      data[8] = r.getStatus().toString();
      data[9] = r.getEquipmentNeeded();
      data[10] = r.getPatientCurrentLocation();
      data[11] = r.getPatientDestinationLocation();
      data[12] = r.getPatientID();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeSanRequests() throws IOException {
    String fileName = "sanRequests";
    RequestDAO.refreshRequests();
    List<SanitationRequest> rs = RequestDAO.getAllSanRequests();
    boolean first = true;
    for (SanitationRequest r : rs) {
      String[] data = new String[11];
      data[0] = Integer.toString(r.getId());
      data[1] = r.getFirstname();
      data[2] = r.getLastname();
      data[3] = r.getEmail();
      data[4] = r.getEmployeeID();
      data[5] = r.getUrgency().toString();
      data[6] = r.getAssignedEmployee();
      data[7] = r.getNotes();
      data[8] = r.getStatus().toString();
      data[9] = r.getCleanUpLocation();
      data[10] = r.getTypeOfCleanUp();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeCRequests() throws IOException {
    String fileName = "cRequests";
    RequestDAO.refreshRequests();
    List<ComputerRequest> rs = RequestDAO.getAllCRequests();
    boolean first = true;
    for (ComputerRequest r : rs) {
      String[] data = new String[12];
      data[0] = Integer.toString(r.getId());
      data[1] = r.getFirstname();
      data[2] = r.getLastname();
      data[3] = r.getEmail();
      data[4] = r.getEmployeeID();
      data[5] = r.getUrgency().toString();
      data[6] = r.getAssignedEmployee();
      data[7] = r.getNotes();
      data[8] = r.getStatus().toString();
      data[9] = r.getTypeOfRepair();
      data[10] = r.getDevice();
      data[11] = r.getRepairLocation();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeSecRequests() throws IOException {
    String fileName = "secRequests";
    RequestDAO.refreshRequests();
    List<SecurityRequest> rs = RequestDAO.getAllSecRequests();
    boolean first = true;
    for (SecurityRequest r : rs) {
      String[] data = new String[12];
      data[0] = Integer.toString(r.getId());
      data[1] = r.getFirstname();
      data[2] = r.getLastname();
      data[3] = r.getEmail();
      data[4] = r.getEmployeeID();
      data[5] = r.getUrgency().toString();
      data[6] = r.getAssignedEmployee();
      data[7] = r.getNotes();
      data[8] = r.getStatus().toString();
      data[9] = r.getIssueType();
      data[10] = r.getEquipmentNeeded();
      data[11] = Integer.toString(r.getNumberRequired());
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void writeAVRequests() throws IOException {
    String fileName = "avRequests";
    RequestDAO.refreshRequests();
    List<AudioVideoRequest> rs = RequestDAO.getAllAVRequests();
    boolean first = true;
    for (AudioVideoRequest r : rs) {
      String[] data = new String[10];
      data[0] = Integer.toString(r.getId());
      data[1] = r.getFirstname();
      data[2] = r.getLastname();
      data[3] = r.getEmail();
      data[4] = r.getEmployeeID();
      data[5] = r.getUrgency().toString();
      data[6] = r.getAssignedEmployee();
      data[7] = r.getNotes();
      data[8] = r.getStatus().toString();
      data[9] = r.getAVType();
      writeCsv(fileName, data, first);
      first = false;
    }
  }

  public static void runWrites() throws IOException, ParseException {
    writeEdges();
    writeNodes();
    writeMoves();
    writeLocationNames();
    writeLogins();
    writePTRequests();
    writeSanRequests();
    writeCRequests();
    writeSecRequests();
    writeAVRequests();
  }
}
