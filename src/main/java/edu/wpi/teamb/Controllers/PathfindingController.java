package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;

public class PathfindingController {
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");

  private GesturePane pane;

  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  private List<Line> lines;
  private AnchorPane aPane = new AnchorPane();
  private AnchorPane linesPlane = new AnchorPane();
  @FXML ChoiceBox startLoc;
  @FXML ChoiceBox endLoc;
  @FXML Button pathfind;
  @FXML Label pathLabel;
  @FXML AnchorPane anchor;
  @FXML ImageView floor1;

  /** Initializes the dropdown menus */
  public void initialize() {
    ImageView i =
        new ImageView(getClass().getResource("/media/Maps/01_thefirstfloor.png").toExternalForm());
    pane = new GesturePane();
    pane.setPrefHeight(433);
    pane.setPrefWidth(800);
    aPane.getChildren().add(i);
    pane.setContent(aPane);
    anchor.getChildren().add(pane);
    aPane.getChildren().add(linesPlane);

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

    selectedCircle.addListener(
        (obs, oldSelection, newSelection) -> {
          if (oldSelection != null) {
            oldSelection.pseudoClassStateChanged(SELECTED_P_C, false);
          }
          if (newSelection != null) {
            newSelection.pseudoClassStateChanged(SELECTED_P_C, true);
          }
        });
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
    pane.toFront();
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
    Circle dot = new Circle(scaleX(node) + 75, scaleY(node) - 50, 6, Color.RED);
    dot.getStyleClass().add("intersection");
    dot.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          selectedCircle.set(dot);
        });
    aPane.getChildren().add(dot);
  }

  private void placeLine(Node start, Node end) {
    Line l = new Line(scaleX(start) + 75, scaleY(start) - 50, scaleX(end) + 75, scaleY(end) - 50);
    l.setFill(Color.BLACK);
    linesPlane.getChildren().add(l);
  }

  private double scaleX(Node n) {
    double padding = 2000;
    double xScalar = (2770 - 1630) / (5000 - padding);
    return ((n.getXCoord() - 1637) / xScalar) + (padding / 2);
  }

  private double scaleY(Node n) {
    double padding = 1000;
    double yScalar = (2260 - 799) / (3400 - padding);
    return (((n.getYCoord() - 799) / yScalar) + (padding / 2));
  }
}
