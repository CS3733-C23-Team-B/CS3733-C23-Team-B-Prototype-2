package edu.wpi.teamb.Database;

import edu.wpi.teamb.Database.DAO.LoginDAO;
import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Database.DAO.RequestDAO;
import edu.wpi.teamb.Database.Requests.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.collections.ObservableList;

public class DBSession {

  private static DBSession instance = null;

  private DBSession() {}

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

  public static ObservableList<String> getStaff() {
    return LoginDAO.getStaff();
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

  public static synchronized List<Edge> getAllEdges() {
    return MapDAO.getAllEdges();
  }

  public static synchronized Map<String, LocationName> getAllLocationNames() {
    return MapDAO.getAllLocationNames();
  }

  public static synchronized List<GeneralRequest> getAllRequests() {
    return RequestDAO.getAllRequests();
  }

  public static synchronized List<PatientTransportationRequest> getAllPTRequests() {
    return RequestDAO.getAllPTRequests();
  }

  public static synchronized List<SanitationRequest> getAllSanRequests() {
    return RequestDAO.getAllSanRequests();
  }

  public static synchronized List<SecurityRequest> getAllSecRequests() {
    return RequestDAO.getAllSecRequests();
  }

  public static synchronized List<AudioVideoRequest> getAllAVRequests() {
    return RequestDAO.getAllAVRequests();
  }

  public static synchronized List<ComputerRequest> getAllCRequests() {
    return RequestDAO.getAllCRequests();
  }

  public static synchronized List<MedicalEquipmentDeliveryRequest> getAllMEDRequests() {
    return RequestDAO.getAllMEDRequests();
  }

  public static synchronized List<MedicineDeliveryRequest> getAllMDRequests() {
    return RequestDAO.getAllMDRequests();
  }

  public static synchronized List<FacilitiesRequest> getAllFacRequests() {
    return RequestDAO.getAllFacRequests();
  }

  public static synchronized Map<String, List<Move>> getIDMoves(Date d) {
    MapDAO.refreshIDMoves(d);
    return MapDAO.getIDMoves();
  }

  public static synchronized Map<String, List<Move>> getIDMoves() {
    return MapDAO.getIDMoves();
  }

  public static synchronized Map<String, Move> getLNMoves(Date d) {
    return MapDAO.getLNMoves(d);
  }

  public static synchronized List<GeneralRequest> getAllRequestsWithEmpID(String id) {
    return RequestDAO.getAllRequestsWithEmpID(id);
  }

  public static synchronized void updateMove(Move oldM, Move newM) {
    MapDAO.updateMove(oldM, newM);
  }

  public static synchronized Move getMostRecentMoveWithLocationName(LocationName ln) {
    return MapDAO.getMostRecentMoveWithLocationName(ln);
  }

  public static synchronized List<Move> getFutureMoves(Date d) {
    return MapDAO.getFutureMoves(d);
  }

  public static synchronized void updateUser(String user, String first, String last, String email) {
    LoginDAO.updateUser(user, first, last, email);
  }

  public static synchronized void updateAdmin(String user, Boolean b) {
    LoginDAO.updateAdmin(user, b);
  }

  public static synchronized Boolean isAdmin(Login l) {
    return LoginDAO.isAdmin(l);
  }

  public static synchronized List<Move> getMostRecentMoves(String NodeID) {
    try {
      return MapDAO.getIDMoves().get(NodeID);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static synchronized String getMostRecentNodeID(String longName) {
    try {
      return MapDAO.getLNMoves(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"))
          .get(longName)
          .getNode()
          .getNodeID();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    } catch (NullPointerException e) {
      return null;
    }
  }

  public static synchronized void updateRequest(GeneralRequest r) {
    RequestDAO.updateRequest(r);
  }
}
