package edu.wpi.teamb.Database;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * class that represents the node table in the Database, allows, insertions, deletions, and other
 * interactions with the node table in the database
 */
public class Node {

  @Id @Getter @Setter private String nodeID;

  @Column(name = "xcoord", length = 4)
  @Getter
  @Setter
  private int xcoord;

  @Column(name = "ycoord", length = 4)
  @Getter
  @Setter
  private int ycoord;

  @Column(name = "floor", length = 25)
  @Getter
  @Setter
  private String floor;

  @Column(name = "building", length = 20)
  @Getter
  @Setter
  private String building;

  public Node() {}

  public Node(String nodeID, int xcoord, int ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
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
