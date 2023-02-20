package edu.wpi.teamb.Database.DAO;

import edu.wpi.teamb.Database.Requests.*;
import edu.wpi.teamb.Entities.SessionGetter;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RequestDAO {

  private static List<GeneralRequest> allRequests = new ArrayList<>();
  private static List<PatientTransportationRequest> PTRequests = new ArrayList<>();
  private static List<SanitationRequest> SanRequests = new ArrayList<>();
  private static List<ComputerRequest> CRequests = new ArrayList<>();

  private static List<SecurityRequest> SecRequests = new ArrayList<>();

  private static List<AudioVideoRequest> AVRequests = new ArrayList<>();

  public static List<GeneralRequest> getAllRequests() {
    return allRequests;
  }

  public static List<PatientTransportationRequest> getAllPTRequests() {
    refreshRequests();
    return PTRequests;
  }

  public static List<SanitationRequest> getAllSanRequests() {
    refreshRequests();
    return SanRequests;
  }

  public static List<SecurityRequest> getAllSecRequests() {
    refreshRequests();
    return SecRequests;
  }

  public static List<ComputerRequest> getAllCRequests() {
    refreshRequests();
    return CRequests;
  }

  public static List<AudioVideoRequest> getAllAVRequests() {
    refreshRequests();
    return AVRequests;
  }

  public static List<GeneralRequest> getAllRequestsWithEmpID(String id) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    String hql = "FROM GeneralRequest WHERE employeeID = '" + id + "'";
    List<GeneralRequest> rs = new ArrayList<GeneralRequest>();
    try {
      Transaction tx = session.beginTransaction();
      Query q = session.createQuery(hql, GeneralRequest.class);
      rs = q.list();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      return rs;
    }
  }

  public static void refreshRequests() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q1 = session.createQuery("FROM GeneralRequest", GeneralRequest.class);
      allRequests = q1.list();
      Query q2 =
          session.createQuery(
              "FROM PatientTransportationRequest", PatientTransportationRequest.class);
      PTRequests = q2.list();
      Query q3 = session.createQuery("FROM SanitationRequest", SanitationRequest.class);
      SanRequests = q3.list();
      Query q4 = session.createQuery("FROM ComputerRequest", ComputerRequest.class);
      CRequests = q4.list();
      Query q5 = session.createQuery("FROM SecurityRequest", SecurityRequest.class);
      SecRequests = q5.list();
      Query q6 = session.createQuery("FROM AudioVideoRequest", AudioVideoRequest.class);
      AVRequests = q6.list();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void addRequest(GeneralRequest r) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      session.persist(r);
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      refreshRequests();
    }
  }

  public static void updateRequest(GeneralRequest r) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      session.merge(r);
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      refreshRequests();
    }
  }
}
