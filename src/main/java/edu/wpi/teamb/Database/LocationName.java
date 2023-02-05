package edu.wpi.teamb.Database;

import jakarta.persistence.*;

@Entity
@Table(name = "locationname")
public class LocationName {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "longname", length = 70)
  private String longName;

  @Column(name = "shortName", length = 20)
  private String shortName;

  @Column(name = "locationType", length = 20)
  private String locationType;

  public LocationName() {}

  public LocationName(String longName, String shortName, String locationType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = locationType;
  }

  public void getAll() {}
}
