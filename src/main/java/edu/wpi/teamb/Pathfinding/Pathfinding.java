package edu.wpi.teamb.Pathfinding;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Pathfinding {
  private static List<Edge> edges = DBSession.getAllEdges();
  private static Map<String, Node> nodes = DBSession.getAllNodes();
  private static Date date;

  static {
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
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
        .map(nodeID -> movesID.get(nodeID).get(0).getLocationName().getLongName())
        .collect(Collectors.toList());
  }

  private static ArrayList<String> getPathBreadth(String startLoc, String endLoc) {
    return getPathBreadthDepth(startLoc, endLoc, true);
  }

  private static ArrayList<String> getPathDepth(String startLoc, String endLoc) {
    return getPathBreadthDepth(startLoc, endLoc, false);
  }

  public static String[] getPathDirections(List<String> nodeList) {
    String[] directions = {"", "", "", "", "", ""};
    List<String> locationList = nodesToLocations(nodeList);
    List<String> floors = Arrays.asList("L2", "L1", "G", "1", "2", "3");
    int index;
    String s;

    for (int i = 0; i < nodeList.size() - 1; i++) {
      index = floors.indexOf(nodes.get(nodeList.get(i)).getFloor());
      s = "";
      if (onSameFloor(nodeList.get(i), nodeList.get(i + 1))) {
        if (s.isEmpty()) s += "Go to " + locationList.get(i + 1) + ".";
        else s += "\nThen go to " + locationList.get(i + 1) + ".";
      } else
        for (int j = i; j < nodeList.size() - 1; j++) {
          if (true) System.out.println("TRUE");
        }
    }

    return directions;
  }

  private static boolean onSameFloor(String s1, String s2) {
    return nodes.get(s1).getFloor().equals(nodes.get(s2).getFloor());
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
    nodes = DBSession.getAllNodes();
  }

  public static Map<String, Move> getMovesLN() {
    return movesLN;
  }

  public static ArrayList<String> getPathFromID(String start, String end) {
    PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
    queue.add(new GraphNode(start, 0));

    HashMap<String, String> cameFrom = new HashMap<String, String>();
    HashMap<String, Double> costSoFar = new HashMap<String, Double>();
    cameFrom.put(start, null);
    costSoFar.put(start, 0.0);

    while (!queue.isEmpty()) {
      String current = queue.poll().getNodeID();

      if (current.equals(end)) break;

      for (String next : Pathfinding.getDirectPaths(current)) {
        double newCost = costSoFar.get(current) + Pathfinding.getWeight(current, next);
        if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
          costSoFar.put(next, newCost);
          double priority = newCost + Pathfinding.getWeight(end, next);
          queue.add(new GraphNode(next, priority));
          cameFrom.put(next, current);
        }
      }
    }

    ArrayList<String> path = new ArrayList<>();
    path.add(start);

    String current = end;
    while (!current.equals(start)) {
      path.add(1, current);
      current = cameFrom.get(current);
      if (current == null) return null;
    }
    return path;
  }
}
