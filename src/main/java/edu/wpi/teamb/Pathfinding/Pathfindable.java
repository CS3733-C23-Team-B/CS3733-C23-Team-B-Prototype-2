package edu.wpi.teamb.Pathfinding;

import java.util.ArrayList;

public interface Pathfindable {
  ArrayList<String> getShortestPath(String start, String end);
}
