package edu.wpi.teamb.Pathfinding;

import java.util.ArrayList;

public class DepthFirstPathfinder implements Pathfindable {

  public ArrayList<String> getShortestPath(String start, String end) {
    return Pathfinding.getPathBreadthDepth(start, end, false);
  }
}
