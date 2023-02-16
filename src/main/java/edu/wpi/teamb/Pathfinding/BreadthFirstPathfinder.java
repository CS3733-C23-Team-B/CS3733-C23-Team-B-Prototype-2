package edu.wpi.teamb.Pathfinding;

import java.util.ArrayList;

public class BreadthFirstPathfinder implements Pathfindable {
  @Override
  public ArrayList<String> getShortestPath(String start, String end) {
    return Pathfinding.getPathBreadthDepth(start, end, true);
  }
}
