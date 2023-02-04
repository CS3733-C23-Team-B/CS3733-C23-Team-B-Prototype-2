package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.shape.Line;

public class PathfindingController {

  private List<Line> lines;
  @FXML ChoiceBox startLoc;
  @FXML ChoiceBox endLoc;
  @FXML Button pathfind;
  @FXML Label pathLabel;
  @FXML AnchorPane anchor;
  @FXML AnchorPane linesPlane;

  /** Initializes the dropdown menus */
  public void initialize() {
    startLoc.setItems(getLocations());
    endLoc.setItems(getLocations());
    pathfind.setOnAction(
        (eventAction) -> {
          try {
            findPath();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    nodes.forEach((key, value) -> placeNode(value));
  }

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() throws SQLException {
    linesPlane.getChildren().clear();
    String start = (String) startLoc.getValue();
    String end = (String) endLoc.getValue();
    ArrayList<String> path = Pathfinding.getShortestPath(start, end);
    Map<String, Node> nodes = Node.getAll();
    for (int i = 0; i < path.size() - 1; i++) {
      String s = path.get(i);
      String e = path.get(i + 1);
      placeLine(nodes.get(s), nodes.get(e));
    }
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

  /**
   * Places all the nodes on the map
   *
   * @param node
   */
  private void placeNode(Node node) {
    Circle dot = new Circle(scaleX(node), scaleY(node), 6, Color.RED);
    anchor.getChildren().add(dot);
  }

  private void placeLine(Node start, Node end) {
    Line l = new Line(scaleX(start), scaleY(start), scaleX(end), scaleY(end));
    l.setFill(Color.BLACK);
    linesPlane.getChildren().add(l);
  }

  private double scaleX(Node n) {
    double padding = 15;
    double xScaler = (2770 - 1630) / (800 - padding);
    return ((n.getXCoord() - 1637) / xScaler) + (padding / 2);
  }

  private double scaleY(Node n) {
    double padding = 15;
    double yScaler = (2260 - 799) / (380 - padding);
    return (((n.getYCoord() - 799) / yScaler) + (padding / 2)) + 150;
  }
}
