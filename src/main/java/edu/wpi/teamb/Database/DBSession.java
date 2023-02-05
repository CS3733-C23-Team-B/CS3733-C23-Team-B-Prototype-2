package edu.wpi.teamb.Database;

import edu.wpi.teamb.SessionGetter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DBSession {

  private static DBSession instance = null;

  private DBSession() {};

  public static DBSession getInstance() {
    if (instance == null) {
      instance = new DBSession();
    }
    return instance;
  }

  public static void main(String[] args) {
    DBSession db = new DBSession();
    NodeH n = new NodeH();
    n.setNodeID("Test1");
    n.setXcoord(52);
    n.setYcoord(52);
    n.setFloor("Tester2");
    n.setBuilding("Building Test2");
    db.addNodeH(n);

    Transportation t = new Transportation();
    t.setDestination("ding");
    t.setRequestId(1);
    db.addTransportation(t);
  }

  public static void addNodeH(NodeH aNode) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.persist(aNode);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void addTransportation(Transportation t) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.persist(t);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
}
