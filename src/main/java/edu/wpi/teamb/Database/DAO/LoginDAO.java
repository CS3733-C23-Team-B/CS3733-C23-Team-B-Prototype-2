package edu.wpi.teamb.Database.DAO;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Entities.SessionGetter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LoginDAO {
  private static Map<String, Login> logins = new HashMap<String, Login>();

  public static Map<String, Login> getAllLogins() {
    refreshLogins();
    return logins;
  }

  public static ObservableList<String> getStaff() {
    ObservableList<String> list = FXCollections.observableArrayList();

    Map<String, Login> logins = DBSession.getAllLogins();
    logins.forEach(
        (key, value) -> {
          String name = value.getFirstname() + " " + value.getLastname();
          list.add(name);
        });

    return list;
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

  public static void deleteLogin(Login l) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.remove(l);
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      refreshLogins();
    }
  }

  public static void updateUser(String user, String first, String last, String email) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q =
          session.createQuery(
              "UPDATE Login SET "
                  + "email = '"
                  + email
                  + "'"
                  + ", firstname = '"
                  + first
                  + "'"
                  + ", lastname = '"
                  + last
                  + "'"
                  + " WHERE username = '"
                  + user
                  + "'");
      q.executeUpdate();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      refreshLogins();
    }
  }

  public static void updateAdmin(String user, Boolean b) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q =
          session.createQuery(
              "UPDATE Login SET " + "admin = " + b + "" + " WHERE username = '" + user + "'");
      q.executeUpdate();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      refreshLogins();
    }
  }

  public static void main(String[] args) {}

  public static boolean isAdmin(Login l) {
    if (l.getAdmin()) {
      return true;
    }
    return false;
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
