package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDAO {

    static Map<String, Node> nodes;
    static List<Edge> edges;

    public static Map<String, Node> getNodes() {
        return nodes;
    }

    public static List<Edge> getEdges() {
        return edges;
    }

    public static void refreshNodes() {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Node", Node.class);
            List<Node> ns = q.list();
            nodes.clear();
            for (Node node : ns) nodes.put(node.getNodeID(), node);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void refreshEdges() {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Edge", Edge.class);
            edges = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void addNode(Node n) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(n);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void deleteNode(Node n) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(n);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
