package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.kurobako.gesturefx.GesturePane;

public class PathfindingController {
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private GesturePane pane;

  @FXML AnchorPane map;

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
  private List<List<Node>> pathNodePairs = new ArrayList<>();
  private Map<String, List<Move>> moveMap;
  private ImageView lowerlevel =
      new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
  private ImageView groundfloor =
      new ImageView(getClass().getResource("/media/Maps/00_thegroundfloor.png").toExternalForm());
  private ImageView lowerlevel2 =
      new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel2.png").toExternalForm());
  private ImageView firstfloor =
      new ImageView(getClass().getResource("/media/Maps/01_thefirstfloor.png").toExternalForm());
  private ImageView secondfloor =
      new ImageView(getClass().getResource("/media/Maps/02_thesecondfloor.png").toExternalForm());
  private ImageView thirdfloor =
      new ImageView(getClass().getResource("/media/Maps/03_thethirdfloor.png").toExternalForm());
  @FXML MFXButton pathfind;
  @FXML AnchorPane anchor;
  @FXML ImageView floor1;
  @FXML MFXFilterComboBox<String> floorCombo;
  @FXML CheckBox avoidStairsCheckBox;
  @FXML MFXCheckbox showLocationsCheckBox;
  private List<Label> locLabels = new ArrayList<>();
  @FXML MFXFilterComboBox searchCombo;
  @FXML MFXDatePicker datePicker;
  private String currentFloor;
  private String startID;
  private String endID;
  @FXML TextField pathNotFoundTextField;
  private Map<String, String> floorMap = new HashMap<>();
  private Map<String, ImageView> imageMap = new HashMap<>();
  private Map<String, SearchType> searchTypeMap = new HashMap<>();
  Circle startDot;
  Circle endDot;

  /** Initializes the dropdown menus */
  public void initialize() {
    moveMap = DBSession.getIDMoves(new Date(2023, 1, 1));

    floorMap.put("Lower Level 2", "L2");
    floorMap.put("Lower Level 1", "L1");
    floorMap.put("Ground Floor", "G");
    floorMap.put("First Floor", "1");
    floorMap.put("Second Floor", "2");
    floorMap.put("Third Floor", "3");

    imageMap.put("L2", lowerlevel2);
    imageMap.put("L1", lowerlevel);
    imageMap.put("G", groundfloor);
    imageMap.put("1", firstfloor);
    imageMap.put("2", secondfloor);
    imageMap.put("3", thirdfloor);

    searchTypeMap.put("A* Search", SearchType.A_STAR);
    searchTypeMap.put("Breadth-first Search", SearchType.BREADTH_FIRST);
    searchTypeMap.put("Depth-first Search", SearchType.DEPTH_FIRST);

    floorCombo.setItems(
        FXCollections.observableArrayList(
            "Lower Level 2",
            "Lower Level 1",
            "Ground Floor",
            "First Floor",
            "Second Floor",
            "Third Floor"));

    searchCombo.setItems(
        FXCollections.observableArrayList(
            "A* Search", "Breadth-first Search", "Depth-first Search"));

    nodeMap = new HashMap<>();
    nodeMap.clear();
    pane = new GesturePane();
    pane.setPrefHeight(536);
    pane.setPrefWidth(1089.6);
    changeFloor("L1", new javafx.geometry.Point2D(2220, 974));
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    pane.setContent(aPane);
    map.getChildren().add(pane);
    pane.zoomTo(-5000, -3000, Point2D.ZERO);
    floorCombo.setOnAction(
        e -> changeFloor(floorMap.get(floorCombo.getValue()), pane.targetPointAtViewportCentre()));
  }

  public void setNodeColors() {
    List<javafx.scene.Node> nodeTest = aPane.getChildren();
    for (javafx.scene.Node n : nodeTest) {
      if (n instanceof Circle) {
        Circle c = (Circle) n;
        Node node = nodeMap.get(c);
        if (node.getNodeID().equals(startID)) {
          startDot = c;
          startDot.setFill(Color.GREEN);
        } else if (node.getNodeID().equals(endID)) {
          endDot = c;
          endDot.setFill(Color.RED);
        }
      }
    }
  }

  private void changeFloor(String floor, Point2D p) {
    ImageView image;
    nodeMap.clear();

    currentFloor = floor;
    image = imageMap.get(floor);

    image.toFront();
    image.setOnMouseClicked(
        e -> {
          handleClick();
          setNodeColors();
        });
    image.setOnMouseMoved(e -> setNodeColors());

    aPane.getChildren().clear();
    aPane.getChildren().add(image);
    aPane.getChildren().add(linesPlane);
    linesPlane.getChildren().clear();
    startLoc.setItems(getLocations(currentFloor));
    endLoc.setItems(getLocations(currentFloor));
    image.setOnMouseClicked(e -> handleClick());
    linesPlane.setOnMouseMoved(e -> setNodeColors());
    linesPlane.setOnMouseClicked(e -> handleClick());
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
        displayLoc(dot);
      }

    selectedCircle.addListener(
        (obs, oldSelection, newSelection) -> {
          if (oldSelection != null) {
            oldSelection.pseudoClassStateChanged(SELECTED_P_C, false);
            setNodeColors();
          }
          if (newSelection != null) {
            newSelection.pseudoClassStateChanged(SELECTED_P_C, true);
            displayPopUp(newSelection);
          }
        });
    drawLines();
    Platform.runLater(() -> pane.centreOn(p));

    if (startDot != null) {
      startDot.setFill(Color.BLUE);
      startDot = null;
    }
    if (endDot != null) {
      endDot.setFill(Color.BLUE);
      endDot = null;
    }
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
    vbox.setSpacing(5);
    //    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(10, 10, 10, 10));

    vbox.getChildren().add(id);
    vbox.getChildren().add(pos);
    vbox.getChildren().add(loc);

    HBox hbox = new HBox();
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

  public void dateEntered() {
    LocalDate d = datePicker.getValue();
    ZoneId z = ZoneId.of("-05:00");
    ZonedDateTime zdt = d.atStartOfDay(z);
    Instant instant = zdt.toInstant();
    Date date = Date.from(instant);
    Pathfinding.setDate(date);
  }

  public void createPathFromNode() {}

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() throws SQLException {
    pathNotFoundTextField.setVisible(false);
    Pathfinding.avoidStairs = avoidStairsCheckBox.isSelected();
    SearchType type = searchTypeMap.get(searchCombo.getText());

    linesPlane.getChildren().clear();
    String start = startLoc.getValue();
    String end = endLoc.getValue();

    Pathfindable pathfindable;
    if (type == SearchType.BREADTH_FIRST) pathfindable = new BreadthFirstPathfinder();
    else if (type == SearchType.DEPTH_FIRST) pathfindable = new DepthFirstPathfinder();
    else pathfindable = new AStarPathfinder();

    PathfindingContext pContext = new PathfindingContext(pathfindable);
    ArrayList<String> path = pContext.getShortestPath(start, end);

    if (path == null) {
      System.out.println("PATH NOT FOUND");
      pathNotFoundTextField.setVisible(true);
      pathNotFoundTextField.setStyle("-fx-text-fill: red; -fx-background-color:  #e0e0e0");
    }

    startID = DBSession.getMostRecentNodeID(start);
    endID = DBSession.getMostRecentNodeID(end);

    Map<String, Node> nodes = DBSession.getAllNodes();

    pathNodePairs.clear();

    Node startNode = nodes.get(path.get(0));
    if (!currentFloor.equals(startNode.getFloor())) {
      changeFloor(startNode.getFloor(), new Point2D(startNode.getXCoord(), startNode.getYCoord()));
      floorMap.forEach(
          (key, value) -> {
            if (value.equals(startNode.getFloor())) floorCombo.setValue(key);
          });
    }

    for (int i = 0; i < path.size() - 1; i++) {
      Node s = nodes.get(path.get(i));
      Node e = nodes.get(path.get(i + 1));
      pathNodePairs.add(Arrays.asList(s, e));

      if (s.getFloor().equals(currentFloor) && e.getFloor().equals(currentFloor)) placeLine(s, e);
    }
    pane.toFront();

    if (startDot != null) {
      startDot.setFill(Color.BLUE);
      startDot = null;
    }
    if (endDot != null) {
      endDot.setFill(Color.BLUE);
      endDot = null;
    }

    setNodeColors();
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

  public void displayLoc(Circle dot) {
    Node node = nodeMap.get(dot);
    AnchorPane popPane = new AnchorPane();
    popPane.setTranslateX(dot.getCenterX() + dot.getRadius() * 2 - 50);
    popPane.setTranslateY(dot.getCenterY() - dot.getRadius() * 2 + 38);

    VBox vbox = new VBox();
    popPane.getChildren().add(vbox);
    List<Move> l = moveMap.get(node.getNodeID());
    if (l == null) l = Arrays.asList();
    for (Move move : l) {
      Label loc = new Label(move.getLocationName().getLongName());
      loc.setFont(new Font("Arial", 8));
      loc.setRotate(-45);
      vbox.getChildren().add(loc);
      loc.setVisible(false);
      locLabels.add(loc);
    }

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    vbox.getChildren().add(hbox);
    aPane.getChildren().add(popPane);
  }

  public void showLocationsClicked() {

    showLocationsCheckBox.setOnAction(
        e -> {
          boolean showLocations = showLocationsCheckBox.isSelected();
          for (Label loc : locLabels) {
            loc.setVisible(showLocations);
          }
        });
  }

  private void placeLine(Node start, Node end) {
    Line l = new Line(start.getXCoord(), start.getYCoord(), end.getXCoord(), end.getYCoord());
    l.setFill(Color.BLACK);
    l.setStrokeWidth(5);
    linesPlane.getChildren().add(l);
  }
}
