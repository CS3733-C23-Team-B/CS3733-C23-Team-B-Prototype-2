package edu.wpi.teamb.Database;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class EdgeID implements Serializable {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EdgeID edgeID = (EdgeID) o;
    return getNode1().equals(edgeID.getNode1()) && getNode2().equals(edgeID.getNode2());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNode1(), getNode2());
  }
}
