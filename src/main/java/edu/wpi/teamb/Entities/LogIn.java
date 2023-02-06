package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login")
@PrimaryKeyJoinColumn(name = "login", foreignKey = @ForeignKey(name = "loginIDKey"))
public class LogIn implements IORM {
  @Id private String username;

  @Column(name = "password", length = 20)
  @Getter
  @Setter
  private String password;

  public LogIn(String scol, String sc) {
    this.username = scol;
    this.password = sc;
  }

  public LogIn() {}

  public void delete() throws SQLException {}

  @Override
  public String getSearchStr() {
    return "FROM LogIn WHERE username=" + getUsername();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}