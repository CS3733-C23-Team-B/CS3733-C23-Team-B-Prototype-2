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

  public Edge(String node1, String node2) {

    this.node1 = node1;
    this.node2 = node2;
  }


  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE edge",
            "(node1 CHAR(10),",
            "node2 CHAR(10),",
            "PRIMARY KEY(node1, node2),",
            "FOREIGN KEY(node1) REFERENCES Node(nodeID),",
            "FOREIGN KEY(node2) REFERENCES Node(nodeID) );");
    Bdb.processUpdate(sql);
  }

  public static List<Edge> getAll() throws SQLException {
    ArrayList<Edge> Edges = new ArrayList<>();
    String sql = "SELECT * FROM edge;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      Edges.add(
          new Edge(rs.getString("node1"), rs.getString("node2")));
    }
    return Edges;
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO edge (node1, node2) VALUES (?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, node1);
    ps.setString(2, node2);

    /// not sure how we will deal with generating new edgeID yet but left at string for now
    /// so ignore duplicate in update() for now

    ps.executeUpdate();
  }

  public void update(String newNode1, String newNode2) throws SQLException {

    String sql = "UPDATE edge SET node1 = ?, node2 = ? WHERE node1 = ?, node2 = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, newNode1);
    ps.setString(2, newNode2);
    ps.setString(3, node1);
    ps.setString(4, node2);
    ps.executeUpdate();
  }

  public void delete() throws SQLException {
    String sql = "DELETE FROM edge WHERE node1 = ?, node2 = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, node1);
    ps.setString(2, node2);
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
        "Node 1: " + node1 + ", " + "Node 2: " + node2;
    return str;
  }
}
