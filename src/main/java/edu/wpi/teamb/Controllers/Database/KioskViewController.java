package edu.wpi.teamb.Controllers.Database;

import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Database.DAO.MapDAO;
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
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class KioskViewController {
  public HBox floorH;
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
  private ArrayList<String> path;
  private List<List<Node>> pathNodePairs = new ArrayList<>();
  private Map<String, Node> nodes = DBSession.getAllNodes();
  @FXML HBox frontRight;
  @FXML HBox frontLeft;
  @FXML HBox frontCenter;
  @FXML Label frontLabel;
  @FXML VBox frontBox2;
  @FXML Label floorLabel;
  @FXML Label rightLoc;
  @FXML Label leftLoc;
  Map<Circle, Node> nodeMap = new HashMap<>();
  private Map<String, String> floors = new HashMap<>();
  List<KioskMove> kioskMoveList;
  Timeline timeline;

  public void initialize() throws IOException, SQLException {
    AtomicInteger index = new AtomicInteger(0);
    kioskMoveList = DBSession.getAllKioskMoves();
    if (kioskMoveList.size() > 0) {

      KioskLocation l = MapDAO.getKioskLocation();

      if (l.getLocationName() != null) {
        Map<String, Move> moves = DBSession.getLNMoves(new Date());
        Node n = moves.get(l.getLocationName().getLongName()).getNode();
        List<String> direct = Pathfinding.getDirectPaths(n.getNodeID());
        Map<String, List<Move>> pathMoves = DBSession.getIDMoves(new Date());
        rightLoc.setText(l.getLocationName().getLongName());
        leftLoc.setText(l.getLocationName().getLongName());
        if (pathMoves != null && direct.size() > 1) {
          String l1 = pathMoves.get(direct.get(0)).get(0).getLocationName().getLongName();
          String l2 = pathMoves.get(direct.get(1)).get(0).getLocationName().getLongName();
          if (l.getRev()) {
            rightLoc.setText(l2);
            leftLoc.setText(l1);
          } else {
            rightLoc.setText(l1);
            leftLoc.setText(l2);
          }
        }
      }

      imageMap.put("L2", Bapp.lowerlevel2);
      imageMap.put("L1", Bapp.lowerlevel);
      imageMap.put("G", Bapp.groundfloor);
      imageMap.put("1", Bapp.firstfloor);
      imageMap.put("2", Bapp.secondfloor);
      imageMap.put("3", Bapp.thirdfloor);

      floors.put("L1", "Lower Level 1");
      floors.put("L2", "Lower Level 2");
      floors.put("G", "Ground Floor");
      floors.put("1", "First Floor");
      floors.put("2", "Second Floor");
      floors.put("3", "Third Floor");

      nodeMap = new HashMap<>();
      nodeMap.clear();
      pane = new GesturePane();
      pane.setPrefHeight(714);
      pane.setPrefWidth(1168);
      pane.setContent(aPane);
      center.add(pane, 0, 0);
      pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
      pane.toBack();
      moveMessage.setText(kioskMoveList.get(index.get()).getMessage());
      frontLabel.setText(
          kioskMoveList.get(index.get()).getLocationName().getLongName() + " is being moved");
      findPath(
          kioskMoveList.get(index.get()).getPrevNode().getNodeID(),
          kioskMoveList.get(index.get()).getNextNode().getNodeID());

      frontBox2.toFront();
      frontRight.toFront();
      frontLeft.toFront();
      floorH.toFront();
      frontCenter.toFront();

      // Set up the slide-in and slide-out animations
      TranslateTransition slideOut = new TranslateTransition(Duration.seconds(3), frontBox2);
      slideOut.setToY(-300);

      TranslateTransition slideIn = new TranslateTransition(Duration.seconds(3), frontBox2);
      slideIn.setToY(30);

      slideOut.setOnFinished(
          evt -> {
            if (index.get() >= kioskMoveList.size()) {
              index.set(0);
            }
            moveMessage.setText(kioskMoveList.get(index.get()).getMessage());
            frontLabel.setText(
                kioskMoveList.get(index.get()).getLocationName().getLongName() + " is being moved");
            try {
              findPath(
                  kioskMoveList.get(index.get()).getPrevNode().getNodeID(),
                  kioskMoveList.get(index.get()).getNextNode().getNodeID());
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
            Platform.runLater(
                () -> {
                  slideIn.play();
                });
          });

      // Schedule a task to update the index and slide in the new message and image every 10 seconds

      timeline =
          new Timeline(
              new KeyFrame(
                  Duration.seconds(10),
                  event -> {
                    index.getAndIncrement();
                    Platform.runLater(
                        () -> {
                          slideOut.play();
                        });
                  }));
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
      EventHandler<MouseEvent> mouseMoveHandler =
          event -> {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline
                .getKeyFrames()
                .add(
                    new KeyFrame(
                        Duration.seconds(10),
                        e -> {
                          index.getAndIncrement();
                          Platform.runLater(
                              () -> {
                                slideOut.play();
                              });
                        }));
            timeline.play();
          };
      aPane.setOnMouseMoved(mouseMoveHandler);

      Platform.runLater(
          () -> {
            changeFloorLater(kioskMoveList.get(index.get()).getPrevNode());
          });
    }
  }

  private void changeFloorLater(Node n) {
    Platform.runLater(
        () -> {
          changeFloor(n.getFloor(), new Point2D(n.getXCoord(), n.getYCoord()));
        });
  }

  private void changeFloor(String floor, Point2D p) {
    currentFloor = floor;
    nodeMap.clear();
    ImageView image = imageMap.get(floor);
    aPane.getChildren().clear();
    image.toFront();
    aPane.getChildren().add(image);
    aPane.getChildren().add(linesPlane);
    linesPlane.getChildren().clear();
    floorLabel.setText(floors.get(floor));
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
    frontBox2.toFront();
    frontRight.toFront();
    frontLeft.toFront();
    floorH.toFront();
    frontCenter.toFront();

    Platform.runLater(() -> pane.centreOn(p));
  }

  private Circle placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Bapp.blue);
    if (path.get(0).equals(node.getNodeID())) dot.setFill(Color.GREEN);
    else if (path.get(path.size() - 1).equals(node.getNodeID())) dot.setFill(Color.RED);
    else dot.setVisible(false);
    aPane.getChildren().add(dot);
    aPane.toFront();
    frontBox2.toFront();
    frontRight.toFront();
    frontLeft.toFront();
    floorH.toFront();
    frontCenter.toFront();
    if (buttonMap.get(node) != null) {
      showButton(buttonMap.get(node));
    }

    return dot;
  }

  public void openMap() throws IOException {
    if (!kioskMoveList.isEmpty()) {
      timeline.getKeyFrames().clear();
      timeline.stop();
      timeline = null;
    }
    final String filename = Screen.PATHFINDING.getFilename();
    final BorderPane rootPane = Bapp.getRootPane();
    final StackPane stackPane = Bapp.getStackPane();
    final var r = Bapp.class.getResource(filename);
    final FXMLLoader loader = new FXMLLoader(r);
    final Parent root = loader.load();

    final String filename1 = Screen.KIOSK_VIEW.getFilename();
    final var r1 = Bapp.class.getResource(filename1);
    final FXMLLoader loader1 = new FXMLLoader(r1);
    final Parent root1 = loader1.load();

    HBox h = new HBox();
    MFXButton b = new MFXButton();

    b.setText("Back to Kiosk");
    b.setFont(new Font("Nunito", 12));
    b.setTextFill(WHITE);
    b.setStyle("-fx-background-color: #E89F55; -fx-background-radius: 5; -fx-font-weight: bold");
    b.setPrefHeight(30);
    b.setPrefWidth(122);

    h.setAlignment(Pos.TOP_RIGHT);
    h.setPadding(new Insets(10, 10, 10, 10));
    h.setPickOnBounds(false);
    h.getChildren().add(b);

    Platform.runLater(
        () -> {
          rootPane.setCenter(root);
          // rootPane.setTop(h);
          stackPane.getChildren().add(h);
        });

    b.setOnAction(
        e -> {
          Platform.runLater(
              () -> {
                rootPane.setCenter(null);
                // rootPane.setTop(null);
                stackPane.getChildren().remove(1);
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

    path = Pathfinding.getPathFromID(start, end);

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
    frontBox2.toFront();
    frontCenter.toFront();
    frontRight.toFront();
    frontLeft.toFront();
    floorH.toFront();

    Platform.runLater(
        () -> {
          changeFloor(
              startNode.getFloor(), new Point2D(startNode.getXCoord(), startNode.getYCoord()));
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
          changeFloor(endNode.getFloor(), new Point2D(endNode.getXCoord(), endNode.getYCoord()));
        });
    nextFloor.setText("Go to next Floor");
    nextFloor.setStyle("-fx-background-color: #21357E; -fx-text-fill: #F2F2F2");
    nextFloor.setLayoutX(startNode.getXCoord() + 20);
    nextFloor.setLayoutY(startNode.getYCoord() - 20);
    buttonMap.put(startNode, nextFloor);
  }

  public void signIn() {
    if (!kioskMoveList.isEmpty()) {
      timeline.getKeyFrames().clear();
      timeline.stop();
      timeline = null;
    }
    Navigation.navigate(Screen.SIGN_IN);
  }
}
