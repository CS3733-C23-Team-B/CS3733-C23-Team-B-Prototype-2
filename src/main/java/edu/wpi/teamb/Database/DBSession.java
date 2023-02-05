package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.PatientTransportationRequest;
import edu.wpi.teamb.Entities.SanitationRequest;
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
    PatientTransportationRequest t = new PatientTransportationRequest();
    db.addORM(t);
    SanitationRequest s = new SanitationRequest();
    db.addORM(s);
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

  public static getAll()


}
