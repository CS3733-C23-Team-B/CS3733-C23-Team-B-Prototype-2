package edu.wpi.teamb.Pathfinding;

import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Node;
import java.sql.SQLException;
import java.util.*;

public class Pathfinding {
  private static List<Edge> edges;
  private static Map<String, Node> nodes;

  private static final boolean BREADTH = true;

  static {
    try {
      edges = Edge.getAll();
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Given an edge, evaluates the weight of the edge
   *
   * @param edge the edge to evaluate the weight of
   * @return the weight of the edge via Euclidean distance
   */
  private static double getWeight(Edge edge) {
    Node node1 = nodes.get(edge.getStartNode());
    Node node2 = nodes.get(edge.getEndNode());

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
    double x1 = node1.getXcoord();
    double x2 = node2.getXcoord();
    double y1 = node1.getYcoord();
    double y2 = node2.getYcoord();
    //    int f1 = Integer.parseInt(node1.getFloor().substring(1));
    //    int f2 = Integer.parseInt(node2.getFloor().substring(1));

    double dist = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    return dist;
  }

  /**
   * Generates a list of the nodes that can be reached directly from the given node
   *
   * @param node the node to generate paths from
   * @return a list of all nodes reachable via one edge
   */
  private static ArrayList<String> getDirectPaths(String node) {
    ArrayList<String> retList = new ArrayList<String>();
    for (Edge edge : edges) {
      if (edge.getStartNode().equals(node)) retList.add(edge.getEndNode());
      else if (edge.getEndNode().equals(node)) retList.add(edge.getStartNode());
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
    String retStr = "";

    for (String a : path) retStr += a + " -> ";

    retStr = retStr.substring(0, retStr.length() - 4);
    return retStr;
  }

  /**
   * Finds a path from start to end, by stringing together nodes and edges
   *
   * @param start the node to start from
   * @param end the node to end at
   * @return a String representation of the optimal path to take
   */
  public static String getShortestPath(String start, String end) {
    return getPathDepth(start, end);
  }

  /**
   * Finds a path from start to end using depth/breadth-first search
   *
   * @param start the node to start from
   * @param end the node to end at
   * @return a String representation of the path taken
   */
  private static String getPathDepth(String start, String end) {

    boolean done = false;
    HashMap<String, String> cameFrom = new HashMap<String, String>();

    ArrayList<String> toExpand = new ArrayList<String>();
    toExpand.add(start);

    while (toExpand.size() > 0) {

      // BREADTH is field that tells program to use breadth-first instead of depth-first
      int index = BREADTH ? 0 : toExpand.size() - 1;
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

    if (!done) return "PATH NOT FOUND";

    ArrayList<String> path = new ArrayList<>();
    path.add(start);

    String current = end;
    while (!current.equals(start)) {
      path.add(1, current);
      current = cameFrom.get(current);
    }

    return pathToString(path);
  }

  /**
   * Finds an optimal path from start to end using A* search
   *
   * @param start the node to start from
   * @param end the node to end at
   * @return a String representation of the path taken
   */
  private static String getPathAStar(String start, String end) {
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
    }

    return pathToString(path);
  }
}
