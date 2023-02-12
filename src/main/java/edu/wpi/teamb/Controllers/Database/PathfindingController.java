package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
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
  @FXML MFXComboBox<String> startLoc;
  @FXML MFXComboBox<String> endLoc;
  @FXML MFXButton pathfind;
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
    Map<String, Node> nodes = DBSession.getAllNodes();
    nodes.forEach(
        (key, value) -> {
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
    Platform.runLater(() -> pane.centreOn(new javafx.geometry.Point2D(2220, 974)));
  }

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() throws SQLException {
    linesPlane.getChildren().clear();
    String start = (String) startLoc.getValue();
    String end = (String) endLoc.getValue();
    ArrayList<String> path = Pathfinding.getShortestPath(start, end);

    Map<String, Node> nodes = DBSession.getAllNodes();

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

    Map<String, Node> nodes = DBSession.getAllNodes();
    List<Move> moves = DBSession.getAllMoves();

    for (Move move : moves)
      if (!list.contains(move.getLocationName().getLongName())
          && nodes.get(move.getNode()).getFloor().equals("L1")) list.add(move.getLocationName().getLongName());

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
    l.setStrokeWidth(5);
    linesPlane.getChildren().add(l);
  }
}
