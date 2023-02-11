package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import net.kurobako.gesturefx.GesturePane;

public class PathfindingController {
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private GesturePane pane;
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  private List<Line> lines;
  private AnchorPane aPane = new AnchorPane();
  private AnchorPane linesPlane = new AnchorPane();
  private final int POP_UP_HEIGHT = 110;
  Map<Circle, Node> nodeMap;
  AnchorPane currentPopUp;
  private static Node currentNode;
  private Circle currentDot;
  @FXML MFXFilterComboBox<String> startLoc;
  @FXML MFXFilterComboBox<String> endLoc;
  @FXML MFXButton pathfind;
  @FXML AnchorPane anchor;

  /** Initializes the dropdown menus */
  public void initialize() {
    nodeMap = new HashMap<>();
    nodeMap.clear();
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
          if (value.getFloor().equals("L1")) nodeMap.put(placeNode(value), value);
        });

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
    Platform.runLater(() -> pane.centreOn(new javafx.geometry.Point2D(2220, 974)));
  }

  public void displayPopUp(Circle dot) {
    clearPopUp();
    Node node = nodeMap.get(dot);

    AnchorPane popPane = new AnchorPane();
    popPane.setTranslateX(dot.getCenterX() + dot.getRadius() * 2);
    popPane.setTranslateY(dot.getCenterY() - dot.getRadius() * 2 - POP_UP_HEIGHT);
    popPane.setStyle("-fx-background-color: FFFFFF; -fx-border-color: black;");

    VBox vbox = new VBox();
    popPane.getChildren().add(vbox);

    Text id = new Text("NodeID:   " + node.getNodeID());
    Text pos = new Text("(x, y):  " + "(" + node.getXCoord() + ", " + node.getYCoord() + ")");

    Text loc = new Text(DBSession.getMostRecentLocation(node.getNodeID()));

    Button editButton = new Button("Create Path from Here");
    editButton.setStyle("-fx-background-color: #003AD6; -fx-text-fill: white;");
    editButton.setOnAction(
        (eventAction) -> {
          createPathFromNode();
        });
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

    aPane.getChildren().add(popPane);
    currentPopUp = popPane;
    currentNode = node;
    currentDot = dot;
  }

  private void clearPopUp() {
    if (currentPopUp != null) {
      aPane.getChildren().remove(currentPopUp);
      currentPopUp = null;
      currentNode = null;
      currentDot = null;
    }
  }

  public void createPathFromNode() {}

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() throws SQLException {
    linesPlane.getChildren().clear();
    String start = startLoc.getValue();
    String end = endLoc.getValue();
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
      if (!list.contains(move.getLongName()) && nodes.get(move.getNodeID()).getFloor().equals("L1"))
        list.add(move.getLongName());

    Sorting.quickSort(list);
    return list;
  }

  /**
   * Places all the nodes on the map
   *
   * @param node
   */
  private Circle placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Color.RED);
    aPane.getChildren().add(dot);
    dot.getStyleClass().add("intersection");
    dot.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          selectedCircle.set(dot);
        });
    return dot;
  }

  private void placeLine(Node start, Node end) {
    Line l = new Line(start.getXCoord(), start.getYCoord(), end.getXCoord(), end.getYCoord());
    l.setFill(Color.BLACK);
    l.setStrokeWidth(5);
    linesPlane.getChildren().add(l);
  }
}
