package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.KioskMove;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class KioskViewController {
  private String currentFloor;
  private AnchorPane aPane = new AnchorPane();
  private GesturePane pane;
  @FXML GridPane center;
  @FXML Label moveMessage;
  private Map<String, ImageView> imageMap = new HashMap<>();
  private Map<String, SearchType> searchTypeMap = new HashMap<>();
  private HashMap<Node, MFXButton> buttonMap = new HashMap<>();
  private AnchorPane linesPlane = new AnchorPane();
  private String startID;
  private String endID;
  private List<List<Node>> pathNodePairs = new ArrayList<>();
  private Map<String, Node> nodes = DBSession.getAllNodes();
  @FXML GridPane frontBox;
  @FXML Label frontLabel;
  Map<Circle, Node> nodeMap;

  public void initialize() throws IOException, SQLException {
    AtomicInteger index = new AtomicInteger(0);
    List<KioskMove> kioskMoveList;
    kioskMoveList = DBSession.getAllKioskMoves();
    moveMessage.setText(kioskMoveList.get(index.get()).getMessage());
    imageMap.put("L2", Bapp.lowerlevel2);
    imageMap.put("L1", Bapp.lowerlevel);
    imageMap.put("G", Bapp.groundfloor);
    imageMap.put("1", Bapp.firstfloor);
    imageMap.put("2", Bapp.secondfloor);
    imageMap.put("3", Bapp.thirdfloor);

    nodeMap = new HashMap<>();
    nodeMap.clear();
    pane = new GesturePane();
    pane.setPrefHeight(714);
    pane.setPrefWidth(1168);
    pane.setContent(aPane);
    pane.zoomTo(-5000, -3000, Point2D.ZERO);
    center.add(pane, 0, 0);
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    pane.toBack();
    frontBox.toFront();
    frontLabel.toFront();
    findPath(
        kioskMoveList.get(index.get()).getPrevNode().getNodeID(),
        kioskMoveList.get(index.get()).getNextNode().getNodeID());

    // Schedule a task to update the index and set the new message and image every 10 seconds
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(10),
                event -> {
                  index.getAndIncrement();
                  if (index.get() >= kioskMoveList.size()) {
                    index.set(0);
                  }
                  moveMessage.setText(kioskMoveList.get(index.get()).getMessage());
                  try {
                    findPath(
                        kioskMoveList.get(index.get()).getPrevNode().getNodeID(),
                        kioskMoveList.get(index.get()).getNextNode().getNodeID());
                  } catch (SQLException e) {
                    throw new RuntimeException(e);
                  }
                }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void changeFloor(String floor, Point2D p, ArrayList<String> path) {
    currentFloor = floor;
    nodeMap.clear();
    ImageView image = imageMap.get(floor);
    aPane.getChildren().clear();
    image.toFront();
    aPane.getChildren().add(image);
    aPane.getChildren().add(linesPlane);
    linesPlane.getChildren().clear();

    for (Node value : nodes.values()) {
      if (value.getFloor().equals(currentFloor) && path.contains(value.getNodeID())) {
        Circle dot = placeNode(value);
        nodeMap.put(dot, value);
      }
    }
    for (int i = 0; i < path.size() - 1; i++) {
      Node s = nodes.get(path.get(i));
      Node e = nodes.get(path.get(i + 1));
      if (s.getFloor().equals(currentFloor) && e.getFloor().equals(currentFloor)) {
        placeLine(s, e);
      }
    }
    aPane.toFront();
    frontBox.toFront();
    frontLabel.toFront();

    Platform.runLater(() -> pane.centreOn(p));
  }

  private Circle placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Bapp.blue);
    aPane.getChildren().add(dot);
    aPane.toFront();
    frontBox.toFront();
    frontLabel.toFront();
    if (buttonMap.get(node) != null) {
      System.out.println("Showing button with text: " + buttonMap.get(node).getText());
      showButton(buttonMap.get(node));
    }

    return dot;
  }

  public void openMap() throws IOException {
    final String filename = Screen.PATHFINDING.getFilename();
    final BorderPane rootPane = Bapp.getRootPane();
    final var r = Bapp.class.getResource(filename);
    final FXMLLoader loader = new FXMLLoader(r);
    final Parent root = loader.load();

    final String filename1 = Screen.SIGN_IN.getFilename();
    final var r1 = Bapp.class.getResource(filename1);
    final FXMLLoader loader1 = new FXMLLoader(r1);
    final Parent root1 = loader1.load();
    MFXButton b = new MFXButton();
    b.setText("Back to Sign In");

    Platform.runLater(
        () -> {
          rootPane.setCenter(root);
          rootPane.setTop(b);
        });

    b.setOnAction(
        e -> {
          Platform.runLater(
              () -> {
                rootPane.setCenter(null);
                rootPane.setTop(null);
                rootPane.setCenter(root1);
              });
        });
  }

  private void findPath(String st, String en) throws SQLException {

    Node startNode = nodes.get(st);

    Pathfinding.avoidStairs = true;

    linesPlane.getChildren().clear();
    String start = st;
    String end = en;

    ArrayList<String> path = Pathfinding.getPathFromID(start, end);

    if (path == null) {
      System.out.println("PATH NOT FOUND");
    }

    startID = DBSession.getMostRecentNodeID(start);
    endID = DBSession.getMostRecentNodeID(end);

    pathNodePairs.clear();

    List<Node> nodePath = new ArrayList<>();
    for (String s : path) {
      nodePath.add(nodes.get(s));
    }
    for (int i = 0; i < nodePath.size() - 1; i++) {
      String first = nodePath.get(i).getFloor();
      String second = nodePath.get(i + 1).getFloor();
      if (!first.equals(second)) {
        showFloorChangeOnNode(nodePath.get(i), nodePath.get(i + 1), path);
      }
    }
    for (int i = 0; i < path.size() - 1; i++) {
      Node s = nodes.get(path.get(i));
      Node e = nodes.get(path.get(i + 1));
      pathNodePairs.add(Arrays.asList(s, e));
    }
    pane.toFront();
    aPane.toFront();
    frontBox.toFront();
    frontLabel.toFront();

    Platform.runLater(
        () -> {
          changeFloor(
              startNode.getFloor(),
              new Point2D(startNode.getXCoord(), startNode.getYCoord()),
              path);
        });
  }

  private void showButton(MFXButton button) {
    aPane.getChildren().add(button);
  }

  private void placeLine(Node start, Node end) {
    Line line = new Line(start.getXCoord(), start.getYCoord(), end.getXCoord(), end.getYCoord());
    line.setFill(Color.BLACK);
    line.setStrokeWidth(5);
    // Add the line to the scene graph and track the nodes that have been added
    linesPlane.getChildren().add(line);
  }

  private void showFloorChangeOnNode(Node startNode, Node endNode, ArrayList<String> path) {
    MFXButton nextFloor = new MFXButton();
    nextFloor.setOnAction(
        e -> {
          changeFloor(
              endNode.getFloor(), new Point2D(endNode.getXCoord(), endNode.getYCoord()), path);
        });
    nextFloor.setText("Go to next Floor");
    nextFloor.setStyle("-fx-background-color: #21357E; -fx-text-fill: #F2F2F2");
    nextFloor.setLayoutX(startNode.getXCoord() + 20);
    nextFloor.setLayoutY(startNode.getYCoord() - 20);
    System.out.println("Go to Floor " + endNode.getFloor());
    buttonMap.put(startNode, nextFloor);
  }

  public void signIn() {
    Navigation.navigate(Screen.SIGN_IN);
  }
}
