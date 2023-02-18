package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "locationname", schema = "iter3Testing")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocationName {
  @Id
  @Column(name = "longname", length = 70)
  @Getter
  @Setter
  private String longName;

  @Column(name = "shortname", length = 50)
  @Getter
  @Setter
  private String shortName;

  @Column(name = "locationtype", length = 20)
  @Getter
  @Setter
  private String locationType;

  public LocationName() {}

  @Override
  public String toString() {
    return longName;
  }

  public LocationName(String longName, String shortName, String locationType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = locationType;
  }
}
