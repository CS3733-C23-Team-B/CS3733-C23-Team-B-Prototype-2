package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that represents the node table in the Database, allows, insertions, deletions, and other
 * interactions with the node table in the database
 */
public class Node {

  public static final String tableName = "node";

  // Primary Key
  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;

  /**
   * Constructor of a node with a "null" nodeID
   *
   * @param xcoord
   * @param ycoord
   * @param floor
   * @param building
   */
  public Node(int xcoord, int ycoord, String floor, String building) {

    this.nodeID = null;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  /**
   * Constructor for node
   *
   * @param nodeID
   * @param xcoord
   * @param ycoord
   * @param floor
   * @param building
   */
  public Node(String nodeID, int xcoord, int ycoord, String floor, String building) {

    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  /**
   * Method to initialize the node table in the database
   *
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

  /**
   * Gets all the nodes in the database
   *
   * @return nodes
   * @throws SQLException
   */
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
   * Inserts a new node into the database.
   *
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
   * Updates all the fields of a node already in the database.
   *
   * @throws SQLException
   */
  public void update() throws SQLException {
    String sql =
        "UPDATE node "
            + "SET nodeid = ?, xcoord = ?, ycoord = ?, floor = ?, "
            + "building = ? "
            + "WHERE nodeID = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    String nid = buildID();
    ps.setString(1, nid);
    ps.setInt(2, xcoord);
    ps.setInt(3, ycoord);
    ps.setString(4, floor);
    ps.setString(5, building);
    ps.setString(6, nodeID);
    ps.executeUpdate();
    nodeID = nid;
  }

  /**
   * Deletes a node that is already in the database based on the nodeID.
   *
   * @throws SQLException
   */
  public void delete() throws SQLException {
    String sql = "DELETE FROM node WHERE nodeID = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, nodeID);
    ps.executeUpdate();
  }

  /**
   * Gets the name of a table.
   *
   * @return tablename
   */
  public static String getTableName() {
    return tableName.toLowerCase();
  }

  /**
   * Gets the nodeID of a node.
   *
   * @return nodeID
   */
  public String getID() {
    return nodeID;
  }

  /**
   * Gets the x-coordinate of a node.
   *
   * @return xcoord
   */
  public int getXCoord() {
    return xcoord;
  }
  /**
   * Gets the y-coordinate of a node.
   *
   * @return ycoord
   */
  public int getYCoord() {
    return ycoord;
  }

  /**
   * Gets the floor of a node.
   *
   * @return floor
   */
  public String getFloor() {
    return floor;
  }

  /**
   * Gets all the information of a node and returns it as a string
   *
   * @return str
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
   * method to set the new coordinates of an instance of a node in the database Sets the coordinates
   * of a node.
   *
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
   * Returns a list of the empty nodes.
   *
   * @return mtNodes
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

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setXcoord(int xcoord) {
    this.xcoord = xcoord;
  }

  public void setYcoord(int ycoord) {
    this.ycoord = ycoord;
  }
  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String buildID() {
    String x = Integer.toString(xcoord);
    String y = Integer.toString(ycoord);
    while (x.length() < 4) {
      x = "0" + x;
    }
    while (y.length() < 4) {
      y = "0" + y;
    }
    String id = floor + "X" + x + "Y" + y;
    return id;
  }
}
