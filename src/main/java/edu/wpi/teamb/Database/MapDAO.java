package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.*;

public class MapDAO {

    private static Map<String, Node> nodes = new HashMap<String, Node>();
    private static List<Edge> edges;
    private static Map<String, LocationName> locationNames = new HashMap<String, LocationName>();

    public static Map<String, Node> getNodes() {
        return nodes;
    }

    public static List<Edge> getEdges() {
        return edges;
    }

    public static Map<String, LocationName> getLocationNames() {return locationNames; }

    public static Map<String, Move> getIDMoves(Date d) {
        HashMap<String, Move> moves = new HashMap<String, Move>();
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        String hql = "FROM Move WHERE moveDate <= '" + d.toString() + "'";
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery(hql, Move.class);
            List<Move> ms = q.list();
            tx.commit();
            for (Move m : ms) { moves.put(m.getNode().getNodeID(), m);}
            return moves;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            return moves;
        }
    }

    public static Map<String, Move> getLNMoves(Date d) {
        HashMap<String, Move> moves = new HashMap<String, Move>();
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        String hql = "FROM Move WHERE moveDate <= '" + d.toString() + "'";
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery(hql, Move.class);
            List<Move> ms = q.list();
            tx.commit();
            for (Move m : ms) { moves.put(m.getLocationName().getLongName(), m);}
            return moves;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            return moves;
        }
    }

    public static void refreshNodes() {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Node", Node.class);
            List<Node> ns = q.list();
            tx.commit();
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
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void refreshLocationNames() {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM LocationName", LocationName.class);
            List<LocationName> lns = q.list();
            tx.commit();
            locationNames.clear();
            for (LocationName ln : lns) locationNames.put(ln.getLongName(), ln);
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
            refreshNodes();
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
            refreshNodes();
        }
    }

    public static void updateNode(Node n) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        String oldID = n.getNodeID();
        String newID = n.buildID();
        String hql = "UPDATE FROM Node SET nodeID = '" + newID + "' WHERE nodeID = '" + oldID + "'";
        try {
            Transaction tx = session.beginTransaction();
            session.merge(n);
            session.createQuery(hql, Node.class);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            refreshNodes();
        }
    }

    public static void addEdge(Edge ed) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(ed);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            refreshEdges();
        }
    }

    public static void deleteEdge(Edge ed) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.remove(ed);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            refreshEdges();
        }
    }

    public static void addLocationName(LocationName ln) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(ln);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            refreshLocationNames();
        }
    }

    public static void deleteLocationName(LocationName ln) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(ln);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            refreshLocationNames();
        }
    }

    public static void updateLocationName(LocationName newLN, LocationName oldLN) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        String oldLNName = oldLN.getLongName();
        String hql = "UPDATE FROM LocationName SET longName = '" + newLN.getLongName() +
                "', shortName = '" + newLN.getShortName() +
                "', locationType = '" + newLN.getLocationType() +
                "' WHERE longName = '" + oldLNName + "'";
        try {
            Transaction tx = session.beginTransaction();
            session.createQuery(hql, LocationName.class);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            refreshNodes();
        }
    }

    public static void addMove(Move m) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(m);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void deleteMove(Move m) {
        SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.remove(m);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
