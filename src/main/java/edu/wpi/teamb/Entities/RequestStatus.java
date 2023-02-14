package edu.wpi.teamb.Entities;

public enum RequestStatus {
  BLANK("blank"),
  PROCESSING("processing"),
  DONE("done");
  private String str;

  RequestStatus(String str) {
    this.str = str;
  }

  public String toString() {
    return str;
  }
}
