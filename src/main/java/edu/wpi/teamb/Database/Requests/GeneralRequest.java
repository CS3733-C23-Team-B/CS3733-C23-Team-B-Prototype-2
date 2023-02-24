package edu.wpi.teamb.Database.Requests;

import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Entities.Urgency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
// this will need to be changed to have inheritance work
@Table(name = "GeneralRequest", schema = "iter4")
@Inheritance(strategy = InheritanceType.JOINED)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GeneralRequest {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gr_seq_iter4")
  @SequenceGenerator(name = "gr_seq_iter4", sequenceName = "gr_seq_iter4")
  @Id
  @Setter
  @Getter
  private int id;

  @Column(name = "firstname", length = 60)
  @Getter
  @Setter
  private String firstname;

  @Column(name = "lastname", length = 60)
  @Getter
  @Setter
  private String lastname;

  @Column(name = "email", length = 60)
  @Getter
  @Setter
  private String email;

  @Column(name = "employeeid", length = 60)
  @Getter
  @Setter
  private String employeeID;

  @Column(name = "urgency", length = 40)
  @Getter
  @Setter
  private Urgency urgency;

  @Column(name = "assignedto", length = 60)
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

  @Column(name = "date", length = 60)
  @Getter
  @Setter
  private String date;

  @Column(name = "requesttype", length = 60)
  @Getter
  @Setter
  private RequestType requestType;

  public void generalRequest() {}
}
