package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.SessionGetter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
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

      Node n = new Node();
      n.setNodeID(line[0]);
      n.setXCoord(Integer.parseInt(line[1]));
      n.setYCoord(Integer.parseInt(line[2]));
      n.setFloor(line[3]);
      n.setBuilding(line[4]);

      if (!lns.contains(line[6])) {
        lns.add(line[6]);
        LocationName ln = new LocationName();
        ln.setLongName(line[6]);
        ln.setShortName(line[7]);
        ln.setLocationType(line[5]);
        s.persist(ln);

        Move m = new Move();
        m.setNode(n);
        m.setLocationName(ln);
        m.setMoveDate(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"));
        s.persist(m);
      }
      s.persist(n);
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
      Object n1 = s.get("Node", line[1]);
      Object n2 = s.get("Node", line[2]);
      e.setNode1((Node) n1);
      e.setNode2((Node) n2);
      s.persist(e);
    }

    tx.commit();
    s.close();
  }

  public static void main(String[] args)
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
  }
}
