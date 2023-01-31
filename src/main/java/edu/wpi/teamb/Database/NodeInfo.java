package edu.wpi.teamb.Database;

import java.sql.SQLException;
import java.util.Date;

public class NodeInfo {
  private String nodeID;
  private int xCoord;
  private int yCoord;
  private String location;
  private Date moveDate;
  private String edges;

  private Node node;
  private Move move;

  public NodeInfo(
      String nodeID,
      int xCoord,
      int yCoord,
      String location,
      Date moveDate,
      String edges,
      Node node,
      Move move) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.location = location;
    this.moveDate = moveDate;
    this.edges = edges;
    this.node = node;
    this.move = move;
  }

  public void update() {
    nodeID = node.buildID();
    node.setNodeID(nodeID);
    node.setXcoord(xCoord);
    node.setYcoord(yCoord);

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

  public Date getMoveDate() {
    return moveDate;
  }

  public String getEdges() {
    return edges;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setxCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public void setyCoord(int yCoord) {
    this.yCoord = yCoord;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setMoveDate(Date moveDate) {
    this.moveDate = moveDate;
  }

  public void setEdges(String edges) {
    this.edges = edges;
  }
}
