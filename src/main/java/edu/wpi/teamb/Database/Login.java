package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "login", schema = "iter3Testing")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Login {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq_iter3Testing")
  @SequenceGenerator(name = "log_seq_iter3Testing", sequenceName = "log_seq_iter3Testing")
  @Getter
  @Setter
  @Id
  private int id;

  @Column(name = "username", length = 60)
  @Getter
  @Setter
  private String username;

  @Column(name = "password", length = 60)
  @Getter
  @Setter
  private String password;

  @Column(name = "email", length = 60)
  @Getter
  @Setter
  private String email;

  @Column(name = "firstname", length = 60)
  @Getter
  @Setter
  private String firstname;

  @Column(name = "lastname", length = 60)
  @Getter
  @Setter
  private String lastname;

  @Column(name = "admin")
  @Getter
  @Setter
  private Boolean admin;

  public Login(String user, String pass, String email, String firstname, String lastname) {
    this.username = user;
    this.password = pass;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.admin = false;
  }

  public Login() {}
}
