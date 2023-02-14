package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DatabaseRestore {

  public static ArrayList<String[]> read(File aFile) throws FileNotFoundException {
    ArrayList<String[]> all = new ArrayList<String[]>();
    Scanner sc = new Scanner(aFile);
    while (sc.hasNext()) {
      String line = sc.nextLine();
      String[] attr = line.split(",");
      all.add(attr);
    }
    return all;
  }

  public static void makeNodesLNMoves()
      throws URISyntaxException, FileNotFoundException, ParseException {

    HashSet<String> lns = new HashSet<String>();
    ArrayList<String[]> data =
        read(
            new File(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("edu/wpi/teamb/Iter2Data/nodes.csv")
                    .toURI()));

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();
    Transaction tx = s.beginTransaction();

    for (int i = 1; i < data.size(); i++) {
      String[] line = data.get(i);

      LocationName ln = new LocationName();
      Node n = new Node();
      Move m = new Move();

      n.setNodeID(line[0]);
      n.setXCoord(Integer.parseInt(line[1]));
      n.setYCoord(Integer.parseInt(line[2]));
      n.setFloor(line[3]);
      n.setBuilding(line[4]);

      s.persist(n);

      if (!lns.contains(line[6])) {
        lns.add(line[6]);
        ln.setLongName(line[6]);
        ln.setShortName(line[7]);
        ln.setLocationType(line[5]);
        s.persist(ln);
      }

      if (!(ln.getLongName() == null)) {
        m.setNode(n);
        m.setLocationName(ln);
        m.setMoveDate(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"));
        s.persist(m);
      }
    }

    tx.commit();
    s.close();
  }

  public static void makeEdges() throws URISyntaxException, FileNotFoundException {

    ArrayList<String[]> data =
        read(
            new File(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("edu/wpi/teamb/Iter2Data/edges.csv")
                    .toURI()));

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();
    Transaction tx = s.beginTransaction();

    for (int i = 1; i < data.size(); i++) {
      String[] line = data.get(i);
      Edge e = new Edge();
      Node n1 = (Node) s.get(Node.class, line[1]);
      Node n2 = (Node) s.get(Node.class, line[2]);
      e.setNode1(n1);
      e.setNode2(n2);
      s.persist(e);
    }

    tx.commit();
    s.close();
  }

  public static void updateNodeIDs() {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();
    Transaction tx = s.beginTransaction();
    List<Node> ns = s.createQuery("FROM Node", Node.class).list();
    for (Node n : ns) {
      String newID = n.buildID();
      String oldID = n.getNodeID();
      String hql = "UPDATE Node SET nodeID = '" + newID + "' WHERE nodeID = '" + oldID + "'";
      s.createQuery(hql).executeUpdate();
    }
    tx.commit();
    s.close();
  }

  public static void runRestore()
      throws FileNotFoundException, URISyntaxException, ParseException {

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();
    Transaction tx = s.beginTransaction();
    s.createQuery("DELETE FROM Edge").executeUpdate();
    s.createQuery("DELETE FROM Move").executeUpdate();
    s.createQuery("DELETE FROM Node").executeUpdate();
    s.createQuery("DELETE FROM LocationName").executeUpdate();
    tx.commit();
    s.close();
    makeNodesLNMoves();
    makeEdges();
    updateNodeIDs();
  }
}
