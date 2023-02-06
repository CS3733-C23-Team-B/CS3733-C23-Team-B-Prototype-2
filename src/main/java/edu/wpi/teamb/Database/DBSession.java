package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.*;
import edu.wpi.teamb.SessionGetter;
import java.util.List;
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
      Query q = session.createQuery("FROM egde");
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

  public static List<Move> getAllMoves() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM move");
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

  public static List<Node> getAllNodes() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM node");
      List<Node> nodes = q.list();
      session.close();
      return nodes;
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
      session.createQuery("DELETE " + iorm.getSearchStr());
      session.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void updateLocationName(LocationName newLN, LocationName oldLN) {
    if (newLN.getLongName() == oldLN.getLongName()) {
      delete(oldLN);
      addORM(newLN);
    } else {
      List<Move> moves = getAllMoves();
      for (Move m : moves) {
        if (m.getLongName() == oldLN.getLongName()) {
          Move newm = new Move(m.getNodeID(), newLN.getLongName(), m.getMoveDate());
          addORM(newm);
          delete(m);
        }
      }
    }
  }

  public static void updateNode(Node n) {

    Node ncopy = new Node("", n.getXcoord(), n.getYcoord(), n.getFloor(), n.getBuilding());
    ncopy.setNodeID(ncopy.buildID());

    List<Edge> edges = getAllEdges();
    for (Edge e : edges) {
      if (e.getNode1() == n.getNodeID()) {
        Edge newe = new Edge(ncopy.getNodeID(), e.getNode2());
        addORM(newe);
        delete(e);
      } else if (e.getNode2() == n.getNodeID()) {
        Edge newe = new Edge(e.getNode1(), ncopy.getNodeID());
        addORM(newe);
        delete(e);
      }
    }

    List<Move> moves = getAllMoves();
    for (Move m : moves) {
      if (m.getNodeID() == n.getNodeID()) {
        Move newm = new Move(ncopy.getNodeID(), m.getLongName(), m.getMoveDate());
        addORM(newm);
        delete(m);
      }
    }

    addORM(ncopy);
    delete(n);
  }

  public static void updateAllNodes() {

    List<Node> nodes = getAllNodes();

    for (Node n : nodes) {
      updateNode(n);
    }
  }

  public static void main(String[] args) {
    Node n = new Node();
    n.setNodeID("Testing");
    n.setXcoord(5);
    n.setYcoord(5);
    n.setBuilding("testingbuilding");
    n.setFloor("testfloor");
    addORM(n);
  }
}
