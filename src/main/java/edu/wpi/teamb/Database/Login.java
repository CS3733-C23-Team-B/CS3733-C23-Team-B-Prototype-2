package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "login")
public class Login implements IORM {
  private static final String tableName = "login";

  @Id
  @Column(name = "username", length = 70)
  private String username;

  @Column(name = "password", length = 70)
  private String password;

  public Login(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Login() {}

  public static void initTable() throws SQLException {}

  public void insert() throws SQLException {}

  public void delete() throws SQLException {}

  public static Map<String, Login> getAll() throws SQLException {
    HashMap<String, Login> users = new HashMap<String, Login>();

    return users;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public static String getTableName() {
    return tableName.toLowerCase();
  }

  @Override
  public String getSearchStr() {
    return "FROM login WHERE username = '" + username + "'";
  }
}
