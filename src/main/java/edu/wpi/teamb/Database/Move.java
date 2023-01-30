package edu.wpi.teamb.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Move {

  public static final String tableName = "move";

  private String nodeID;

  private String longName;

  private Date moveDate;

  public Move(String nodeID, String longName, Date moveDate) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.moveDate = moveDate;
  }

  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE move",
            "(nodeID CHAR(10),",
            "longName VARCHAR(70),",
            "moveDate DATE,",
            "PRIMARY KEY (nodeID, longName, moveDate),",
            "FOREIGN KEY (nodeID) REFERENCES Node(nodeID) ON UPDATE CASCADE,",
            "FOREIGN KEY (longName) REFERENCES LocationName (longName) ON UPDATE CASCADE );");
    Bdb.processUpdate(sql);
  }

  public static ArrayList<Move> getAll() throws SQLException {
    ArrayList<Move> moves = new ArrayList<Move>();
    String sql = "SELECT * FROM move;";
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      moves.add(new Move(rs.getString("nodeID"), rs.getString("longName"), rs.getDate("moveDate")));
    }
    return moves;
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO move (nodeID, longName, moveDate) " + "VALUES (?,?,?);";
    PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
    ps.setString(1, nodeID);
    ps.setString(2, longName);
    ps.setDate(3, moveDate);
  }

  public void update() throws SQLException {
    String sql =
        "UPDATE move "
            + "SET nodeID = ?, longName = ?, moveDate = ? "
            + "WHERE nodeID = ?, longName = ?, moveDate = ?;";

    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, nodeID);
    ps.setString(2, longName);
    ps.setDate(3, moveDate);
    ps.setString(4, nodeID);
    ps.setString(5, longName);
    ps.setDate(6, moveDate);
    ps.executeUpdate();
  }

  public void delete() throws SQLException {
    String sql = "DELETE FROM move WHERE nodeID = ?, longName = ?, moveDate = ?;";
    PreparedStatement ps = Bdb.prepareStatement(sql);
    ps.setString(1, nodeID);
    ps.setString(2, longName);
    ps.setDate(3, moveDate);
    ps.executeUpdate();
  }

  public static String getTableName() {
    return tableName.toLowerCase();
  }

  public String getInfo() {
    String str =
        "NodeID: " + nodeID + ", " + "Long Name: " + longName + ", " + "Move Date: " + moveDate;
    return str;
  }

  public static String getLocationName(String nodeID) throws SQLException {
    String sql = "SELECT longName FROM move WHERE nodeID = " + nodeID;
    ResultSet rs = Bdb.processQuery(sql);
    rs.next();
    if (rs.wasNull()) {
      return "";
    } else {
      return rs.getString("longName");
    }
  }

  public String getLongName() {
    return longName;
  }

  /**
   * Given a longName of a department, determines node that the department occupies
   *
   * @param longName the long name of the department being located
   * @return the NodeID of the node that the department currently occupies
   * @throws SQLException
   */
  public static String getMostRecentNode(String longName) throws SQLException {
    List<Move> moves = getAllLN(longName);

    if (moves.isEmpty()) return "NO MOVES";

    Move mostRecent = moves.get(0);
    for (Move move : moves) if (moreRecentThan(move, mostRecent)) mostRecent = move;

    return mostRecent.nodeID;
  }

  public static String getMostRecentLocation(String NodeID) {
    List<Move> moves = null;
    try {
      moves = getAllNodeID(NodeID);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    if (moves.isEmpty()) return "NO MOVES";

    Move mostRecent = moves.get(0);
    for (Move move : moves) if (moreRecentThan(move, mostRecent)) mostRecent = move;

    return mostRecent.longName;
  }

  private static List<Move> getAllLN(String LNSearch) throws SQLException {
    String sql = "SELECT * FROM move WHERE longName = '" + LNSearch + "';";
    ArrayList<Move> moves = new ArrayList<Move>();
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      moves.add(new Move(rs.getString("nodeID"), rs.getString("longName"), rs.getDate("moveDate")));
    }
    return moves;
  }

  private static List<Move> getAllNodeID(String IDSearch) throws SQLException {
    String sql = "SELECT * FROM move WHERE nodeID = '" + IDSearch + "';";
    ArrayList<Move> moves = new ArrayList<Move>();
    ResultSet rs = Bdb.processQuery(sql);
    while (rs.next()) {
      moves.add(new Move(rs.getString("nodeID"), rs.getString("longName"), rs.getDate("moveDate")));
    }
    return moves;
  }

  private static boolean moreRecentThan(Move move1, Move move2) {
    return move1.moveDate.after(move2.moveDate);
  }
}
