package edu.wpi.teamb.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class apiConnection {

  public static String url =
      "https://3pfgc7ju3e.execute-api.us-east-1.amazonaws.com/newStage/newFunction";

  public static void main(String[] args) throws IOException {
    System.out.println(sendGET());
    String[] l = getNewsList(sendGET());
    for (String s : l) {
      System.out.println(s);
    }
  }

  public static String sendGET() throws IOException {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");
    int responseCode = con.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) { // success
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      // print result
      return response.toString();
    } else {
      return "GET request did not work.";
    }
  }

  public static String[] getNewsList(String json) {
    // Find the index of the start of the news list
    int startIdx = json.indexOf('[');
    // Find the index of the end of the news list
    int endIdx = json.lastIndexOf(']');
    // Extract the news list substring
    String newsListStr = json.substring(startIdx, endIdx + 1);

    // Split the news list substring into an array of news strings
    String[] newsArray = newsListStr.substring(1, newsListStr.length() - 1).split(",");

    // Trim any whitespace and quotation marks from the news strings
    for (int i = 0; i < newsArray.length; i++) {
      newsArray[i] = newsArray[i].trim().replaceAll("\"", "");
    }
    return newsArray;
  }
}
