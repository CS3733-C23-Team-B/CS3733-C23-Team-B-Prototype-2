package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.PatientTransportationRequest;
import edu.wpi.teamb.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DBSession {

    private static DBSession instance = null;

    private static HibernateUtil hu = new HibernateUtil();

    private DBSession() {};

    public static DBSession getInstance() {
        if(instance == null) {
            instance = new DBSession();
        }
        return instance;
    }

    public static void addTPRequest(PatientTransportationRequest ptr) {
        SessionFactory sf = hu.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(ptr);
            tx.commit();
        } catch (Exception e) {
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}
