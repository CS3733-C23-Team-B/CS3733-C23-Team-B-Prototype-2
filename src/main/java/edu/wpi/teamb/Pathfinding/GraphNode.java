package edu.wpi.teamb.Pathfinding;

public class GraphNode implements Comparable<GraphNode> {
    private String NodeID;
    private double priority;

    public GraphNode(String nodeID, double priority) {
        NodeID = nodeID;
        this.priority = priority;
    }

    /**
     * Compares the current node's priority to another node's priority
     *
     * @param o the object to be compared.
     * @return >1 is this priority is higher, <1 if this priority is lower, 0 if equal
     */
    @Override
    public int compareTo(GraphNode o) {
        return (int) (priority - o.priority);
    }

    public String getNodeID() {
        return NodeID;
    }
}
