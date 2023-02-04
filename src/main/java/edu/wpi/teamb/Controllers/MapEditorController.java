package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Database.NodeInfo;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapEditorController {
  @FXML AnchorPane anchor;
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  Map<Circle, NodeInfo> nodeList;
  Circle lastClicked;

  public void initialize() {
    nodeList = new HashMap<>();
    Map<String, Node> nodes;

    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    for (Node node : nodes.values()) {
      String nodeID = node.getID();
      int xCoord = node.getXCoord();
      int yCoord = node.getYCoord();
      Move move = Move.getMostRecentMove(nodeID);
      Date moveDate = move.getMoveDate();
      String location = move.getLongName();
      String edges = "";
      for (String edge : Pathfinding.getDirectPaths(nodeID)) edges += edge + ", ";
      edges = edges.substring(0, edges.length() - 2);
      NodeInfo nodeInfo =
          new NodeInfo(nodeID, xCoord, yCoord, location, moveDate, edges, node, move);
      Circle dot = placeNode(nodeInfo);
      nodeList.put(dot, nodeInfo);
    }

    selectedCircle.addListener(
        (obs, oldSelection, newSelection) -> {
          if (oldSelection != null) {
            oldSelection.pseudoClassStateChanged(SELECTED_P_C, false);
          }
          if (newSelection != null) {
            newSelection.pseudoClassStateChanged(SELECTED_P_C, true);
            System.out.println(nodeList.get(newSelection).getNodeID());
            lastClicked = newSelection;
          }
        });
  }

  private Circle placeNode(NodeInfo nodeInfo) {
    Circle dot = new Circle(scaleX(nodeInfo), scaleY(nodeInfo), 6, Color.RED);
    anchor.getChildren().add(dot);
    dot.getStyleClass().add("intersection");
    dot.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          selectedCircle.set(dot);
        });
    return dot;
  }

  private double scaleX(NodeInfo n) {
    double padding = 15;
    double xScaler = (2770 - 1630) / (800 - padding);
    return ((n.getXCoord() - 1637) / xScaler) + (padding / 2);
  }

  private double scaleY(NodeInfo n) {
    double padding = 15;
    double yScaler = (2260 - 799) / (380 - padding);
    return (((n.getYCoord() - 799) / yScaler) + (padding / 2)) + 150;
  }

  public void handleClick() {
    selectedCircle.set(null);
  }
}
