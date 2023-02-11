package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
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
  @FXML MFXComboBox<String> startLoc;
  @FXML MFXComboBox<String> endLoc;
  @FXML MFXButton pathfind;
  @FXML Label pathLabel;
  @FXML AnchorPane anchor;
  @FXML ImageView floor1;
  @FXML MFXFilterComboBox<String> floorCombo;

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
    pane = new GesturePane();
    pane.setPrefHeight(433);
    pane.setPrefWidth(800);
    changeFloor("Lower Level 1");
    pane.setContent(aPane);
    anchor.getChildren().add(pane);
    pane.zoomTo(-5000, -3000, Point2D.ZERO);
    floorCombo.setOnAction(e -> changeFloor(floorCombo.getValue()));
  }

  private void changeFloor(String floor) {
    ImageView image = new ImageView();
    String f = null;
    switch (floor) {
      case "Lower Level 2":
        f = "L2";
        image = lowerlevel2;
        break;
      case "Lower Level 1":
        f = "L1";
        image = lowerlevel;
        break;
      case "Ground Floor":
        f = "G";
        image = groundfloor;
        break;
      case "First Floor":
        f = "1";
        image = firstfloor;
        break;
      case "Second Floor":
        f = "2";
        image = seccondfloor;
        break;
      case "Third Floor":
        f = "3";
        image = thirdfloor;
        break;
    }
    aPane.getChildren().clear();
    aPane.getChildren().add(image);
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
    Map<String, Node> nodes = DBSession.getAllNodes();
    String finalF = f;
    nodes.forEach(
        (key, value) -> {
          if (value.getFloor().equals(finalF)) placeNode(value);
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
      if (!list.contains(move.getLongName()) && nodes.get(move.getNodeID()).getFloor().equals("L1"))
        list.add(move.getLongName());

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
