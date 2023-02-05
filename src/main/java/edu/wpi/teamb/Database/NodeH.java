package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nodeh")
public class NodeH {

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

  public NodeH() {}
}
