package edu.wpi.teamb.Pathfinding;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Node;
import java.util.*;
import java.util.stream.Collectors;

public class Pathfinding {
  private static List<Edge> edges = DBSession.getAllEdges();
  private static Map<String, Node> nodes = DBSession.getAllNodes();
  private static int totalDist = 0;

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
  private static double getWeight(String n1, String n2) {
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
    int floorDist = Math.abs((floors.indexOf(f2) - floors.indexOf(f1))) * 150;

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
      if (edge.getNode1().equals(node)) retList.add(edge.getNode2());
      else if (edge.getNode2().equals(node)) retList.add(edge.getNode1());
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
    return path.stream().map(DBSession::getMostRecentLocation).collect(Collectors.toList());
  }

  /**
   * Finds a path from start to end, by stringing together nodes and edges
   *
   * @param start the longName of the location to start from
   * @param end the longName of the location to end at
   * @return a String representation of the optimal path to take
   */
  public static ArrayList<String> getShortestPath(String start, String end) {
    ArrayList<String> p = getPathAStar(start, end);
    System.out.println(pathToString(p));
    return p;
  }

  /**
   * Finds an optimal path from start to end using A* search
   *
   * @param startLoc the longName of the location to start from
   * @param endLoc the longName of the location to end at
   * @return a String representation of the path taken
   */
  private static ArrayList<String> getPathAStar(String startLoc, String endLoc) {
    String start = DBSession.getMostRecentNodeID(startLoc);
    String end = DBSession.getMostRecentNodeID(endLoc);

    PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
    queue.add(new GraphNode(start, 0));

    HashMap<String, String> cameFrom = new HashMap<String, String>();
    HashMap<String, Double> costSoFar = new HashMap<String, Double>();
    cameFrom.put(start, null);
    costSoFar.put(start, 0.0);

    while (!queue.isEmpty()) {
      String current = queue.poll().getNodeID();

      if (current.equals(end)) break;

      for (String next : getDirectPaths(current)) {
        double newCost = costSoFar.get(current) + getWeight(current, next);
        if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
          costSoFar.put(next, newCost);
          double priority = newCost + getWeight(end, next);
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

  /** Refreshes the node and edge fields from the database */
  public static void refreshData() {
    edges = DBSession.getAllEdges();
  }
}
