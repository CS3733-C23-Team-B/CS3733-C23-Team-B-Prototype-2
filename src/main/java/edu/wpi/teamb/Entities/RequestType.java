package edu.wpi.teamb.Entities;

public enum RequestType {
    SECURITY("Security"),
    SANITATION("Sanitation"),
    PATIENTTRANSPOTATION("Internal Patient Transportation"),
    COMPUTER("Computer"),
    AUDOVISUAL("Audio and Visual");

    private String str;

    RequestType(String str) {
        this.str = str;
    }

    public String toString() {
        return str;
    }
}
