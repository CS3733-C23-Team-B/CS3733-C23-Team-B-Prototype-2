package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "move")
public class Move implements IORM {

  @Id
  @Column(name = "node", length = 14)
  @Getter
  @Setter
  private String nodeID;

  @Id
  @Column(name = "locationname", length = 70)
  @Getter
  @Setter
  private String locationName;

  @Id
  @Column(name = "movedate", length = 20)
  @Getter
  @Setter
  private Date moveDate;

  public Move(String nodeID, String longName, Date moveDate) {
    this.nodeID = nodeID;
    this.locationName = longName;
    this.moveDate = moveDate;
  }

  public Move() {}

  @Override
  public String getSearchStr() {
    return "FROM Move WHERE nodeID = '" + nodeID + "' and locationName = '" + locationName + "'";
  }
}
