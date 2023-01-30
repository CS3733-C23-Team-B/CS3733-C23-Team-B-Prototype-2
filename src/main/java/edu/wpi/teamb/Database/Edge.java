package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; // import java.util.stream.Stream;

/**
 * class that represents the edge table in the Database, allows updates, insertions, deletions, and
 * other interactions with the edge table in the database
 */
public class Edge {

  public static final String tableName = "edge";

  // Primary Key and Foreign Key
  private String node1;

  // Primary Key and Foreign Key
  private String node2;

  public Edge(String node1, String node2) {

    this.node1 = node1;
    this.node2 = node2;
  }

  /**
   * Method to initialize the edge table in the database
   *
   * @throws SQLException
   */
  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE edge",
            "(node1 CHAR(10),",
            "node2 CHAR(10),",
            "PRIMARY KEY(node1, node2),",
            "FOREIGN KEY(node1) REFERENCES Node(nodeID) ON UPDATE CASCADE,",
            "FOREIGN KEY(node2) REFERENCES Node(nodeID) ON UPDATE CASCADE);");
    Bdb.processUpdate(sql);
  }

  /**
   * Method to get all instances of an edge from the database
   *
   * @return a List<Edge> representing all the edges in the database
   * @throws SQLException
   */
  public static List<Edge> getAll() throws SQLException {
    ArrayList<Edge> Edges = new ArrayList<>();
    String sql = "SELECT * FROM edge;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      Edges.add(new Edge(rs.getString("node1"), rs.getString("node2")));
    }
    return Edges;
  }

  /**
   * method to insert an instance of an edge into the database using an instance of the edge class
   * in java
   *
   * @throws SQLException
   */
  public void insert() throws SQLException {
    String sql = "INSERT INTO edge (node1, node2) VALUES (?,?);";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, node1);
    ps.setString(2, node2);
    ps.executeUpdate();
  }

  /**
   * method to update an instance of an edge in the database using an instance of the edge class in
   * java
   *
   * @param newNode1
   * @param newNode2
   * @throws SQLException
   */
  public void update(String newNode1, String newNode2) throws SQLException {

    String sql = "UPDATE edge SET node1 = ?, node2 = ? WHERE node1 = ?, node2 = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, newNode1);
    ps.setString(2, newNode2);
    ps.setString(3, node1);
    ps.setString(4, node2);
    ps.executeUpdate();
  }

  /**
   * method to delete an instance of an edge in the database using an instance of the edge class in
   * java
   *
   * @throws SQLException
   */
  public void delete() throws SQLException {
    String sql = "DELETE FROM edge WHERE node1 = ?, node2 = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, node1);
    ps.setString(2, node2);
    ps.executeUpdate();
  }

  /**
   * method to get the name of the table
   *
   * @return a String representing the name of the table ("edge")
   */
  public static String getTableName() {
    return tableName.toLowerCase();
  }

  /**
   * method to get the start node of an instance of edge
   *
   * @return String representing the nodeID of the start node
   */
  public String getNode1() {
    return node1;
  }

  /**
   * method to get the end node of an instance of edge
   *
   * @return String representing the nodeID of the end node
   */
  public String getNode2() {
    return node2;
  }

  /**
   * method to get the start and end node IDs of an edge
   *
   * @return a String indicating the start node and end node IDs
   */
  public String getInfo() {

    String str = "Node 1: " + node1 + ", " + "Node 2: " + node2;
    return str;
  }
}
