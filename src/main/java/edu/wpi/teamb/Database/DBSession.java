package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.*;
import edu.wpi.teamb.Entities.SessionGetter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DBSession {

  private static DBSession instance = null;

  private DBSession() {};

  public static DBSession getInstance() {
    if (instance == null) {
      instance = new DBSession();
    }
    return instance;
  }

  private static Map<String, Node> nodeMap = new HashMap<>();

  public static void addORM(Object o) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.persist(o);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static List<Object> getAll(ORMType ot) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM " + ot.toString());
      List<Object> objects = q.list();
      session.close();
      return objects;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static List<Edge> getAllEdges() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM Edge");
      List<Edge> edges = q.list();
      session.close();
      return edges;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static List<LocationName> getAllLocationNames() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM LocationName");
      List<LocationName> locationNames = q.list();
      session.close();
      return locationNames;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static List<Move> getAllMoves() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM Move");
      List<Move> moves = q.list();
      session.close();
      return moves;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static List<Move> getAllMovesWithLN(String ln) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM Move WHERE longName = '" + ln + "'");
      List<Move> moves = q.list();
      session.close();
      return moves;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static Map<String, Node> getAllNodes() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM Node");
      List<Node> nodes = q.list();
      session.close();
      for (Node node : nodes) nodeMap.put(node.getNodeID(), node);
      return nodeMap;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static List<LocationName> getAllLN() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM LocationName");
      List<LocationName> lns = q.list();
      session.close();
      return lns;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static void delete(IORM iorm) {

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      String str = "DELETE " + iorm.getSearchStr();
      session.createQuery(str).executeUpdate();
      session.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void deleteNode(Node n) {
    List<Edge> es = getAllEdges();
    for (Edge e : es) {
      if (e.getNode1().equals(n.getNodeID()) || e.getNode2().equals(n.getNodeID())) {
        delete(e);
      }
    }
    List<Move> ms = getAllMoves();
    for (Move m : ms) {
      if (m.getNodeID().equals(n.getNodeID())) {
        delete(m);
      }
    }
    delete(n);
  }

  public static void deleteLN(LocationName ln) {
    List<Move> ms = getAllMoves();
    for (Move m : ms) {
      if (m.getLongName().equals(ln.getLongName())) {
        delete(m);
      }
    }
    delete(ln);
  }

  public static void updateLocationName(LocationName newLN, LocationName oldLN) {
    if (newLN.getLongName().equals(oldLN.getLongName())) {
      delete(oldLN);
      addORM(newLN);
    } else {
      List<Move> moves = getAllMoves();
      for (Move m : moves) {
        if (m.getLongName().equals(oldLN.getLongName())) {
          Move newm = new Move(m.getNodeID(), newLN.getLongName(), m.getMoveDate());
          addORM(newm);
          delete(m);
        }
      }
    }
  }

  public static void updateNode(Node n) {

    Node ncopy = new Node();
    ncopy.setNodeID(n.buildID());
    ncopy.setXCoord(n.getXCoord());
    ncopy.setYCoord(n.getYCoord());
    ncopy.setFloor(n.getFloor());
    ncopy.setBuilding(n.getBuilding());
    addORM(ncopy);

    List<Edge> edges = getAllEdges();
    for (Edge e : edges) {
      if (e.getNode1().equals(n.getNodeID())) {
        Edge newe = new Edge();
        newe.setNode1(ncopy.getNodeID());
        newe.setNode2(e.getNode2());
        addORM(newe);
        delete(e);
      } else if (e.getNode2().equals(n.getNodeID())) {
        Edge newe = new Edge();
        newe.setNode1(e.getNode1());
        newe.setNode2(ncopy.getNodeID());
        addORM(newe);
        delete(e);
      }
    }

    List<Move> moves = getAllMoves();
    for (Move m : moves) {
      if (m.getNodeID().equals(n.getNodeID())) {
        Move newm = new Move();
        newm.setNodeID(ncopy.getNodeID());
        newm.setLongName(m.getLongName());
        newm.setMoveDate(m.getMoveDate());
        addORM(newm);
        delete(m);
      }
    }

    delete(n);
  }

  public static void updateAllNodes() {
    Map<String, Node> nodes = getAllNodes();

    for (Node n : nodes.values()) {
      updateNode(n);
    }
  }

  public static String getMostRecentLocation(String NodeID) {
    return getMostRecentMove(NodeID).getLongName();
  }

  public static String getMostRecentNodeID(String longName) {
    List<Move> moves = getAllMovesWithLN(longName);

    if (moves == null) return "NO MOVES";

    Move mostRecent = moves.get(0);
    for (Move move : moves) if (moreRecentThan(move, mostRecent)) mostRecent = move;

    return mostRecent.getNodeID();
  }

  public static Node getMostRecentNode(String longName) {
    String id = getMostRecentNodeID(longName);
    return nodeMap.get(id);
  }

  public static Move getMostRecentMove(String nodeID) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    List<Move> moves;
    try {
      Transaction tx = session.beginTransaction();
      String str = "FROM Move WHERE nodeID = '" + nodeID + "'";
      Query q = session.createQuery(str);
      moves = q.list();
      session.close();
      if (moves.isEmpty()) {
        return null;
      } else {
        Move mostRecent = moves.get(0);
        for (Move move : moves) if (moreRecentThan(move, mostRecent)) mostRecent = move;
        return mostRecent;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static boolean moreRecentThan(Move move1, Move move2) {
    return move1.getMoveDate().after(move2.getMoveDate());
  }

  public static void switchMoveLN(Node n, LocationName ln) {
    Move m = getMostRecentMove(n.getNodeID());
    Move mnew = new Move();
    mnew.setNodeID(n.getNodeID());
    mnew.setLongName(ln.getLongName());
    mnew.setMoveDate(m.getMoveDate());
    delete(m);
    addORM(mnew);
  }
}
