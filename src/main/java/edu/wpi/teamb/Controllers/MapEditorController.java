package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Database.NodeInfo;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
  Map<Circle, NodeInfo> nodeList;
  AnchorPane currentPopUp;
  private static NodeInfo currentNode;
  private final int popUpHeight = 110;
  private GesturePane pane;
  private AnchorPane aPane = new AnchorPane();
  private static MapEditorController instance;

  public void initialize() {
    instance = this;
    nodeList = new HashMap<>();
    Map<String, Node> nodes;

    ImageView i =
        new ImageView(getClass().getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
    pane = new GesturePane();
    pane.setPrefHeight(433);
    pane.setPrefWidth(800);
    aPane.getChildren().add(i);
    pane.setContent(aPane);
    anchor.getChildren().add(pane);
    i.setOnMouseClicked(e -> handleClick());

    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    for (Node node : nodes.values()) {
      String nodeID = node.getID();
      int xCoord = node.getXCoord();
      int yCoord = node.getYCoord();
      Move move = Move.getMostRecentMove(nodeID);
      Date moveDate = move.getMoveDate();
      String location = move.getLongName();
      String floor = node.getFloor();
      String edges = "";
      for (String edge : Pathfinding.getDirectPaths(nodeID)) edges += edge + ", ";
      edges = edges.substring(0, edges.length() - 2);
      NodeInfo nodeInfo =
          new NodeInfo(nodeID, xCoord, yCoord, location, floor, moveDate, edges, node, move);
      Circle dot = placeNode(nodeInfo);
      nodeList.put(dot, nodeInfo);
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
  }

  public void displayPopUp(Circle dot) {
    clearPopUp();
    NodeInfo node = nodeList.get(dot);

    AnchorPane popPane = new AnchorPane();
    popPane.setTranslateX(dot.getCenterX() + dot.getRadius() * 2);
    popPane.setTranslateY(dot.getCenterY() - dot.getRadius() * 2 - popUpHeight);
    popPane.setStyle("-fx-background-color: FFFFFF; -fx-border-color: black;");

    VBox vbox = new VBox();
    popPane.getChildren().add(vbox);

    Text id = new Text("NodeID:   " + node.getNodeID());
    Text pos = new Text("(x, y):  " + "(" + node.getXCoord() + ", " + node.getYCoord() + ")");
    Text loc = new Text(node.getLocation());
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
    }
  }

  private Circle placeNode(NodeInfo nodeInfo) {
    Circle dot = new Circle(nodeInfo.getXCoord(), nodeInfo.getYCoord(), 6, Color.RED);
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

  static NodeInfo getCurrentNode() {
    return currentNode;
  }

  public void refreshPopUp() {
    ObservableList vboxChildren = ((VBox) (currentPopUp.getChildren().get(0))).getChildren();
    Text id = (Text) vboxChildren.get(0);
    Text pos = (Text) vboxChildren.get(1);
    Text loc = (Text) vboxChildren.get(2);
    id.setText("NodeID:   " + currentNode.getNodeID());
    pos.setText("(x, y):  " + "(" + currentNode.getXCoord() + ", " + currentNode.getYCoord() + ")");
    loc.setText(currentNode.getLocation());
  }

  public static MapEditorController getInstance() {
    return instance;
  }
}
