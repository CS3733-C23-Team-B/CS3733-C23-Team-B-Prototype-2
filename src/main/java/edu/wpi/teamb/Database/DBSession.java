package edu.wpi.teamb.Database;

import edu.wpi.teamb.Database.DAO.LoginDAO;
import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Database.DAO.RequestDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBSession {

  private static DBSession instance = null;

  private DBSession() {};

  public static void refreshAll() {
    MapDAO.refreshNodes();
    MapDAO.refreshEdges();
    MapDAO.refreshLocationNames();
    RequestDAO.refreshRequests();
    LoginDAO.refreshLogins();
  }

  public static void addNode(Node n) {
    MapDAO.addNode(n);
  }

  public static void deleteNode(Node n) {
    MapDAO.deleteNode(n);
  }

  public static void updateNode(Node n) {
    MapDAO.updateNode(n);
  }

  public static void addLogin(Login l) {
    LoginDAO.addLogin(l);
  }

  public static void deleteLogin(Login l) {
    LoginDAO.deleteLogin(l);
  }

  public static void addEdge(Edge e) {
    MapDAO.addEdge(e);
  }

  public static void deleteEdge(Node n1, Node n2) {
    MapDAO.deleteEdge(n1, n2);
  }

  public static void addLocationName(LocationName ln) {
    MapDAO.addLocationName(ln);
  }

  public static void deleteLocationName(LocationName ln) {
    MapDAO.deleteLocationName(ln);
  }

  public static void updateLocationName(LocationName newLN, LocationName oldLN) {
    MapDAO.updateLocationName(newLN, oldLN);
  }

  public static void addMove(Move m) {
    MapDAO.addMove(m);
  }

  public static void deleteMove(Move m) {
    MapDAO.deleteMove(m);
  }

  public static void addRequest(GeneralRequest r) {
    RequestDAO.addRequest(r);
  }

  public static Map<String, Login> getAllLogins() {
    return LoginDAO.getAllLogins();
  }

  public static Map<String, Node> getAllNodes() {
    return MapDAO.getAllNodes();
  }

  public static List<Edge> getAllEdges() {
    return MapDAO.getAllEdges();
  }

  public static Map<String, LocationName> getAllLocationNames() {
    return MapDAO.getAllLocationNames();
  }

  public static List<GeneralRequest> getAllRequests() {
    return RequestDAO.getALLRequests();
  }

  public static List<PatientTransportationRequest> getAllPTRequests() {
    return RequestDAO.getALLPTRequests();
  }

  public static List<SanitationRequest> getAllSRequests() {
    return RequestDAO.getALLSRequests();
  }

  public static List<ComputerRequest> getAllCRequests() {
    return RequestDAO.getALLCRequests();
  }

  public static Map<String, ArrayList<Move>> getIDMoves(Date d) {
    return MapDAO.getIDMoves(d);
  }

  public static Map<String, Move> getLNMoves(Date d) {
    return MapDAO.getLNMoves(d);
  }

  public static void updateMove(Move oldM, Move newM) {
    MapDAO.updateMove(oldM, newM);
  }

  public static Move getMostRecentMoveWithLocationName(LocationName ln) {
    return MapDAO.getMostRecentMoveWithLocationName(ln);
  }

  public static void updateUser(String user, String first, String last, String email) {
    LoginDAO.updateUser(user, first, last, email);
  }

  public static void updateAdmin(String user, Boolean b) {
    LoginDAO.updateAdmin(user, b);
  }

  public static Boolean isAdmin(Login l) {
    return LoginDAO.isAdmin(l);
  }

  public static List<Move> getMostRecentMoves(String NodeID) {
    try {
      return MapDAO.getIDMoves(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01")).get(NodeID);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getMostRecentNodeID(String longName) {
    try {
      return MapDAO.getLNMoves(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"))
          .get(longName)
          .getNode()
          .getNodeID();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
