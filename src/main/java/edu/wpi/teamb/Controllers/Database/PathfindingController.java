package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.*;
import io.github.palexdev.materialfx.controls.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Getter;
import net.kurobako.gesturefx.GesturePane;

public class PathfindingController {
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private GesturePane pane;

  @FXML GridPane map;
  @FXML MFXButton helpButton;
  @FXML MFXDatePicker datePicker;
  @FXML MFXFilterComboBox<String> floorCombo;
  @FXML MFXFilterComboBox searchType;
  @FXML MFXFilterComboBox<String> startLoc;
  @FXML MFXFilterComboBox<String> endLoc;
  @FXML MFXCheckbox avoidStairsCheckBox;
  @FXML MFXCheckbox showLocationsCheckBox;
  @FXML MFXButton pathfind;

  @FXML Pane frontFloorr;
  @FXML GridPane scrollPane;
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  private AnchorPane aPane = new AnchorPane();
  private AnchorPane linesPlane = new AnchorPane();
  private final int POP_UP_HEIGHT = 110;
  Map<Circle, Node> nodeMap;
  AnchorPane currentPopUp;
  private boolean pathNotFound = false;
  private boolean pathingByClick = false;
  private static Node currentNode;
  private Circle currentDot;
  private List<List<Node>> pathNodePairs = new ArrayList<>();
  private Map<String, List<Move>> moveMap;
  @FXML AnchorPane anchor;
  @FXML ImageView floor1;
  private List<Label> locLabels = new ArrayList<>();
  @FXML MFXFilterComboBox searchCombo;
  private String currentFloor;
  private String startID;
  private String endID;
  @FXML TextField pathNotFoundTextField;
  private List<String> floors = new ArrayList<>();
  private Map<String, String> floorMap = new HashMap<>();
  private Map<String, ImageView> imageMap = new HashMap<>();
  private Map<String, SearchType> searchTypeMap = new HashMap<>();
  Circle startDot;
  Circle endDot;
  private TextField textField;
  private HashMap<Node, MFXButton> buttonMap = new HashMap<>();
  List<Node> nodePath;
  @FXML Label timeLabel;
  @FXML Label dateLabel;
  @Getter @FXML private AnchorPane dir;
  @Getter @FXML private Pane forms;

  private String[] directions;
  private static PathfindingController instance;

  /** Initializes the dropdown menus */
  public void initialize() {
    instance = this;
    moveMap = DBSession.getIDMoves(new Date());
    Pathfinding.refreshData();
    Pathfinding.setDate(new Date());

    floorMap.put("Lower Level 2", "L2");
    floorMap.put("Lower Level 1", "L1");
    floorMap.put("Ground Floor", "G");
    floorMap.put("First Floor", "1");
    floorMap.put("Second Floor", "2");
    floorMap.put("Third Floor", "3");

    imageMap.put("L2", Bapp.lowerlevel2);
    imageMap.put("L1", Bapp.lowerlevel);
    imageMap.put("G", Bapp.groundfloor);
    imageMap.put("1", Bapp.firstfloor);
    imageMap.put("2", Bapp.secondfloor);
    imageMap.put("3", Bapp.thirdfloor);

    searchTypeMap.put("A* Search", SearchType.A_STAR);
    searchTypeMap.put("Breadth-first Search", SearchType.BREADTH_FIRST);
    searchTypeMap.put("Depth-first Search", SearchType.DEPTH_FIRST);

    floors.add("L2");
    floors.add("L1");
    floors.add("G");
    floors.add("1");
    floors.add("2");
    floors.add("3");

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
    pane.setPrefHeight(714);
    pane.setPrefWidth(1168);
    pane.setContent(aPane);
    pane.zoomTo(-5000, -3000, Point2D.ZERO);
    map.getChildren().add(pane);
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    floorCombo.setOnAction(
        e -> {
          changeFloor(floorMap.get(floorCombo.getValue()), pane.targetPointAtViewportCentre());
          boolean showLocations = showLocationsCheckBox.isSelected();
          for (Label loc : locLabels) {
            loc.setVisible(showLocations);
          }
        });

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    String formattedDate = currentDate.format(formatter);

    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0),
                event -> {
                  LocalDateTime currentTime = LocalDateTime.now();
                  DateTimeFormatter timefmt = DateTimeFormatter.ofPattern("h:mm a");
                  timeLabel.setText(currentTime.format(timefmt));
                }),
            new KeyFrame(Duration.seconds(1)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    dateLabel.setText(formattedDate);

    scrollPane.setVisible(false);

    Platform.runLater(
        () -> {
          changeFloor("L1", new javafx.geometry.Point2D(2215, 1045));
        });
  }

  public void setNodeColors() {
    if (pathNotFound) return;
    List<javafx.scene.Node> nodeTest = aPane.getChildren();
    for (javafx.scene.Node n : nodeTest) {
      if (n instanceof Circle) {
        Circle c = (Circle) n;
        Node node = nodeMap.get(c);
        if (node != null) {
          if (node.getNodeID().equals(startID)) {
            startDot = c;
            startDot.setFill(Color.GREEN);
          } else if (node.getNodeID().equals(endID)) {
            endDot = c;
            endDot.setFill(Color.RED);
            if (nodeMap != null) {
              updateTextFieldPosition(nodeMap.get(endDot));
            }
          }
        }
      }
    }
  }

  private void changeFloor(String floor, Point2D p) {
    currentFloor = floor;
    ImageView image;
    nodeMap.clear();

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

    for (Node value : nodes.values()) {
      if (value.getFloor().equals(currentFloor)) {
        Circle dot = placeNode(value);
        nodeMap.put(dot, value);
        displayLoc(dot);
      }
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
    if (directions != null)
      for (int i = 0; i < directions.length; i++) {
        for (Node value : nodes.values()) {
          if (value.getFloor().equals(currentFloor)) {
            AnchorPane dir = new AnchorPane();
            forms.getChildren().clear();

            dir.setPrefHeight(226);
            dir.setPrefWidth(290);

            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setPrefHeight(250);
            vbox.setPrefHeight(265);

            Label floorDirections = new Label(directions[floors.indexOf(currentFloor)]);

            floorDirections.setPrefHeight(250);
            floorDirections.setPrefWidth(265);

            HBox hbox = new HBox();
            hbox.getChildren().add(floorDirections);
            hbox.setAlignment(Pos.CENTER);

            vbox.getChildren().add(hbox);
            dir.getChildren().add(vbox);
            // vbox.getChildren().clear();
            System.out.println(i + ":\n" + directions[i]);
          }
        }
      }
    frontFloorr.toFront();
    scrollPane.toFront();
    Platform.runLater(() -> pane.centreOn(p));
  }

  private void drawLines() {
    for (List<Node> pair : pathNodePairs) {
      if (pair.get(0).getFloor().equals(currentFloor)
          && pair.get(1).getFloor().equals(currentFloor)) placeLine(pair.get(0), pair.get(1));
      if ((buttonMap.get(pair.get(1)) != null) && pair.get(1).getFloor().equals(currentFloor))
        showButton(buttonMap.get(pair.get(1)));
    }
  }

  public void displayPopUp(Circle dot) {
    clearPopUp();
    Node node = nodeMap.get(dot);

    if (node == null) return;

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

    Button startPathButton = new Button("Start Path Here");
    startPathButton.setStyle("-fx-background-color: #003AD6; -fx-text-fill: white;");
    startPathButton.setOnAction(
        (eventAction) -> {
          try {
            startPathFromHereClicked();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          clearPopUp();
        });

    HBox hbox = new HBox();
    hbox.getChildren().add(startPathButton);
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
    datePicker.setValue(LocalDate.now());
    LocalDate d = datePicker.getValue();
    ZoneId z = ZoneId.of("-05:00");
    LocalDateTime ldt = d.atTime(23, 59, 59, 999_000_000);
    ZonedDateTime zdt = ldt.atZone(z);
    Instant instant = zdt.toInstant();
    Date date = Date.from(instant);
    Pathfinding.setDate(date);
  }

  public void createPathFromNode() {}

  /** Finds the shortest path by calling the pathfinding method from Pathfinding */
  private void findPath() throws SQLException {
    pathNotFound = false;

    if (startDot != null) {
      startDot.setFill(Bapp.blue);
      startDot = null;
    }
    if (endDot != null) {
      endDot.setFill(Bapp.blue);
      endDot = null;
    }

    textField = null;
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
    directions = Pathfinding.getPathDirections(path);

    if (path == null) {
      System.out.println("PATH NOT FOUND");
      pathNotFoundTextField.setVisible(true);
      pathNotFound = true;
      pathNotFoundTextField.setStyle("-fx-text-fill: red; -fx-background-color:  #F2F2F2");
      return;
    }

    System.out.println(path);
    Map<String, Move> moves = Pathfinding.getMovesLN();
    startID = moves.get(start).getNode().getNodeID();
    endID = moves.get(end).getNode().getNodeID();
    Map<String, Node> nodes = DBSession.getAllNodes();

    pathNodePairs.clear();

    Node startNode = nodes.get(path.get(0));
    Node endNode = nodes.get(path.get(path.size() - 1));
    if (!currentFloor.equals(startNode.getFloor())) {
      changeFloor(startNode.getFloor(), new Point2D(startNode.getXCoord(), startNode.getYCoord()));
      pane.centreOn(new Point2D(startNode.getXCoord(), startNode.getYCoord()));
      floorMap.forEach(
          (key, value) -> {
            if (value.equals(startNode.getFloor())) floorCombo.setValue(key);
          });
    }

    List<Node> nodePath = new ArrayList<>();
    for (String s : path) {
      nodePath.add(nodes.get(s));
    }
    for (int i = 0; i < nodePath.size() - 1; i++) {
      String first = nodePath.get(i).getFloor();
      String second = nodePath.get(i + 1).getFloor();
      if (!first.equals(second)) {
        showFloorChangeOnNode(nodePath.get(i), nodePath.get(i + 1));
      }
    }

    for (int i = 0; i < path.size() - 1; i++) {
      Node s = nodes.get(path.get(i));
      Node e = nodes.get(path.get(i + 1));
      pathNodePairs.add(Arrays.asList(s, e));

      if (s.getFloor().equals(currentFloor) && e.getFloor().equals(currentFloor)) {
        placeLine(s, e);
      }
      if ((buttonMap.get(s) != null) && s.getFloor().equals(currentFloor))
        showButton(buttonMap.get(s));
    }
    pane.toFront();

    if (startDot != null) {
      startDot.setFill(Bapp.blue);
      startDot = null;
    }
    if (endDot != null) {
      endDot.setFill(Bapp.blue);
      endDot = null;
    }
    setNodeColors();
    // Update the text field position to be above the center of the path
    frontFloorr.toFront();
    scrollPane.toFront();
  }

  private void showButton(MFXButton button) {
    aPane.getChildren().add(button);
  }

  // at start node make a print out that lets user know that floor went up
  private void showFloorChangeOnNode(Node startNode, Node endNode) {
    MFXButton nextFloor = new MFXButton();
    nextFloor.setOnAction(
        e -> {
          changeFloor(endNode.getFloor(), new Point2D(endNode.getXCoord(), endNode.getYCoord()));
          floorMap.forEach(
              (key, value) -> {
                if (value.equals(endNode.getFloor())) floorCombo.setValue(key);
              });
        });
    nextFloor.setText("Go to next Floor");
    nextFloor.setStyle("-fx-background-color: #21357E; -fx-text-fill: #F2F2F2");
    nextFloor.setLayoutX(startNode.getXCoord() + 20);
    nextFloor.setLayoutY(startNode.getYCoord() - 20);
    buttonMap.put(startNode, nextFloor);
  }

  /**
   * Generates a list of the possible locations
   *
   * @return a list of the location longNames
   */
  static ObservableList<String> getLocations(String s) {
    ObservableList<String> list = FXCollections.observableArrayList();

    Map<String, Move> moves = DBSession.getLNMoves(new Date(123, 0, 1));

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
          if (pathingByClick) {
            Node n = nodeMap.get(dot);
            String ln = moveMap.get(n.getNodeID()).get(0).getLocationName().getLongName();
            endLoc.setValue(ln);
            pathingByClick = false;
            try {
              findPath();
              // scrollPane.setVisible(false);
            } catch (SQLException ex) {
              throw new RuntimeException(ex);
            }
          }
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
      if (move.getLocationName().getLocationType().equals("HALL")) continue;
      Label loc = new Label(move.getLocationName().getLongName());
      loc.setFont(new Font("Arial", 8));
      loc.setRotate(-45);
      vbox.getChildren().add(loc);
      loc.setVisible(false);
      locLabels.add(loc);
    }

    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 10, 10, 10));

    HBox hbox = new HBox();
    // hbox.getChildren().add(editButton);
    hbox.setAlignment(Pos.CENTER);
    vbox.getChildren().add(hbox);
    aPane.getChildren().add(popPane);
  }

  public void startPathFromHereClicked() throws IOException {

    pathingByClick = true;
    Node n = nodeMap.get(currentDot);
    String ln = moveMap.get(n.getNodeID()).get(0).getLocationName().getLongName();
    startLoc.setValue(ln);
    scrollPane.setVisible(true);

    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.CLICK_PATHFINDING_INSTRUCTION.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load())
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
    Line line = new Line(start.getXCoord(), start.getYCoord(), end.getXCoord(), end.getYCoord());
    line.setFill(Color.BLACK);
    line.setStrokeWidth(5);

    // Add the line to the scene graph and track the nodes that have been added
    linesPlane.getChildren().add(line);
  }

  private void updateTextFieldPosition(Node endNode) {
    double textFieldWidth = 10;
    double textFieldHeight = 10;
    double textFieldPadding = 10;
  }

  public void helpButtonClicked() {
    Popup.displayPopup(Screen.PATHFINDING_HELP_POP_UP);
  }

  public void searchCombo(ActionEvent actionEvent) {}

  public static PathfindingController getInstance() {
    return instance;
  }

  public void cancelPath() {
    pathingByClick = false;
    scrollPane.setVisible(false);
    handleClick();
    startLoc.clear();
    endLoc.clear();
  }
}
