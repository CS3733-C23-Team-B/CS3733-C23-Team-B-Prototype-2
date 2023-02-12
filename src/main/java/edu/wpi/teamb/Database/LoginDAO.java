package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginDAO {
    private static Map<String, Login> logins = new HashMap<String, Login>();
    public static Map<String, Login> getAllLogins() {
        return logins;
    }

  public static void addLogin(Login l) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      session.persist(l);
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      refreshLogins();
    }
  }

    public static void refreshLogins() {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Login ", Login.class);
            List<Login> ns = q.list();
            tx.commit();
            logins.clear();
            for (Login login : ns) logins.put(login.getUsername(), login);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
