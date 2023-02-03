package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PathfindingController {

  @FXML ChoiceBox startLoc;
  @FXML ChoiceBox endLoc;
  @FXML Button pathfind;
  @FXML Label pathLabel;
  @FXML AnchorPane anchor;

  /** Initializes the dropdown menus */
  public void initialize() {
    startLoc.setItems(getLocations());
    endLoc.setItems(getLocations());
    pathfind.setOnAction((eventAction) -> findPath());
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    nodes.forEach((key, value) -> placeNode(value));
  }

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() {
    String start = (String) startLoc.getValue();
    String end = (String) endLoc.getValue();
    String path = Pathfinding.getShortestPath(start, end);
    pathLabel.setText("Path: " + path);
  }

  /**
   * Generates a list of the possible locations
   *
   * @return a list of the location longNames
   */
  static ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();

    List<Move> moves;
    try {
      moves = Move.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    for (Move move : moves) if (!list.contains(move.getLongName())) list.add(move.getLongName());

    return list;
  }

  private void placeNode(Node node) {
    double padding = 15;
    double xScaler = (2770 - 1630) / (800 - padding);
    double xPlot = ((node.getXcoord() - 1637) / xScaler) + (padding / 2);
    double yScaler = (2260 - 799) / (380 - padding);
    double yPlot = ((node.getYcoord() - 799) / yScaler) + (padding / 2);

    Circle dot = new Circle(xPlot, yPlot + 150, 6, Color.RED);
    anchor.getChildren().add(dot);
  }
}
