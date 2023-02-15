package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Database.NodeInfo;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
  @FXML AnchorPane map;
  @FXML AnchorPane anchor;
  @FXML MFXButton editNodeButton;
  @FXML MFXButton newNodeButton;
  @FXML MFXButton addMovesButton;
  @FXML MFXButton editLocationButton;
  @FXML private AnchorPane forms;
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  Map<Circle, Node> nodeMap;
  AnchorPane currentPopUp;
  private static Node currentNode;
  private Circle currentDot;
  private final int POP_UP_HEIGHT = 110;
  private GesturePane pane;
  private AnchorPane aPane = new AnchorPane();

  private static MapEditorController instance;
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

  @FXML MFXFilterComboBox<String> floorCombo;

  public void initialize() {
    moveMap = DBSession.getIDMoves(new Date(2023, 1, 1));
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
    nodeMap.clear();
    pane = new GesturePane();
    pane.setPrefHeight(536);
    pane.setPrefWidth(1089.6);
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    changeFloor("Lower Level 1", new javafx.geometry.Point2D(2220, 974));
    pane.setContent(aPane);
    map.getChildren().add(pane);
    // Changes floor when selecting a new floor
    floorCombo.setOnAction(
        e -> changeFloor(floorCombo.getValue(), pane.targetPointAtViewportCentre()));
    pane.zoomTo(-5000, -3000, new Point2D(2215, 1045));
  }

  private void changeFloor(String floor, Point2D p) {
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
        image = secondfloor;
        break;
      case "Third Floor":
        f = "3";
        image = thirdfloor;
        break;
    }
    aPane.getChildren().clear();
    aPane.getChildren().add(image);

    Map<String, Node> nodes = new HashMap<>();
    nodes.clear();
    nodes = DBSession.getAllNodes();

    for (Node node : nodes.values()) {
      if (node.getFloor().equals(f)) {
        Circle dot = placeNode(node);
        nodeMap.put(dot, node);
        List<Move> locations = DBSession.getMostRecentMoves(node.getNodeID());
        if (locations != null) for (Move move : locations) displayLoc(dot);
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
    Platform.runLater(() -> pane.centreOn(p));
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
    for (Move move : l) {
      Label loc = new Label(move.getLocationName().getLongName());
      loc.setFont(new Font("Arial", 6));
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
      currentNode = null;
      currentDot = null;
    }
  }

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
  }

  public void editLocationClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.LOCATION_EDITOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  public void addMoveClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.MOVE_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
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

  public void editNodeClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.SIDE_NODE_EDITOR.getFilename());
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
      id.setText("NodeID:   " + currentNode.getNodeID());
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
}
