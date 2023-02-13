package edu.wpi.teamb.Database.DAO;

import edu.wpi.teamb.Database.ComputerRequest;
import edu.wpi.teamb.Database.GeneralRequest;
import edu.wpi.teamb.Database.PatientTransportationRequest;
import edu.wpi.teamb.Database.SanitationRequest;
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
  private static List<SanitationRequest> SRequests = new ArrayList<>();
  private static List<ComputerRequest> CRequests = new ArrayList<>();

  public static List<GeneralRequest> getALLRequests() {
    return allRequests;
  }

  public static List<PatientTransportationRequest> getALLPTRequests() {
    refreshRequests();
    return PTRequests;
  }

  public static List<SanitationRequest> getALLSRequests() {
    refreshRequests();
    return SRequests;
  }

  public static List<ComputerRequest> getALLCRequests() {
    refreshRequests();
    return CRequests;
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
      SRequests = q3.list();
      Query q4 = session.createQuery("FROM ComputerRequest", ComputerRequest.class);
      CRequests = q4.list();
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
}
