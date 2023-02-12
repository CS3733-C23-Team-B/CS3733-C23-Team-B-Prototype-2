package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RequestDAO {

  private static List<GeneralRequest> allRequests;
  private static List<PatientTransportationRequest> PTRequests;
  private static List<SanitationRequest> SRequests;
  private static List<ComputerRequest> CRequests;

    public static List<GeneralRequest> getRequests() { return allRequests; }

  public static List<PatientTransportationRequest> getPTRequests() {
    return PTRequests;
  }

  public static List<SanitationRequest> getSRequests() {
    return SRequests;
  }

  public static List<ComputerRequest> getCRequests() {
    return CRequests;
  }

  public static void refreshRequests() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    try {
      Transaction tx = session.beginTransaction();
      Query q1 = session.createQuery("FROM GeneralRequest", GeneralRequest.class);
      List<GeneralRequest> allRequests = q1.list();
      Query q2 =
          session.createQuery(
              "FROM PatientTransportationRequest", PatientTransportationRequest.class);
      List<PatientTransportationRequest> PTRequests = q2.list();
      Query q3 = session.createQuery("FROM SanitationRequest", SanitationRequest.class);
      List<SanitationRequest> SRequests = q3.list();
      Query q4 = session.createQuery("FROM ComputerRequest", ComputerRequest.class);
      List<ComputerRequest> CRequests = q4.list();
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
