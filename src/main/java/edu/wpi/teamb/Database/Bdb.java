package edu.wpi.teamb.Database;

import java.sql.*;

/**
 * Singleton class that establishes connection with database
 */
public class Bdb {
  private Connection c;
  private static Bdb db;

  static {
    try {
      db = new Bdb();
      db.init();
    } catch (ClassNotFoundException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Establishes jdbc driver and creates connection
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public Bdb() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    c =
        DriverManager.getConnection(
            "jdbc:postgresql://wpi-softeng-postgres-db.coyfss2f91ba.us-east-1.rds.amazonaws.com:2112/dbb",
            "teamb",
            "4ipr31GQJAKch9baIPQlSYyMlywZ0yTe");
  }

  /**
   * Method to initialize all required tables in database if they do not exist
   * @throws SQLException
   */
  public void init() throws SQLException {

    if (!tableExists(Node.getTableName())) {
      String n = Node.getTableName();
      Node.initTable();
    }

    if (!tableExists(Edge.getTableName())) {
      Edge.initTable();
    }

    if (!tableExists(Login.getTableName())) {
      Login.initTable();
    }

    if (!tableExists(LocationName.getTableName())) {
      LocationName.initTable();
    }

    if (!tableExists(LocationName.getTableName())) {
      Move.initTable();
    }

    if (!tableExists(TransportationDataset.getTableName())) {
      TransportationDataset.initTable();
    }
  }

  /**
   * Creates a type PreparedStatement that can be used for sql commands
   * @param s the string used in the creation fo the PreparedStatement
   * @return a PreparedStatement
   * @throws SQLException
   */
  public static PreparedStatement prepareStatement(String s) throws SQLException {
    return getInstance().c.prepareStatement(s);
  }

  /**
   * Creates a type PreparedStatement that can be used for sql commands,
   * this function also auto generates a key
   * @param s the string used in the creation of the PreparedStatement
   * @return a PreparedStatement
   * @throws SQLException
   */
  public static PreparedStatement prepareKeyGeneratingStatement(String s) throws SQLException {
    return getInstance().c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
  }

  /**
   * Method used to execute a query in the database and return the results
   * @param s the string representing the sql query
   * @return a ResultSet, the result of the sql query
   * @throws SQLException
   */
  public static ResultSet processQuery(String s) throws SQLException {
    Statement stmt = getInstance().c.createStatement();
    ResultSet rs = stmt.executeQuery(s);
    return rs;
  }

  /**
   * Method used to execute an update in the database
   * @param s the string representing the sql update
   * @return an int representing the number of updates
   * @throws SQLException
   */
  public static int processUpdate(String s) throws SQLException {
    Statement stmt = getInstance().c.createStatement();
    int numUpdated = stmt.executeUpdate(s);
    return numUpdated;
  }

  /**
   * Method to close the database connection
   * @throws SQLException
   */
  public static void closeConnection() throws SQLException {
    getInstance().c.close();
    db = null;
  }

  /**
   * Method to check if a table exists in the database
   * @param tableName the name of the table to be checked
   * @return a Boolean, true if the table exists in the database, otherwise false
   * @throws SQLException
   */
  public static boolean tableExists(String tableName) throws SQLException {
    String query =
        "SELECT EXISTS (\n"
            + "SELECT FROM\n"
            + "   pg_tables\n"
            + "WHERE\n"
            + "   schemaname = 'public' AND\n"
            + "   tablename  = ?\n"
            + ");\n";

    PreparedStatement stmt = getInstance().c.prepareStatement(query);
    stmt.setString(1, tableName);
    ResultSet rs = stmt.executeQuery();
    rs.next();

    return rs.getBoolean(1);
  }

  /**
   * Method to get the instance of this class
   * @return
   */
  public static Bdb getInstance() {
    return db;
  }
}
