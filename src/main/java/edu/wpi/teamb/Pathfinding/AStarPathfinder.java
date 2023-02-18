package edu.wpi.teamb.Pathfinding;

import edu.wpi.teamb.Database.Move;
import java.util.*;

public class AStarPathfinder implements Pathfindable {
  @Override
  public ArrayList<String> getShortestPath(String startLoc, String endLoc) {
    //    String start = DBSession.getMostRecentNodeID(startLoc);
    //    String end = DBSession.getMostRecentNodeID(endLoc);

    Map<String, Move> moves = Pathfinding.getMovesLN();
    Move startMove = moves.get(startLoc);
    Move endMove = moves.get(endLoc);

    if (startMove == null || endMove == null) {
      return null;
    }

    String start = startMove.getNode().getNodeID();
    String end = endMove.getNode().getNodeID();

    // END HERE

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
