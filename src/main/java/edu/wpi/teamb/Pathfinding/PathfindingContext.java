package edu.wpi.teamb.Pathfinding;

import java.util.ArrayList;

public class PathfindingContext {
  Pathfindable p;

  public PathfindingContext(Pathfindable p) {
    this.p = p;
  }

  public ArrayList<String> getShortestPath(String start, String end) {
    return p.getShortestPath(start, end);
  }
}
