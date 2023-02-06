package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.*;
import edu.wpi.teamb.SessionGetter;
import java.text.ParseException;
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

  public static List<Node> getAllNodes() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery("FROM Node");
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
      String str = "DELETE " + iorm.getSearchStr();
      session.createQuery(str).executeUpdate();
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
    System.out.println(ncopy.getNodeID());

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

  public static void main(String[] args) throws ParseException {

    Node n = new Node();
    n.setNodeID("test");
    n.setYcoord(5);
    n.setXcoord(5);
    n.setFloor("L1");
    n.setBuilding("testing");
    Node n2 = new Node();
    n2.setNodeID("test2");
    n2.setXcoord(20);
    n2.setYcoord(20);
    n2.setFloor("L1");
    n2.setBuilding("testing");
    Node n3 = new Node();
    n3.setNodeID("test3");
    n3.setXcoord(25);
    n3.setYcoord(25);
    n3.setFloor("L1");
    n3.setBuilding("testing");
    //    addORM(n);
    //    addORM(n2);
    //    addORM(n3);
    //
    //    Edge e = new Edge();
    //    e.setNode1(n.getNodeID());
    //    e.setNode2(n2.getNodeID());
    //    Edge e2 = new Edge();
    //    e2.setNode1(n3.getNodeID());
    //    e2.setNode2(n.getNodeID());
    //    addORM(e);
    //    addORM(e2);
    //
    //    LocationName ln = new LocationName();
    //    ln.setLongName("test ln");
    //    ln.setShortName("test sn");
    //    ln.setLocationType("test lt");
    //    LocationName ln2 = new LocationName();
    //    ln2.setLongName("test ln2");
    //    ln2.setShortName("test sn2");
    //    ln2.setLocationType("test lt2");
    //    LocationName ln3 = new LocationName();
    //    ln3.setLongName("test ln3");
    //    ln3.setShortName("test sn3");
    //    ln3.setLocationType("test lt3");
    //    addORM(ln);
    //    addORM(ln2);
    //    addORM(ln3);
    //
    //    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
    //    Move m = new Move();
    //    m.setNodeID(n.getNodeID());
    //    m.setLongName(ln.getLongName());
    //    m.setMoveDate(dateFormat.parse("01-01-2023"));
    //    Move m2 = new Move();
    //    m2.setNodeID(n2.getNodeID());
    //    m2.setLongName(ln2.getLongName());
    //    m2.setMoveDate(dateFormat.parse("01-01-2023"));
    //    Move m3 = new Move();
    //    m3.setNodeID(n3.getNodeID());
    //    m3.setLongName(ln3.getLongName());
    //    m3.setMoveDate(dateFormat.parse("01-01-2023"));
    //    addORM(m);
    //    addORM(m2);
    //    addORM(m3);
    updateNode(n);
  }
}
