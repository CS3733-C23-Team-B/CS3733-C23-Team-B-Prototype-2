package edu.wpi.teamb.Database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "login")
public class Login {
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

  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE login",
            "(username VARCHAR(20),",
            "password VARCHAR(20),",
            "PRIMARY KEY (username) );");
    Bdb.processUpdate(sql);
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO login (username, password)" + "VALUES (?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, username);
    ps.setString(2, password);

    ps.executeUpdate();
  }

  public void delete() throws SQLException {
    String sql = "DELETE FROM login WHERE username = ?";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, username);
    ps.executeUpdate();
  }

  public static Map<String, Login> getAll() throws SQLException {
    HashMap<String, Login> users = new HashMap<String, Login>();
    String sql = "SELECT * FROM login;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      users.put(
          rs.getString("username"), new Login(rs.getString("username"), rs.getString("password")));
    }
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
}
