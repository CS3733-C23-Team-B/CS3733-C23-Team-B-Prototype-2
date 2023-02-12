package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.*;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login")
@PrimaryKeyJoinColumn(name = "login", foreignKey = @ForeignKey(name = "loginIDKey"))
public class Login implements IORM {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
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

  public void delete() throws SQLException {}

  @Override
  public String getSearchStr() {
    return "FROM Login WHERE username = '" + getUsername() + "'";
  }
}
