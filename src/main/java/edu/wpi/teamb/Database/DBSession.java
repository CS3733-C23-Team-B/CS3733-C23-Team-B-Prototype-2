package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.ORMType;
import edu.wpi.teamb.Entities.PatientTransportationRequest;
import edu.wpi.teamb.Entities.SanitationRequest;
import edu.wpi.teamb.SessionGetter;
import java.sql.Date;
import java.util.List;
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
    LocationName ln = new LocationName("test", "t", "bathroom");

    PatientTransportationRequest t = new PatientTransportationRequest();
    db.addORM(t);
    SanitationRequest s = new SanitationRequest();
    db.addORM(s);
    Date date = new Date(2001, 12, 1);
    Move m = new Move("test", "test", date);
    db.addORM(m);
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
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      List<Object> objects = session.createQuery("SELECT * FROM iter1." + ot.toString()).list();
      return objects;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
