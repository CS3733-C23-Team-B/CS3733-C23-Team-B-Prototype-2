package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

/**
 * class that represents the edge table in the Database, allows updates, insertions, deletions, and
 * other interactions with the edge table in the database
 */
@Entity
@Table(name = "edge")
public class Edge {

  // Primary Key and Foreign Key
  @Id
  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @JoinColumn(
      name = "node1",
      nullable = false,
      foreignKey =
          @ForeignKey(
              name = "edge_node1_fk_iter3",
              foreignKeyDefinition =
                  "FOREIGN KEY (node1) REFERENCES iter3.Node(nodeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  @ManyToOne
  @Setter
  @Getter
  Node node1;

  // Primary Key and Foreign Key
  @Id
  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @JoinColumn(
      name = "node2",
      nullable = false,
      foreignKey =
          @ForeignKey(
              name = "edge_node2_fk_iter3",
              foreignKeyDefinition =
                  "FOREIGN KEY (node2) REFERENCES iter3.Node(nodeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  @ManyToOne
  @Setter
  @Getter
  Node node2;

  public Edge(Node node1, Node node2) {
    this.node1 = node1;
    this.node2 = node2;
  }

  public Edge() {}
}
