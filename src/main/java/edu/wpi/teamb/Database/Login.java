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
  @Id private String username;

  @Column(name = "password", length = 60)
  @Getter
  @Setter
  private String password;

  public Login(String scol, String sc) {
    this.username = scol;
    this.password = sc;
  }

  public Login() {}

  public void delete() throws SQLException {}

  @Override
  public String getSearchStr() {
    return "FROM Login WHERE username = '" + getUsername() + "'";
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
