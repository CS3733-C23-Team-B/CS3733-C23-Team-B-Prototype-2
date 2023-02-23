package edu.wpi.teamb.Entities;

public enum RequestType {
  ALLREQUESTS("All Requests"),
  SECURITY("Security"),
  SANITATION("Sanitation"),
  PATIENTTRANSPOTATION("Internal Patient Transportation"),
  COMPUTER("Computer"),
  MEDICINE("Medicine Delivery"),
  MEDICALEQUIPMENT("Medical Equipment Delivery"),
  FACILITIES("Facilities Maintenance"),
  AUDOVISUAL("Audio and Visual");


  private String str;

  RequestType(String str) {
    this.str = str;
  }

  public String toString() {
    return str;
  }
}
