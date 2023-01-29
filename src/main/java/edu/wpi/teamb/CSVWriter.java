package edu.wpi.teamb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
  /**
   * Writes the given data to a CSV with the provided name, escaping commas, quotation marks, and
   * newlines.
   *
   * @param fileName the name of the CSV
   * @param data an array of Strings, each being one "cell"
   * @throws IOException
   */
  public static void writeCsv(String fileName, String[] data) throws IOException {
    File csvFile = new File("./src/main/resources/dataSources/" + fileName + ".csv");
    FileWriter fileWriter = new FileWriter(csvFile, true);
    fileWriter.write(convertToCSV(data));
    fileWriter.close();
  }

  /**
   * Converts a list of Strings to one String in CSV format
   *
   * @param data the list of String values
   * @return a String of comma-separated values
   */
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
}
