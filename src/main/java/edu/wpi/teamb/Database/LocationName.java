package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "locationname")
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
