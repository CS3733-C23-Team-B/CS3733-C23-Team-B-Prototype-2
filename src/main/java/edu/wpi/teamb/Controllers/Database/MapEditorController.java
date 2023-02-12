package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Database.NodeInfo;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorController {
  @FXML AnchorPane anchor;
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  Map<Circle, Node> nodeMap;
  AnchorPane currentPopUp;
  private static Node currentNode;
  private Circle currentDot;
  private final int popUpHeight = 110;
  private GesturePane pane;
  private AnchorPane aPane = new AnchorPane();
  private static MapEditorController instance;

  public void initialize() {
    instance = this;
    nodeMap = new HashMap<>();
    nodeMap.clear();
    Map<String, Node> nodes = new HashMap<>();

    ImageView i =
        new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
    pane = new GesturePane();
    pane.setPrefHeight(433);
    pane.setPrefWidth(800);
    aPane.getChildren().add(i);
    pane.setContent(aPane);
    anchor.getChildren().add(pane);
    i.setOnMouseClicked(e -> handleClick());

    pane.zoomTo(-5000, -3000, new Point2D(2215, 1045));
    nodes.clear();
    nodes = DBSession.getAllNodes();

    for (Node node : nodes.values()) {
      if (!node.getFloor().equals("L1")) continue;
      Circle dot = placeNode(node);
      nodeMap.put(dot, node);
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
    Platform.runLater(() -> pane.centreOn(new javafx.geometry.Point2D(2220, 974)));
  }

  public void displayPopUp(Circle dot) {
    clearPopUp();
    Node node = nodeMap.get(dot);

    AnchorPane popPane = new AnchorPane();
    popPane.setTranslateX(dot.getCenterX() + dot.getRadius() * 2);
    popPane.setTranslateY(dot.getCenterY() - dot.getRadius() * 2 - popUpHeight);
    popPane.setStyle("-fx-background-color: FFFFFF; -fx-border-color: black;");

    VBox vbox = new VBox();
    popPane.getChildren().add(vbox);

    Text id = new Text("NodeID:   " + node.getNodeID());
    Text pos = new Text("(x, y):  " + "(" + node.getXCoord() + ", " + node.getYCoord() + ")");

    List<Move> moves = DBSession.getMostRecentMoves(node.getNodeID());
    String t = moves.get(0).getLocationName().getLongName();
    if (moves.size() > 1) t += "\n" + moves.get(1).getLocationName().getLongName();
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

  public void locations() {
    Navigation.navigate(Screen.LOCATION_EDITOR);
  }

  public void newMove() {
    Navigation.navigate(Screen.MOVE_CREATOR);
  }

  public void home() {
    Navigation.navigate(Screen.HOME);
  }

  public void newNode() {
    Navigation.navigate(Screen.NODE_CREATOR);
  }

  static Node getCurrentNode() {
    return currentNode;
  }

  public void refreshPopUp() {
    if (!currentNode.getFloor().equals("L1")) {
      removeNode();
      return;
    }
    nodeMap.replace(currentDot, currentNode);
    ObservableList vboxChildren = ((VBox) (currentPopUp.getChildren().get(0))).getChildren();
    Text id = (Text) vboxChildren.get(0);
    Text pos = (Text) vboxChildren.get(1);
    Text loc = (Text) vboxChildren.get(2);
    id.setText("NodeID:   " + currentNode.getNodeID());
    pos.setText("(x, y):  " + "(" + currentNode.getXCoord() + ", " + currentNode.getYCoord() + ")");

    List<Move> moves = DBSession.getMostRecentMoves(currentNode.getNodeID());
    String t = moves.get(0).getLocationName().getLongName();
    if (moves.size() > 1) t += "\n" + moves.get(1).getLocationName().getLongName();
    loc.setText(t);

    currentDot.setCenterX(currentNode.getXCoord());
    currentDot.setCenterY(currentNode.getYCoord());
    currentPopUp.setTranslateX(currentDot.getCenterX() + currentDot.getRadius() * 2);
    currentPopUp.setTranslateY(currentDot.getCenterY() - currentDot.getRadius() * 2 - popUpHeight);
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
}
