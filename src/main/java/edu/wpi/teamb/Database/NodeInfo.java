package edu.wpi.teamb.Database;

import java.util.Date;

public class NodeInfo {
  private String nodeID;
  private int xCoord;
  private int yCoord;
  private String location;
  private Date moveDate;
  private String edges;

  public NodeInfo(
      String nodeID, int xCoord, int yCoord, String location, Date moveDate, String edges) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.location = location;
    this.moveDate = moveDate;
    this.edges = edges;
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
}
