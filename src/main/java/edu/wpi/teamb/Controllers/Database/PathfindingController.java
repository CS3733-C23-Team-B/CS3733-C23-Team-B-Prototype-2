package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.*;
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
  private AnchorPane aPane = new AnchorPane();
  private AnchorPane linesPlane = new AnchorPane();
  private final int POP_UP_HEIGHT = 110;
  Map<Circle, Node> nodeMap;
  AnchorPane currentPopUp;
  private static Node currentNode;
  private Circle currentDot;
  @FXML MFXFilterComboBox<String> startLoc;
  @FXML MFXFilterComboBox<String> endLoc;
  private Circle startDot = null;
  private Circle endDot = null;
  private List<List<Node>> pathNodePairs = new ArrayList<>();
  private ImageView lowerlevel =
      new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
  private ImageView groundfloor =
      new ImageView(getClass().getResource("/media/Maps/00_thegroundfloor.png").toExternalForm());
  private ImageView lowerlevel2 =
      new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel2.png").toExternalForm());
  private ImageView firstfloor =
      new ImageView(getClass().getResource("/media/Maps/01_thefirstfloor.png").toExternalForm());
  private ImageView seccondfloor =
      new ImageView(getClass().getResource("/media/Maps/02_thesecondfloor.png").toExternalForm());
  private ImageView thirdfloor =
      new ImageView(getClass().getResource("/media/Maps/03_thethirdfloor.png").toExternalForm());
  @FXML MFXButton pathfind;
  @FXML AnchorPane anchor;
  @FXML ImageView floor1;
  @FXML MFXFilterComboBox<String> floorCombo;
  private String currentFloor;
  private String startID;
  private String endID;

  /** Initializes the dropdown menus */
  public void initialize() {
    floorCombo.setItems(
        FXCollections.observableArrayList(
            "Lower Level 2",
            "Lower Level 1",
            "Ground Floor",
            "First Floor",
            "Second Floor",
            "Third Floor"));
    nodeMap = new HashMap<>();
    nodeMap.clear();
    pane = new GesturePane();
    pane.setPrefHeight(433);
    pane.setPrefWidth(800);
    changeFloor("Lower Level 1", new javafx.geometry.Point2D(2220, 974));
    pane.setContent(aPane);
    anchor.getChildren().add(pane);
    pane.zoomTo(-5000, -3000, Point2D.ZERO);
    floorCombo.setOnAction(
        e -> changeFloor(floorCombo.getValue(), pane.targetPointAtViewportCentre()));
  }

  private void changeFloor(String floor, Point2D p) {
    ImageView image = new ImageView();
    switch (floor) {
      case "Lower Level 2":
        currentFloor = "L2";
        image = lowerlevel2;
        break;
      case "Lower Level 1":
        currentFloor = "L1";
        image = lowerlevel;
        break;
      case "Ground Floor":
        currentFloor = "G";
        image = groundfloor;
        break;
      case "First Floor":
        currentFloor = "1";
        image = firstfloor;
        break;
      case "Second Floor":
        currentFloor = "2";
        image = seccondfloor;
        break;
      case "Third Floor":
        currentFloor = "3";
        image = thirdfloor;
        break;
    }
    aPane.getChildren().clear();
    aPane.getChildren().add(image);
    aPane.getChildren().add(linesPlane);
    linesPlane.getChildren().clear();
    startLoc.setItems(getLocations(currentFloor));
    endLoc.setItems(getLocations(currentFloor));
    image.setOnMouseClicked(e -> handleClick());
    pathfind.setOnAction(
        (eventAction) -> {
          try {
            findPath();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    Map<String, Node> nodes = DBSession.getAllNodes();

    for (Node value : nodes.values())
      if (value.getFloor().equals(currentFloor)) {
        Circle dot = placeNode(value);
        nodeMap.put(dot, value);
        if (value.getNodeID().equals(startID)) {
          startDot = dot;
          startDot.setFill(Color.GREEN);
        } else if (value.getNodeID().equals(endID)) {
          endDot = dot;
          endDot.setFill(Color.RED);
        }
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
    drawLines();
    Platform.runLater(() -> pane.centreOn(p));
  }

  private void drawLines() {
    for (List<Node> pair : pathNodePairs) {
      if (pair.get(0).getFloor().equals(currentFloor)
          && pair.get(1).getFloor().equals(currentFloor)) placeLine(pair.get(0), pair.get(1));
    }
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

    List<Move> m = DBSession.getMostRecentMoves(node.getNodeID());
    Text loc = new Text();
    if (m == null) loc.setText("NO MOVES");
    else for (Move move : m) loc.setText(move.getLocationName().getLongName());

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

  public void handleClick() {
    selectedCircle.set(null);
    clearPopUp();
  }

  public void createPathFromNode() {}

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() throws SQLException {
    if (startDot != null) startDot.setFill(Color.BLUE);
    if (endDot != null) endDot.setFill(Color.BLUE);

    linesPlane.getChildren().clear();
    String start = startLoc.getValue();
    String end = endLoc.getValue();
    ArrayList<String> path = Pathfinding.getShortestPath(start, end);

    startID = DBSession.getMostRecentNodeID(start);
    endID = DBSession.getMostRecentNodeID(end);

    for (Map.Entry<Circle, Node> entry : nodeMap.entrySet()) {
      Circle circle = entry.getKey();
      Node node = entry.getValue();
      if (node.getNodeID().equals(startID)) startDot = circle;
      if (node.getNodeID().equals(endID)) endDot = circle;
    }

    if (startDot != null) startDot.setFill(Color.GREEN);
    if (endDot != null) endDot.setFill(Color.RED);

    Map<String, Node> nodes = DBSession.getAllNodes();

    pathNodePairs.clear();

    for (int i = 0; i < path.size() - 1; i++) {
      Node s = nodes.get(path.get(i));
      Node e = nodes.get(path.get(i + 1));
      pathNodePairs.add(Arrays.asList(s, e));
      if (s.getFloor().equals(currentFloor) && e.getFloor().equals(currentFloor)) placeLine(s, e);
    }
    pane.toFront();
  }

  /**
   * Generates a list of the possible locations
   *
   * @return a list of the location longNames
   */
  static ObservableList<String> getLocations(String s) {
    ObservableList<String> list = FXCollections.observableArrayList();

    Map<String, Move> moves = DBSession.getLNMoves(new Date(2023, 1, 1));

    for (Move move : moves.values())
      if (!list.contains(move.getLocationName().getLongName()))
        list.add(move.getLocationName().getLongName());

    //    for (Move move : moves.values())
    //      if (!list.contains(move.getLocationName().getLongName())
    //          && move.getNode().getFloor().equals(s))
    // list.add(move.getLocationName().getLongName());

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
