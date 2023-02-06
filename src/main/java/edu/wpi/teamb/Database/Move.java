package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.IORM;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "move")
public class Move implements IORM {

  public static final String tableName = "move";

  @Id
  @Column(name = "nodeID", length = 20)
  @Getter
  @Setter
  private String nodeID;

  @Column(name = "longname", length = 70)
  @Getter
  @Setter
  private String longName;

  @Column(name = "movedate", length = 20)
  @Getter
  @Setter
  private Date moveDate;

  public Move(String nodeID, String longName, Date moveDate) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.moveDate = moveDate;
  }

  public Move() {}

  public static void initTable() throws SQLException {}

  public static ArrayList<Move> getAll() throws SQLException {
    ArrayList<Move> moves = new ArrayList<Move>();
    return moves;
  }

  public void insert() throws SQLException {}

  public void update() throws SQLException {}

  public void delete() throws SQLException {}

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
    return sql;
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

  public static Move getMostRecentMove(String NodeID) {
    List<Move> moves = null;
    try {
      moves = getAllNodeID(NodeID);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    if (moves.isEmpty()) return null;

    Move mostRecent = moves.get(0);
    for (Move move : moves) if (moreRecentThan(move, mostRecent)) mostRecent = move;

    return mostRecent;
  }

  public static String getMostRecentLocation(String NodeID) {
    return getMostRecentMove(NodeID).longName;
  }

  private static List<Move> getAllLN(String LNSearch) throws SQLException {
    String sql = "SELECT * FROM move WHERE longName = '" + LNSearch + "';";
    ArrayList<Move> moves = new ArrayList<Move>();
    return moves;
  }

  private static List<Move> getAllNodeID(String IDSearch) throws SQLException {
    String sql = "SELECT * FROM move WHERE nodeID = '" + IDSearch + "';";
    ArrayList<Move> moves = new ArrayList<Move>();
    return moves;
  }

  private static boolean moreRecentThan(Move move1, Move move2) {
    return move1.moveDate.after(move2.moveDate);
  }

  @Override
  public String getSearchStr() {
    return "FROM move WHERE nodeid = '" + nodeID + "' and longname = '" + longName + "'";
  }
}
