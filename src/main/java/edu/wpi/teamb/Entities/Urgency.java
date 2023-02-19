package edu.wpi.teamb.Entities;

public enum Urgency {
  LOW("Low"),
  MODERATE("Moderate"),

  HIGH("High"),
  REQUIRESIMMEADIATEATTENTION("Requires Immediate Attention");
  private String str;

  Urgency(String str) {
    this.str = str;
  }

  public String toString() {
    return str;
  }
}
