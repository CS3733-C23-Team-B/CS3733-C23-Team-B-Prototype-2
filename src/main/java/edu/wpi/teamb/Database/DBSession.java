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


}
