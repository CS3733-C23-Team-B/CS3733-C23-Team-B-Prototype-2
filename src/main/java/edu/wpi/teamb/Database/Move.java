package edu.wpi.teamb.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

public class Move {

    public static final String tableName = "move";

    private String nodeID;

    private String longName;

    private String moveDate;

    public Move(String nodeID, String longName, String moveDate) {
        this.nodeID = nodeID;
        this.longName = longName;
        this.moveDate = moveDate;
    }

    public static void initTable() throws SQLException {
        String sql =
                String.join(" ",
                        "CREATE TABLE move",
                        "(nodeID VARCHAR(10),",
                        "longName VARCHAR(20),",
                        "moveDate VARCHAR(20),",
                        "PRIMARY KEY (nodeID, longName, moveDate) );");
        Bdb.processUpdate(sql);
    }

    public static ArrayList<Move> getAll() throws SQLException {
        ArrayList<Move> moves = new ArrayList<Move>();
        String sql = "SELECT * FROM move;";
        ResultSet rs = Bdb.processQuery(sql);
        while (rs.next()) {
            moves.add(
                    new Move(
                            rs.getString("nodeID"),
                            rs.getString("longName"),
                            rs.getString("moveDate")));
        }
        return moves;
    }

    public void insert() throws SQLException {
        String sql =
                "INSERT INTO move (nodeID, longName, moveDate) "
                        + "VALUES (?,?,?);";
        PreparedStatement ps = Bdb.prepareKeyGeneratingStatement(sql);
        ps.setString(1, nodeID);
        ps.setString(2, longName);
        ps.setString(3, moveDate);
    }

    public void update() throws SQLException {
        String sql =
                "UPDATE move "
                        + "SET nodeID = ?, longName = ?, moveDate = ? "
                        + "WHERE nodeID = ?, longName = ?, moveDate = ?;";

        PreparedStatement ps = Bdb.prepareStatement(sql);
        ps.setString(1, nodeID);
        ps.setString(2, longName);
        ps.setString(3, moveDate);
        ps.setString(4, nodeID);
        ps.setString(5, longName);
        ps.setString(6, moveDate);
        ps.executeUpdate();
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM move WHERE nodeID = ?, longName = ?, moveDate = ?;";
        PreparedStatement ps = Bdb.prepareStatement(sql);
        ps.setString(1, nodeID);
        ps.setString(2, longName);
        ps.setString(3, moveDate);
        ps.executeUpdate();
    }

    public static String getTableName() {
        return tableName.toLowerCase();
    }

    public String getInfo() {
        String str = "NodeID: "
                + nodeID
                + ", "
                + "Long Name: "
                + longName
                + ", "
                + "Move Date: "
                + moveDate;
        return str;
    }
}
