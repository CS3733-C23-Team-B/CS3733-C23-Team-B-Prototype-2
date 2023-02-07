package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
// this will need to be changed to have inheritance work
@Table(name = "GeneralRequest")
@Inheritance(strategy = InheritanceType.JOINED)
public class GeneralRequest {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private int id;

  @Column(name = "firstname", length = 20)
  @Getter
  @Setter
  private String firstname;

  @Column(name = "lastname", length = 20)
  @Getter
  @Setter
  private String lastname;

  @Column(name = "email", length = 20)
  @Getter
  @Setter
  private String email;

  @Column(name = "employeeid", length = 20)
  @Getter
  @Setter
  private String employeeID;

  @Column(name = "urgency", length = 40)
  @Getter
  @Setter
  private String urgency;

  @Column(name = "assignedto", length = 20)
  @Getter
  @Setter
  private String assignedEmployee;

  @Column(name = "notes", length = 60)
  @Getter
  @Setter
  private String notes;

  @Column(name = "status", length = 60)
  @Getter
  @Setter
  private RequestStatus status;

  public void generalRequest() {}

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
