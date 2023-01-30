package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that represents the node table in the Database, allows, insertions, deletions, and
 * other interactions with the node table in the database
 */
public class Node {

  public static final String tableName = "node";

  // Primary Key
  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;

  public Node(int xcoord, int ycoord, String floor, String building) {

    this.nodeID = null;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  public Node(String nodeID, int xcoord, int ycoord, String floor, String building) {

    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  /**
   * Method to initalize the node table in the database
   * @throws SQLException
   */
  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE node",
            "(nodeID VARCHAR(14),",
            "xcoord INTEGER,",
            "ycoord INTEGER,",
            "floor VARCHAR(4),",
            "building VARCHAR(15),",
            "PRIMARY KEY (nodeID) );");
    Bdb.processUpdate(sql);
  }

  public static Map<String, Node> getAll() throws SQLException {
    HashMap<String, Node> nodes = new HashMap<String, Node>();
    String sql = "SELECT * FROM Node;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      nodes.put(
          rs.getString("nodeID"),
          new Node(
              rs.getString("nodeID"),
              rs.getInt("xcoord"),
              rs.getInt("ycoord"),
              rs.getString("floor"),
              rs.getString("building")));
    }
    return nodes;
  }

  /**
   * Method to insert an instance of a node into the database using an instance of the node class
   * in java
   * @throws SQLException
   */
  public void insert() throws SQLException {
    String sql =
        "INSERT INTO node (nodeID, xcoord, ycoord, floor, building) " + "VALUES (?,?,?,?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, nodeID);
    ps.setInt(2, xcoord);
    ps.setInt(3, ycoord);
    ps.setString(4, floor);
    ps.setString(5, building);

    /// not sure how we will deal with generating new nodeID yet but left at string for now
    /// so ignore duplicate in update() for now

    ps.executeUpdate();
  }

  /**
   * method to update an instance of a node in the database using an instance of the node class in
   * java
   * @throws SQLException
   */
  public void update() throws SQLException {
    String sql =
        "UPDATE node "
            + "SET xcoord = ?, ycoord = ?, floor = ?, "
            + "building = ? "
            + "WHERE nodeID = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(5, nodeID);
    ps.setInt(1, xcoord);
    ps.setInt(2, ycoord);
    ps.setString(3, floor);
    ps.setString(4, building);
    ps.executeUpdate();
  }

  /**
   * method to delete an instance of an edge in the database using an instance of the node class in
   * java
   * @throws SQLException
   */
  public void delete() throws SQLException {
    String sql = "DELETE FROM node WHERE nodeID = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, nodeID);
    ps.executeUpdate();
  }

  /**
   * method to get the name of the table
   * @return a String representing the name of the table ("node")
   */
  public static String getTableName() {
    return tableName.toLowerCase();
  }

  /**
   * method to get the node id of an instance of node
   * @return String representing the node ID
   */
  public String getID() {
    return nodeID;
  }

  /**
   * method to get the x coordinate of an instance of a node
   * @return an integer representing the x coordinate of the instance of the node
   */
  public int getXcoord() {
    return xcoord;
  }
  /**
   * method to get the y coordinate of an instance of a node
   * @return an integer representing the y coordinate of the instance of the node
   */
  public int getYcoord() {
    return ycoord;
  }

  /**
   * method to get the floor level of an instance of a node
   * @return a string representing the floor level 
   */
  public String getFloor() {
    return floor;
  }

  /**
   * method to get the nodeID, x coordinate, y coordinate, floor, and building  of a node
   * @return a string, an integer of the x and y coordinate, and a string of the floor and building
   */
  public String getInfo() {
    String str =
        "Node: "
            + nodeID
            + ", "
            + "X: "
            + xcoord
            + ", "
            + "Y: "
            + ycoord
            + ", "
            + "Floor: "
            + floor
            + ", "
            + "Building: "
            + building;
    return str;
  }

  /**
   * method to set the new coordinates of an instance of a node in the database
   * @param newX
   * @param newY
   * @throws SQLException
   */
  public void setCoords(int newX, int newY) throws SQLException {
    xcoord = newX;
    ycoord = newY;
    update();
  }

  /**
   * method to get all instance of the node from the database
   * @return a ArrayList<Node> representing all the nodes in the database
   * @throws SQLException
   */
  public static ArrayList<Node> getEmptyNodes() throws SQLException {
    ArrayList<Node> mtNodes = new ArrayList<Node>();
    String sql = "SELECT * FROM node WHERE nodeID not in (SELECT nodeID FROM move);";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      mtNodes.add(
          new Node(
              rs.getString("nodeID"),
              rs.getInt("xcoord"),
              rs.getInt("ycoord"),
              rs.getString("floor"),
              rs.getString("building")));
    }
    return mtNodes;
  }
}
