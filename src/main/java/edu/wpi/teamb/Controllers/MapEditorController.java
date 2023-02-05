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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MapEditorController {
  @FXML AnchorPane anchor;
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  Map<Circle, NodeInfo> nodeList;
  AnchorPane currentPopUp;
  private final int popUpHeight = 110;

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
            displayPopUp(newSelection);
          }
        });
  }

  public void displayPopUp(Circle dot) {
    clearPopUp();
    NodeInfo node = nodeList.get(dot);

    AnchorPane aPane = new AnchorPane();
    aPane.setTranslateX(dot.getCenterX() + dot.getRadius() * 2);
    aPane.setTranslateY(dot.getCenterY() - dot.getRadius() * 2 - popUpHeight);
    aPane.setStyle("-fx-background-color: FFFFFF; -fx-border-color: black;");

    VBox vbox = new VBox();
    aPane.getChildren().add(vbox);

    Text id = new Text("NodeID:   " + node.getNodeID());
    Text pos = new Text("(x, y):  " + "(" + node.getXCoord() + ", " + node.getYCoord() + ")");
    Text loc = new Text(node.getLocation());
    Button editButton = new Button("Edit");
    editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

    vbox.setSpacing(5);
    //    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(10, 10, 10, 10));

    vbox.getChildren().add(id);
    vbox.getChildren().add(pos);
    vbox.getChildren().add(loc);

    HBox hbox = new HBox();
    hbox.getChildren().add(editButton);
    hbox.setAlignment(Pos.CENTER);
    vbox.getChildren().add(hbox);

    anchor.getChildren().add(aPane);
    currentPopUp = aPane;
  }

  public void clearPopUp() {
    if (currentPopUp != null) {
      anchor.getChildren().remove(currentPopUp);
      currentPopUp = null;
    }
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
    clearPopUp();
  }
}
