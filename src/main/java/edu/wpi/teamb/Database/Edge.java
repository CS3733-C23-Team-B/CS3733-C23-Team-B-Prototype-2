package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * class that represents the edge table in the Database, allows updates, insertions, deletions, and
 * other interactions with the edge table in the database
 */
@Entity
@Table(name = "edge")
public class Edge implements IORM {

  public static final String tableName = "edge";

  // Primary Key and Foreign Key
  @Id
  @Column(name = "node1", length = 20)
  @Setter
  @Getter
  String node1;

  // Primary Key and Foreign Key
  @Id
  @Column(name = "node2", length = 20)
  @Setter
  @Getter
  String node2;

  public Edge(String node1, String node2) {
    this.node1 = node1;
    this.node2 = node2;
  }

  public Edge() {}

  @Override
  public String getSearchStr() {
    return "FROM Edge WHERE node1 = '" + node1 + "' and node2 = '" + node2 + "'";
  }
}
