package edu.wpi.teamb.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Entities.SessionGetter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class MapDAOTests {

  @Test
  public void addNodeTest1() {
    Node n1 = new Node();
    Node n2 = new Node();
    Node n3 = new Node();
    Node n4 = new Node();

    n1.setXCoord(5);
    n1.setYCoord(5);
    n1.setFloor("T1");
    n1.setBuilding("Testing1");
    n1.setNodeID(n1.buildID());

    n2.setXCoord(10);
    n2.setYCoord(10);
    n2.setFloor("T1");
    n2.setBuilding("Testing1");
    n2.setNodeID(n2.buildID());

    n3.setXCoord(15);
    n3.setYCoord(15);
    n3.setFloor("T2");
    n3.setBuilding("Testing2");
    n3.setNodeID(n3.buildID());

    n4.setXCoord(20);
    n4.setYCoord(20);
    n4.setFloor("T2");
    n4.setBuilding("Testing2");
    n4.setNodeID(n4.buildID());

    MapDAO.addNode(n1);
    MapDAO.addNode(n2);
    DBSession.addNode(n3);
    DBSession.addNode(n4);

    Node n1c = new Node();
    Node n2c = new Node();
    Node n3c = new Node();
    Node n4c = new Node();

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();

    n1c = s.get(Node.class, n1.buildID());
    n4c = s.get(Node.class, n4.buildID());

    s.close();

    boolean b1 =
        n1c.getNodeID().equals(n1.getNodeID()) && n1c.getBuilding().equals(n1.getBuilding());
    boolean b2 =
        n4c.getNodeID().equals(n4.getNodeID()) && n4c.getBuilding().equals(n4.getBuilding());

    assertTrue(b1 && b2);
  }

  @Test
  public void addLocationNameTest1() {
    LocationName ln1 = new LocationName();
    LocationName ln2 = new LocationName();

    ln1.setLongName("Add Test 1 LN1");
    ln1.setShortName("AT1 LN1");
    ln1.setLocationType("Test");

    ln2.setLongName("Add Test 1 LN2");
    ln2.setShortName("AT1 LN2");
    ln2.setLocationType("Test");

    MapDAO.addLocationName(ln1);
    DBSession.addLocationName(ln2);

    LocationName ln1c = new LocationName();
    LocationName ln2c = new LocationName();

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();

    ln1c = s.get(LocationName.class, ln1.getLongName());
    ln2c = s.get(LocationName.class, ln2.getLongName());

    s.close();

    boolean b1 =
        ln1c.getLongName().equals(ln1.getLongName())
            && ln1c.getShortName().equals(ln1.getShortName())
            && ln1c.getLocationType().equals(ln1.getLocationType());

    boolean b2 =
        ln2c.getLongName().equals(ln2.getLongName())
            && ln2c.getShortName().equals(ln2.getShortName())
            && ln2c.getLocationType().equals(ln2.getLocationType());

    assertTrue(b1 && b2);
  }

  @Test
  public void addEdgeTest1() {

    Node n1 = new Node();
    Node n2 = new Node();
    Node n3 = new Node();

    n1.setXCoord(5);
    n1.setYCoord(5);
    n1.setFloor("T1");
    n1.setBuilding("Testing1");
    n1.setNodeID(n1.buildID());

    n2.setXCoord(10);
    n2.setYCoord(10);
    n2.setFloor("T1");
    n2.setBuilding("Testing1");
    n2.setNodeID(n2.buildID());

    n3.setXCoord(15);
    n3.setYCoord(15);
    n3.setFloor("T2");
    n3.setBuilding("Testing2");
    n3.setNodeID(n3.buildID());

    Edge e1 = new Edge();
    e1.setNode1(n1);
    e1.setNode2(n2);

    Edge e2 = new Edge();
    e2.setNode1(n2);
    e2.setNode2(n3);

    MapDAO.addEdge(e1);
    DBSession.addEdge(e2);

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();

    Edge e1c = s.get(Edge.class, new Edge(n1, n2));
    Edge e2c = s.get(Edge.class, new Edge(n2, n3));

    s.close();

    boolean b1 =
        e1c.getNode1().getNodeID().equals(e1.getNode1().getNodeID())
            && e1c.getNode2().getNodeID().equals(e1.getNode2().getNodeID());

    boolean b2 =
        e2c.getNode1().getNodeID().equals(e2.getNode1().getNodeID())
            && e2c.getNode2().getNodeID().equals(e2.getNode2().getNodeID());

    assertTrue(b1 && b2);
  }

  @Test
  public void addMoveTest1() throws ParseException {

    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");

    Node n1 = new Node();
    Node n2 = new Node();

    n1.setXCoord(5);
    n1.setYCoord(5);
    n1.setFloor("T1");
    n1.setBuilding("Testing1");
    n1.setNodeID(n1.buildID());

    n2.setXCoord(10);
    n2.setYCoord(10);
    n2.setFloor("T1");
    n2.setBuilding("Testing1");
    n2.setNodeID(n2.buildID());

    LocationName ln1 = new LocationName();
    LocationName ln2 = new LocationName();

    ln1.setLongName("Add Test 1 LN1");
    ln1.setShortName("AT1 LN1");
    ln1.setLocationType("Test");

    ln2.setLongName("Add Test 1 LN2");
    ln2.setShortName("AT1 LN2");
    ln2.setLocationType("Test");

    Move m1 = new Move();
    m1.setNode(n1);
    m1.setLocationName(ln1);
    m1.setMoveDate(fmt.parse("2020-08-10"));

    Move m2 = new Move();
    m2.setNode(n2);
    m2.setLocationName(ln2);
    m2.setMoveDate(fmt.parse("2020-08-10"));

    MapDAO.addMove(m1);
    DBSession.addMove(m2);

    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session s = sf.openSession();

    Move m1c = s.get(Move.class, new Move(n1, ln1, fmt.parse("2020-08-10")));
    Move m2c = s.get(Move.class, new Move(n2, ln2, fmt.parse("2020-08-10")));

    s.close();

    boolean b1 =
        m1c.getNode().getNodeID().equals(m1.getNode().getNodeID())
            && m1c.getLocationName().getLongName().equals(m1.getLocationName().getLongName())
            && m1c.getMoveDate().equals(m1.getMoveDate());

    boolean b2 =
        m2c.getNode().getNodeID().equals(m2.getNode().getNodeID())
            && m2c.getLocationName().getLongName().equals(m2.getLocationName().getLongName())
            && m2c.getMoveDate().equals(m2.getMoveDate());

    assertTrue(b1 && b2);
  }
}
