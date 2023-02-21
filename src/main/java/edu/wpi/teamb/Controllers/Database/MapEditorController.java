package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorController {
  @FXML GridPane gridPane;
  @FXML GridPane map;
  @FXML AnchorPane anchor;
  @FXML MFXButton editNodeButton;
  @FXML MFXButton newNodeButton;
  @FXML MFXButton viewMovesButton;
  @FXML MFXButton editLocationButton;
  @FXML MFXButton newMoveButton;
  @FXML private AnchorPane forms;
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  Map<Circle, Node> nodeMap;
  AnchorPane currentPopUp;
  private static Node currentNode;
  private static Circle currentDot;
  private final int POP_UP_HEIGHT = 110;
  private GesturePane pane;
  private AnchorPane aPane;
  private double origX, origY;
  private boolean dragged;
  private boolean MOVING = false;
  private Circle edgeNode1, edgeNode2;
  private boolean creatingEdge;
  private static MapEditorController instance;
  private Map<String, List<Move>> moveMap;
  @FXML MFXFilterComboBox<String> floorCombo;

  public void initialize() {
    if (instance == null) {
      moveMap = DBSession.getIDMoves(new Date(2023, 1, 1));
    } else {
      moveMap = DBSession.getIDMoves();
    }
    instance = this;
    floorCombo.setItems(
        FXCollections.observableArrayList(
            "Lower Level 2",
            "Lower Level 1",
            "Ground Floor",
            "First Floor",
            "Second Floor",
            "Third Floor"));
    nodeMap = new HashMap<>();
    pane = new GesturePane();
    pane.setPrefHeight(714);
    pane.setPrefWidth(1168);
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    aPane = new AnchorPane();
    pane.setContent(aPane);
    map.getChildren().add(pane);
    // Changes floor when selecting a new floor
    floorCombo.setOnAction(
        e -> changeFloor(floorCombo.getValue(), pane.targetPointAtViewportCentre()));
    pane.zoomTo(-5000, -3000, Point2D.ZERO);
    Platform.runLater(
        () -> {
          changeFloor("Lower Level 1", new javafx.geometry.Point2D(2215, 1045));
        });
  }

  private void changeFloor(String floor, Point2D p) {
    ImageView image = new ImageView();
    String f = null;
    switch (floor) {
      case "Lower Level 2":
        f = "L2";
        image = Bapp.lowerlevel2;
        break;
      case "Lower Level 1":
        f = "L1";
        image = Bapp.lowerlevel;
        break;
      case "Ground Floor":
        f = "G";
        image = Bapp.groundfloor;
        break;
      case "First Floor":
        f = "1";
        image = Bapp.firstfloor;
        break;
      case "Second Floor":
        f = "2";
        image = Bapp.secondfloor;
        break;
      case "Third Floor":
        f = "3";
        image = Bapp.thirdfloor;
        break;
    }
    image.setOnMouseClicked(e -> handleClick());

    aPane.getChildren().clear();
    aPane.getChildren().add(image);

    Map<String, Node> nodes = DBSession.getAllNodes();

    for (Node node : nodes.values()) {
      if (node.getFloor().equals(f)) {
        Circle dot = placeNode(node);
        dot.setOnMouseClicked(
            e -> {
              if (currentDot != null) currentDot.setFill(Color.BLUE);
              displayPopUp(dot);
              dot.setFill(Color.GOLD);
              if (creatingEdge) {
                if (edgeNode1 == null) edgeNode1 = dot;
                else if (edgeNode2 == null && dot != edgeNode1) {
                  edgeNode2 = dot;
                  createEdge();
                }
              }
            });

        nodeMap.put(dot, node);
        displayLoc(dot);
      }
    }
    Platform.runLater(
        () -> {
          pane.centreOn(p);
        });
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

    List<Move> moves = DBSession.getMostRecentMoves(node.getNodeID());
    String t;
    if (moves == null) t = "NO MOVES";
    else {
      t = moves.get(0).getLocationName().getLongName();
      if (moves.size() > 1) t += "\n" + moves.get(1).getLocationName().getLongName();
    }
    Text loc = new Text(t);

    Button editButton = new Button("Edit");
    editButton.setStyle("-fx-background-color: #003AD6; -fx-text-fill: white;");
    editButton.setOnAction(
        (eventAction) -> {
          try {
            editClicked();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
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

  public void displayLoc(Circle dot) {
    Node node = nodeMap.get(dot);
    AnchorPane popPane = new AnchorPane();
    popPane.setTranslateX(dot.getCenterX() + dot.getRadius() * 2 - 25);
    popPane.setTranslateY(dot.getCenterY() - dot.getRadius() * 2 + 35);
    VBox vbox = new VBox();
    popPane.getChildren().add(vbox);
    List<Move> l = moveMap.get(node.getNodeID());
    if (l == null) l = Arrays.asList();
    for (Move move : l) {
      Label loc = new Label(move.getLocationName().getLongName());
      loc.setFont(new Font("Arial", 6));
      loc.setRotate(-45);
      vbox.getChildren().add(loc);
    }

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    vbox.getChildren().add(hbox);
    aPane.getChildren().add(popPane);
  }

  private void editClicked() throws IOException {
    Stage newWindow = new Stage();
    final String filename = Screen.NODE_EDITOR.getFilename();
    try {
      final var resource = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      Scene scene = new Scene(loader.load(), 400, 350);
      newWindow.setScene(scene);
      newWindow.show();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  private void clearPopUp() {
    if (currentPopUp != null) {
      aPane.getChildren().remove(currentPopUp);
      currentPopUp = null;
      if (currentDot != null) currentDot.setFill(Color.BLUE);
      currentNode = null;
      currentDot = null;
    }
  }

  public Circle placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Color.RED);
    aPane.getChildren().add(dot);
    dot.getStyleClass().add("intersection");
    dot.setCursor(Cursor.HAND);

    dot.setOnMousePressed(
        (e) -> {
          origX = e.getSceneX();
          origY = e.getSceneY();
          currentDot = dot;

          pane.setGestureEnabled(false);

          Circle c = (Circle) (e.getSource());
          c.toFront();
        });

    dot.setOnMouseReleased(
        e -> {
          pane.setGestureEnabled(true);
          if (dragged) updateNode(dot);
          dragged = false;
        });

    dot.setOnMouseDragged(
        (e) -> {
          double offsetX = (e.getSceneX() - origX) / pane.getCurrentScaleX();
          double offsetY = (e.getSceneY() - origY) / pane.getCurrentScaleY();
          Circle c = (Circle) (e.getSource());
          c.setCenterX(c.getCenterX() + offsetX);
          c.setCenterY(c.getCenterY() + offsetY);
          origX = e.getSceneX();
          origY = e.getSceneY();
          dragged = true;
        });

    return dot;
  }

  public void updateNode(Circle dot) {
    Node node = nodeMap.get(dot);
    node.setXCoord((int) dot.getCenterX());
    node.setYCoord((int) dot.getCenterY());
    DBSession.updateNode(node);
    node.setNodeID(node.buildID());
    currentNode = node;
    currentDot = dot;
    refreshPopUp();
  }

  private double scaleX(NodeInfo n) {
    double padding = 2000;
    double xScalar = (2770 - 1630) / (5000 - padding);
    return ((n.getXCoord() - 1637) / xScalar) + (padding / 2);
  }

  private double scaleY(NodeInfo n) {
    double padding = 1000;
    double yScalar = (2260 - 799) / (3400 - padding);
    return (((n.getYCoord() - 799) / yScalar) + (padding / 2));
  }

  public void handleClick() {
    selectedCircle.set(null);
    clearPopUp();
    if (edgeNode1 != null) {
      edgeNode1.setFill(Color.GOLD);
    }
  }

  public void editLocationClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.LOCATION_EDITOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  public void viewMovesClicked() throws IOException {
    Stage newWindow = new Stage();
    final String filename = Screen.FUTURE_MOVES.getFilename();
    try {
      final var resource = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      Scene scene = new Scene(loader.load(), 800, 650);
      newWindow.setScene(scene);
      newWindow.show();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  public void home() {
    Navigation.navigate(Screen.HOME);
  }

  public void newNodeClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.NODE_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  public void newEdgeClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.EDGE_CLICK_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
    creatingEdge = true;
  }

  public void cancelClickEdge() {
    if (edgeNode1 != null) {
      edgeNode1.setFill(Color.BLUE);
      edgeNode1 = null;
    }
    if (edgeNode2 != null) {
      edgeNode2.setFill(Color.BLUE);
      edgeNode2 = null;
    }
    clearForm();
  }

  public void clearForm() {
    forms.getChildren().clear();
  }

  public void createEdge() {
    if (edgeNode1 == null || edgeNode2 == null) return;
    Edge e = new Edge();
    e.setNode1(nodeMap.get(edgeNode1));
    e.setNode2(nodeMap.get(edgeNode2));
    DBSession.addEdge(e);
    cancelClickEdge();
    creatingEdge = false;
  }

  public void editNodeClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.SIDE_NODE_EDITOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  public void newMovesClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.MOVE_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  //  public void viewMovesClicked() throws IOException {
  //    forms.getChildren().clear();
  //    final var res = Bapp.class.getResource(Screen.FUTURE_MOVES.getFilename());
  //    final FXMLLoader loader = new FXMLLoader(res);
  //    forms.getChildren().add(loader.load());
  //  }

  public void newLocationClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.LOCATION_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  static Node getCurrentNode() {
    return currentNode;
  }

  public void refreshPopUp() {
    if (!currentNode.getFloor().equals("L1")) {
      removeNode();
      return;
    }

    currentDot.setCenterX(currentNode.getXCoord());
    currentDot.setCenterY(currentNode.getYCoord());
    nodeMap.replace(currentDot, currentNode);

    if (currentPopUp != null) {
      ObservableList vboxChildren = ((VBox) (currentPopUp.getChildren().get(0))).getChildren();
      Text id = (Text) vboxChildren.get(0);
      Text pos = (Text) vboxChildren.get(1);
      Text loc = (Text) vboxChildren.get(2);
      id.setText("NodeID:   " + currentNode.buildID());
      pos.setText(
          "(x, y):  " + "(" + currentNode.getXCoord() + ", " + currentNode.getYCoord() + ")");

      List<Move> moves = DBSession.getMostRecentMoves(currentNode.getNodeID());
      String t;
      if (moves != null) {
        t = moves.get(0).getLocationName().getLongName();
        if (moves.size() > 1) t += "\n" + moves.get(1).getLocationName().getLongName();
      } else t = "NO MOVES";

      loc.setText(t);
      currentPopUp.setTranslateX(currentDot.getCenterX() + currentDot.getRadius() * 2);
      currentPopUp.setTranslateY(
          currentDot.getCenterY() - currentDot.getRadius() * 2 - POP_UP_HEIGHT);
    }
  }

  public void removeNode() {
    aPane.getChildren().remove(currentDot);
    nodeMap.remove(currentDot);
    clearPopUp();
  }

  public static MapEditorController getInstance() {
    return instance;
  }

  public static void setCurrentNode(Node currentNode) {
    MapEditorController.currentNode = currentNode;
  }

  public Circle getDot(Node n) {
    Circle c;
    for (Map.Entry<Circle, Node> entry : nodeMap.entrySet()) {
      Node value = entry.getValue();
      if (value.equals(n)) return entry.getKey();
    }
    return null;
  }

  public void setCurrentDot(Circle dot) {
    currentDot = dot;
  }

  public static Circle getCurrentDot() {
    return currentDot;
  }

  public static void promptEdgeRepair(Node node) {
    Pathfinding.refreshData();
    List<String> nodes = Pathfinding.getDirectPaths(node.getNodeID());
    Map<String, Node> allNodes = DBSession.getAllNodes();
    if (nodes.size() != 2) return;
    EdgeRepairController.setNodes(allNodes.get(nodes.get(0)), allNodes.get(nodes.get(1)));
    Popup.displayPopup(Screen.EDGE_REPAIR);
  }

  public void helpButtonClicked() {
    Navigation.navigate(Screen.MAINHELP);
  }
}
