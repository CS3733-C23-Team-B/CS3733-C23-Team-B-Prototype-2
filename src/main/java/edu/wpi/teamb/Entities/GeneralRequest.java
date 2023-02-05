package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
// this will need to be changed to have inheritance work
@Table(name = "GeneralRequest")
@Inheritance(strategy = InheritanceType.JOINED)
public class GeneralRequest {

  @Id private Long id;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "firstname", length = 20)
  @Getter
  @Setter
  private String firstName;

  @Column(name = "lastname", length = 20)
  @Getter
  @Setter
  private String lastName;

  @Column(name = "email", length = 20)
  @Getter
  @Setter
  private String email;

  @Column(name = "employeeid", length = 20)
  @Getter
  @Setter
  private String employeeID;

  @Column(name = "urgency", length = 20)
  @Getter
  @Setter
  private String urgency;

  @Column(name = "TBD", length = 20)
  @Getter
  @Setter
  private String assignedEmployee;

  @Column(name = "notes", length = 20)
  @Getter
  @Setter
  private String notes;

  @Column(name = "status", length = 20)
  @Getter
  @Setter
  private RequestStatus status;

  public void generalRequest() {}

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
