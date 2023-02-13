package edu.wpi.teamb.Database;

import edu.wpi.teamb.Database.DAO.MapDAO;
import java.io.*;
import java.util.List;

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

  public static void main(String[] args) throws IOException {
    writeEdges();
  }
}
