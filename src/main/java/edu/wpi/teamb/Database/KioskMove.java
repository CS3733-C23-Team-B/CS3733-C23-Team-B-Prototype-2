package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "kioskmove", schema = "iter4")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KioskMove {

  @Id
  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @JoinColumn(
      name = "nextNode",
      nullable = false,
      foreignKey =
          @ForeignKey(
              name = "kioskmove_nextnode_fk_iter4",
              foreignKeyDefinition =
                  "FOREIGN KEY (nextNode) REFERENCES iter4.Node(nodeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  @ManyToOne
  @Setter
  @Getter
  private Node nextNode;

  @Id
  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @JoinColumn(
      name = "locationName",
      nullable = false,
      foreignKey =
          @ForeignKey(
              name = "move_locationName_fk_iter4",
              foreignKeyDefinition =
                  "FOREIGN KEY (locationName) REFERENCES iter4.LocationName(longName) ON UPDATE CASCADE ON DELETE CASCADE"))
  @ManyToOne
  @Setter
  @Getter
  private LocationName locationName;

  @Id
  @Column(name = "movedate", length = 20)
  @Getter
  @Setter
  private Date moveDate;

  @Column(name = "prevnode")
  @Setter
  @Getter
  private Object prevNode;

  @Column(name = "message")
  @Getter
  @Setter
  private String message;

  public KioskMove() {};
}
