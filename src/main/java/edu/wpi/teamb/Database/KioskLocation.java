package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "kiosklocation", schema = "iter4")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KioskLocation {

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

  public KioskLocation() {};
}
