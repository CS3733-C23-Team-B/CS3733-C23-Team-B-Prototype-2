package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginDAO {
    private static Map<String, Node> logins = new HashMap<String, Node>();
    public static Map<String, Node> getAllLogins() {
        refreshLogins();
        return logins;
    }

    public static void refreshLogins() {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Login ", Login.class);
            List<Node> ns = q.list();
            tx.commit();
            logins.clear();
            for (Node node : ns) logins.put(node.getNodeID(), node);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
