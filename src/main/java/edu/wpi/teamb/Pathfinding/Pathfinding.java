package edu.wpi.teamb.Pathfinding;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Pathfinding {
  private static List<Edge> edges = DBSession.getAllEdges();
  private static Map<String, Node> nodes = DBSession.getAllNodes();
  private static Date date;

  static {
    try {
      date = new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01");
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private static Map<String, List<Move>> movesID = DBSession.getIDMoves(date);
  private static Map<String, Move> movesLN = DBSession.getLNMoves(date);

  private static int totalDist = 0;
  public static boolean avoidStairs = false;

  public static void setDate(Date d) {
    date = d;
    movesID = DBSession.getIDMoves(d);
    movesLN = DBSession.getLNMoves(d);
  }

  /**
   * Given an edge, evaluates the weight of the edge
   *
   * @param edge the edge to evaluate the weight of
   * @return the weight of the edge via Euclidean distance
   */
  private static double getWeight(Edge edge) {
    Node node1 = nodes.get(edge.getNode1());
    Node node2 = nodes.get(edge.getNode2());

    return getDist(node1, node2);
  }

  /**
   * Given two nodes, evaluates the weight of the edge between the two
   *
   * @param n1 start node
   * @param n2 end node
   * @return the Euclidean distance between the two nodes
   */
  static double getWeight(String n1, String n2) {
    Node node1 = nodes.get(n1);
    Node node2 = nodes.get(n2);

    return getDist(node1, node2);
  }

  /**
   * Calculates the Euclidean distance between two nodes
   *
   * @param node1 start node
   * @param node2 end node
   */
  private static double getDist(Node node1, Node node2) {

    ArrayList<String> floors = new ArrayList<>();
    Collections.addAll(floors, "L2", "L1", "1", "2", "3");

    double x1 = node1.getXCoord();
    double x2 = node2.getXCoord();
    double y1 = node1.getYCoord();
    double y2 = node2.getYCoord();
    String f1 = node1.getFloor();
    String f2 = node2.getFloor();

    int kFloor;

    if (!movesID.containsKey(node1.getNodeID())) kFloor = 0;
    else {
      if (avoidStairs)
        kFloor =
            movesID.get(node1.getNodeID()).get(0).getLocationName().getLocationType().equals("ELEV")
                ? 150
                : 999999;
      else
        kFloor =
            movesID.get(node1.getNodeID()).get(0).getLocationName().getLocationType().equals("ELEV")
                ? 150
                : 250;
    }

    int floorDist = Math.abs((floors.indexOf(f2) - floors.indexOf(f1))) * kFloor;

    double dist = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) + floorDist;
    return dist;
  }

  /**
   * Generates a list of the nodes that can be reached directly from the given node
   *
   * @param node the node to generate paths from
   * @return a list of all nodes reachable via one edge
   */
  public static ArrayList<String> getDirectPaths(String node) {
    ArrayList<String> retList = new ArrayList<String>();
    for (Edge edge : edges) {
      if (edge.getNode1().getNodeID().equals(node)) retList.add(edge.getNode2().getNodeID());
      if (edge.getNode2().getNodeID().equals(node)) retList.add(edge.getNode1().getNodeID());
    }
    return retList;
  }

  /**
   * Converts a graph traversal path from a list to a String
   *
   * @param path List of nodes traversed in order
   * @return a String representation of the path taken
   */
  private static String pathToString(List<String> path) {
    //    path = nodesToLocations(path);
    totalDist = 0;
    if (path == null) return "PATH NOT FOUND";
    for (int i = 0; i < path.size() - 1; i++) totalDist += getWeight(path.get(i), path.get(i + 1));
    System.out.println("Total dist: " + totalDist);

    String retStr = "";

    for (String a : path) retStr += a + " -> ";

    retStr = retStr.substring(0, retStr.length() - 4);
    return retStr;
  }

  /**
   * Takes a list of NodeIDs and converts to a list of associated locations
   *
   * @param path the list of NodeID's
   * @return the list of locationLongNames associated with each NodeID
   */
  private static List<String> nodesToLocations(List<String> path) {
    return path.stream()
        .map(nodeID -> DBSession.getMostRecentMoves(nodeID).get(0).getLocationName().getLongName())
        .collect(Collectors.toList());
  }

  private static ArrayList<String> getPathBreadth(String startLoc, String endLoc) {
    return getPathBreadthDepth(startLoc, endLoc, true);
  }

  private static ArrayList<String> getPathDepth(String startLoc, String endLoc) {
    return getPathBreadthDepth(startLoc, endLoc, false);
  }

  static ArrayList<String> getPathBreadthDepth(String startLoc, String endLoc, boolean breadth) {

    Map<String, Move> moves = Pathfinding.getMovesLN();
    Move startMove = moves.get(startLoc);
    Move endMove = moves.get(endLoc);

    if (startMove == null || endMove == null) {
      return null;
    }

    String start = startMove.getNode().getNodeID();
    String end = endMove.getNode().getNodeID();
    //////

    boolean done = false;
    HashMap<String, String> cameFrom = new HashMap<String, String>();

    ArrayList<String> toExpand = new ArrayList<String>();
    toExpand.add(start);

    while (toExpand.size() > 0) {

      // BREADTH is field that tells program to use breadth-first instead of depth-first
      int index = breadth ? 0 : toExpand.size() - 1;
      String current = toExpand.remove(index);

      ArrayList<String> directPaths = getDirectPaths(current);
      for (String path : directPaths) {
        if (path.equals(end)) {
          cameFrom.put(path, current);
          done = true;
          break;
        }
        if (!cameFrom.containsKey(path)) {
          cameFrom.put(path, current);
          toExpand.add(path);
        }
      }

      if (done) break;
    }

    if (!done) return null;

    ArrayList<String> path = new ArrayList<>();
    path.add(start);

    String current = end;
    while (!current.equals(start)) {
      path.add(1, current);
      current = cameFrom.get(current);
    }

    return path;
  }

  /** Refreshes the node and edge fields from the database */
  public static void refreshData() {
    edges = DBSession.getAllEdges();
  }

  public static Map<String, Move> getMovesLN() {
    return movesLN;
  }
}
