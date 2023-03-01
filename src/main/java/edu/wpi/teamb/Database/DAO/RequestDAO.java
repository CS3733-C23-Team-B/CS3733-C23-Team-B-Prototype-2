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
  private static List<MedicalEquipmentDeliveryRequest> MEDRequests = new ArrayList<>();
  private static List<MedicineDeliveryRequest> MDRequests = new ArrayList<>();
  private static List<FacilitiesRequest> FacRequests = new ArrayList<>();

  public static List<GeneralRequest> getAllRequests() {
    refreshRequests();
    return allRequests;
  }

  public static List<GeneralRequest> getAllRequests(String l) {
    List<GeneralRequest> rs = new ArrayList<GeneralRequest>();
    try {
      for (GeneralRequest r : allRequests) {
        if (r instanceof PatientTransportationRequest) {
          if (r.getLocation().equalsIgnoreCase(l)
              || ((PatientTransportationRequest) r)
                  .getPatientDestinationLocation()
                  .equalsIgnoreCase(l)) {
            rs.add(r);
          }
        } else {
          if (r.getLocation().equalsIgnoreCase(l)) {
            rs.add(r);
          }
        }
      }
    } catch (NullPointerException e) {
      return new ArrayList<>();
    }
    return rs;
  }

  public static List<PatientTransportationRequest> getAllPTRequests() {
    return PTRequests;
  }

  public static List<PatientTransportationRequest> getAllPTRequests(String l) {
    List<PatientTransportationRequest> rs = new ArrayList<PatientTransportationRequest>();
    for (PatientTransportationRequest r : PTRequests) {
      if (r.getPatientDestinationLocation().equalsIgnoreCase(l)
          || r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<SanitationRequest> getAllSanRequests() {
    return SanRequests;
  }

  public static List<SanitationRequest> getAllSanRequests(String l) {
    List<SanitationRequest> rs = new ArrayList<SanitationRequest>();
    for (SanitationRequest r : SanRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<SecurityRequest> getAllSecRequests(String l) {
    List<SecurityRequest> rs = new ArrayList<SecurityRequest>();
    for (SecurityRequest r : SecRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<SecurityRequest> getAllSecRequests() {
    return SecRequests;
  }

  public static List<ComputerRequest> getAllCRequests() {
    return CRequests;
  }

  public static List<ComputerRequest> getAllCRequests(String l) {
    List<ComputerRequest> rs = new ArrayList<ComputerRequest>();
    for (ComputerRequest r : CRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<AudioVideoRequest> getAllAVRequests() {
    return AVRequests;
  }

  public static List<AudioVideoRequest> getAllAVRequests(String l) {
    List<AudioVideoRequest> rs = new ArrayList<AudioVideoRequest>();
    for (AudioVideoRequest r : AVRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<MedicalEquipmentDeliveryRequest> getAllMEDRequests() {
    return MEDRequests;
  }

  public static List<MedicalEquipmentDeliveryRequest> getAllMEDRequests(String l) {
    List<MedicalEquipmentDeliveryRequest> rs = new ArrayList<MedicalEquipmentDeliveryRequest>();
    for (MedicalEquipmentDeliveryRequest r : MEDRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<MedicineDeliveryRequest> getAllMDRequests() {
    return MDRequests;
  }

  public static List<MedicineDeliveryRequest> getAllMDRequests(String l) {
    List<MedicineDeliveryRequest> rs = new ArrayList<MedicineDeliveryRequest>();
    for (MedicineDeliveryRequest r : MDRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
  }

  public static List<FacilitiesRequest> getAllFacRequests() {
    return FacRequests;
  }

  public static List<FacilitiesRequest> getAllFacRequests(String l) {
    List<FacilitiesRequest> rs = new ArrayList<FacilitiesRequest>();
    for (FacilitiesRequest r : FacRequests) {
      if (r.getLocation().equalsIgnoreCase(l)) {
        rs.add(r);
      }
    }
    return rs;
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
      Query q7 =
          session.createQuery(
              "FROM MedicalEquipmentDeliveryRequest", MedicalEquipmentDeliveryRequest.class);
      MEDRequests = q7.list();
      Query q8 =
          session.createQuery("FROM MedicineDeliveryRequest ", MedicineDeliveryRequest.class);
      MDRequests = q8.list();
      Query q9 = session.createQuery("FROM FacilitiesRequest", FacilitiesRequest.class);
      FacRequests = q9.list();
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
