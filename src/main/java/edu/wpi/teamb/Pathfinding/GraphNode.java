package edu.wpi.teamb.Pathfinding;

public class GraphNode implements Comparable<GraphNode> {
  private String NodeID;
  private double priority;

  public GraphNode(String nodeID, double priority) {
    NodeID = nodeID;
    this.priority = priority;
  }

  @Override
  public int compareTo(GraphNode o) {
    return (int) (priority - o.priority);
  }

  public String getNodeID() {
    return NodeID;
  }
}
