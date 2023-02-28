package edu.wpi.teamb.Pathfinding;

import edu.wpi.teamb.Database.Move;
import java.util.*;

public class AStarPathfinder implements Pathfindable {
  @Override
  public ArrayList<String> getShortestPath(String startLoc, String endLoc) {

    Map<String, Move> moves = Pathfinding.getMovesLN();
    Move startMove = moves.get(startLoc);
    Move endMove = moves.get(endLoc);

    if (startMove == null || endMove == null) {
      return null;
    }

    String start = startMove.getNode().getNodeID();
    String end = endMove.getNode().getNodeID();

    return Pathfinding.getPathFromID(start, end);
  }
}
