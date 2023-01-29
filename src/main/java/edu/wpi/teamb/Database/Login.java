package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Login {
  private static final String tableName = "login";
  private String username, password, email;

  public Login(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE login",
            "(username VARCHAR(20),",
            "password VARCHAR(20),",
            "email VARCHAR(20),",
            "PRIMARY KEY (username) );");
    Bdb.processUpdate(sql);
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO login (username, password, email)" + "VALUES (?,?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, username);
    ps.setString(2, password);
    ps.setString(3, email);

    ps.executeUpdate();
  }

  public static Map<String, Login> getAll() throws SQLException {
    HashMap<String, Login> users = new HashMap<String, Login>();
    String sql = "SELECT * FROM login;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      users.put(
          rs.getString("username"),
          new Login(rs.getString("username"), rs.getString("password"), rs.getString("email")));
    }
    return users;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public static String getTableName() {
    return tableName.toLowerCase();
  }
}
