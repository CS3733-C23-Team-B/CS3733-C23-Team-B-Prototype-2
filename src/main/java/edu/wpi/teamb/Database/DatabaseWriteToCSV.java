package edu.wpi.teamb.Database;

import edu.wpi.teamb.Database.DAO.MapDAO;
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

    for (int i = 0; i < data.length; i++) {
      String s = data[i];

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

  public static void main(String[] args) throws IOException, ParseException {
    writeEdges();
    writeNodes();
    writeMoves();
    writeLocationNames();
  }
}
