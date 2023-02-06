package edu.wpi.teamb.Database;

import java.sql.SQLException;
import java.util.Date;

public class NodeInfo {
  private String nodeID;
  private int xCoord;
  private int yCoord;
  private String location;
  private String floor;
  private Date moveDate;
  private String edges;
  private Node node;
  private Move move;

  public NodeInfo(
      String nodeID,
      int xCoord,
      int yCoord,
      String location,
      String floor,
      Date moveDate,
      String edges,
      Node node,
      Move move) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.location = location;
    this.floor = floor;
    this.moveDate = moveDate;
    this.edges = edges;
    this.node = node;
    this.move = move;
  }

  public void update() {
    node.setXCoord(xCoord);
    node.setYCoord(yCoord);
    node.setFloor(floor);
    nodeID = node.buildID();
    node.setNodeID(nodeID);

    try {
      node.update();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public String getNodeID() {
    return nodeID;
  }

  public int getXCoord() {
    return xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public String getLocation() {
    return location;
  }

  public String getFloor() {
    return floor;
  }

  public Date getMoveDate() {
    return moveDate;
  }

  public String getEdges() {
    return edges;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public void setYCoord(int yCoord) {
    this.yCoord = yCoord;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setMoveDate(Date moveDate) {
    this.moveDate = moveDate;
  }

  public void setEdges(String edges) {
    this.edges = edges;
  }
}
