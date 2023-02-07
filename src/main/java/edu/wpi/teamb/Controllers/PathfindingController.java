package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
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
        new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
    pane = new GesturePane();
    pane.setPrefHeight(433);
    pane.setPrefWidth(800);
    aPane.getChildren().add(i);
    pane.setContent(aPane);
    anchor.getChildren().add(pane);
    aPane.getChildren().add(linesPlane);

    pane.zoomTo(-5000, -3000, Point2D.ZERO);
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
    List<Node> nodes = DBSession.getAllNodes();
    nodes.forEach(
        value -> {
          if (value.getFloor().equals("L1")) placeNode(value);
        });

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

    List<Node> nodeDBList = DBSession.getAllNodes();
    Map<String, Node> nodes = new HashMap<>();
    nodeDBList.forEach(node -> nodes.put(node.getNodeID(), node));

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

    List<Move> moves = DBSession.getAllMoves();

    for (Move move : moves) if (!list.contains(move.getLongName())) list.add(move.getLongName());

    return list;
  }

  /**
   * Places all the nodes on the map
   *
   * @param node
   */
  private void placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Color.RED);
    dot.getStyleClass().add("intersection");
    dot.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          selectedCircle.set(dot);
        });
    aPane.getChildren().add(dot);
  }

  private void placeLine(Node start, Node end) {
    Line l = new Line(start.getXCoord(), start.getYCoord(), end.getXCoord(), end.getYCoord());
    l.setFill(Color.BLACK);
    linesPlane.getChildren().add(l);
  }
}
