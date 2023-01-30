package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

  public void delete() throws SQLException {
    String sql = "DELETE FROM node WHERE nodeID = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, nodeID);
    ps.executeUpdate();
  }

  public static String getTableName() {
    return tableName.toLowerCase();
  }

  public String getID() {
    return nodeID;
  }

  public int getXcoord() {
    return xcoord;
  }

  public int getYcoord() {
    return ycoord;
  }

  public String getFloor() {
    return floor;
  }

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

  public void setCoords(int newX, int newY) throws SQLException {
    xcoord = newX;
    ycoord = newY;
    update();
  }

  public static ArrayList<Node> getEmptyNodes() throws SQLException {
    ArrayList<Node> mtNodes = new ArrayList<Node>();
    String sql = "SELECT * FROM node WHERE nodeID not in (SELECT nodeID FROM move);";
    ResultSet rs = Bdb.processQuery(sql);
    while(rs.next()) {
      mtNodes.add(new Node(rs.getString("nodeID"),
              rs.getInt("xcoord"),
              rs.getInt("ycoord"),
              rs.getString("floor"),
              rs.getString("building")));
    }
    return mtNodes;
  }


}
