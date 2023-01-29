package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; // import java.util.stream.Stream;

public class Edge {

  public static final String tableName = "edge";

  //Primary Key and Foreign Key
  private String node1;

  //Primary Key and Foreign Key
  private String node2;

  public Edge(String startNode, String endNode) {

    this.node1 = startNode;
    this.node2 = endNode;
  }


  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE edge",
            "(edgeID CHAR(10),",
            "startNode CHAR(10),",
            "endNode CHAR(10),",
            "PRIMARY KEY(edgeID),",
            "FOREIGN KEY(startNode) REFERENCES Node(nodeID),",
            "FOREIGN KEY(endNode) REFERENCES Node(nodeID) );");
    Bdb.processUpdate(sql);
  }

  public static List<Edge> getAll() throws SQLException {
    ArrayList<Edge> Edges = new ArrayList<>();
    String sql = "SELECT * FROM edge;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      Edges.add(
          new Edge(rs.getString("startNode"), rs.getString("endNode")));
    }
    return Edges;
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO edge (edgeID, startNode, endNode) VALUES (?,?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(2, node1);
    ps.setString(3, node2);

    /// not sure how we will deal with generating new edgeID yet but left at string for now
    /// so ignore duplicate in update() for now

    ps.executeUpdate();
  }

  public void update() throws SQLException {

    String sql = "UPDATE edge SET startNode = ?, endNode = ? WHERE edgeID = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, node1);
    ps.setString(2, node2);
    ps.executeUpdate();
  }

  public void delete() throws SQLException {
    String sql = "DELETE FROM edge WHERE edgeID = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, edgeID);
    ps.executeUpdate();
  }

  public static String getTableName() {
    return tableName.toLowerCase();
  }


  public String getStartNode() {
    return node1;
  }

  public String getEndNode() {
    return node2;
  }

  public String getInfo() {

    String str =
        "Start Node: " + node1 + ", " + "End Node: " + node2;
    return str;
  }
}
