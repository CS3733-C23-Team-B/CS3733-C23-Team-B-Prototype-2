package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * class that represents the node table in the Database, allows, insertions, deletions, and other
 * interactions with the node table in the database
 */
@Entity
@Table(name = "node", schema = "iter3")
public class Node {

  @Id
  @Column(name = "nodeID", length = 14)
  @Getter
  @Setter
  private String nodeID;

  @Column(name = "xcoord", length = 4)
  @Getter
  @Setter
  private int xCoord;

  @Column(name = "ycoord", length = 4)
  @Getter
  @Setter
  private int yCoord;

  @Column(name = "floor", length = 4)
  @Getter
  @Setter
  private String floor;

  @Column(name = "building", length = 25)
  @Getter
  @Setter
  private String building;

  public Node() {}

  @Override
  public String toString() {
    return nodeID;
  }

  public String buildID() {
    String x = Integer.toString(xCoord);
    String y = Integer.toString(yCoord);
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
