package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login")
@PrimaryKeyJoinColumn(name = "login", foreignKey = @ForeignKey(name = "loginIDKey"))
public class Login {
  @GeneratedValue(strategy = GenerationType.AUTO)
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
