package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Controllers.Database.KioskEditController;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import edu.wpi.teamb.Pathfinding.SearchType;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;

public class KioskPopup {

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
  @FXML Label rightLoc;
  @FXML Label leftLoc;

  Map<Circle, Node> nodeMap;

  public void initialize() throws SQLException {
    List<KioskMove> kioskMoveList;
    kioskMoveList = DBSession.getAllKioskMoves();
    KioskLocation l = MapDAO.getKioskLocation();

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

    KioskMove k = KioskEditController.getInstance().getCurrentSelection();
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
    center.add(pane, 0, 0);
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    pane.toBack();
    moveMessage.setText(k.getMessage());
    frontLabel.setText(k.getLocationName().getLongName() + " is being moved");
    findPath(k.getPrevNode().getNodeID(), k.getNextNode().getNodeID());

    frontBox2.toFront();
    frontRight.toFront();
    frontLeft.toFront();
    frontCenter.toFront();
    Node pr = k.getPrevNode();
    Platform.runLater(
        () -> {
          pane.centreOn(new Point2D(pr.getXCoord(), pr.getYCoord()));
        });
  }

  private void findPath(String st, String en) {

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

    changeFloor(startNode.getFloor(), new Point2D(startNode.getXCoord(), startNode.getYCoord()));
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

  private void changeFloor(String floor, Point2D p) {
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
    frontBox2.toFront();
    frontRight.toFront();
    frontLeft.toFront();
    frontCenter.toFront();

    Platform.runLater(() -> pane.centreOn(p));
  }

  private Circle placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Bapp.blue);
    aPane.getChildren().add(dot);
    aPane.toFront();
    frontBox2.toFront();
    frontRight.toFront();
    frontLeft.toFront();
    frontCenter.toFront();
    if (buttonMap.get(node) != null) {
      showButton(buttonMap.get(node));
    }
    return dot;
  }

  public void backButtonClicked() {
    Popup.hidePopup(Screen.KIOSK_POPUP);
  }
}
