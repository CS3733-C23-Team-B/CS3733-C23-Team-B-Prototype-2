package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.SearchType;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.shape.Circle;
import net.kurobako.gesturefx.GesturePane;

public class KioskViewController {
  private String currentFloor;
  private AnchorPane aPane = new AnchorPane();
  private GesturePane pane;
  @FXML GridPane center;
  @FXML Label messageLabel;
  private Map<String, String> floorMap = new HashMap<>();
  private Map<String, ImageView> imageMap = new HashMap<>();
  private Map<String, SearchType> searchTypeMap = new HashMap<>();
  private HashMap<Node, MFXButton> buttonMap = new HashMap<>();
  private AnchorPane linesPlane = new AnchorPane();

  Circle startDot;
  Circle endDot;

  private String startLoc;
  private String endLoc;
  Map<Circle, Node> nodeMap;

  public void initialize() throws IOException {
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
    Platform.runLater(
        () -> {
          changeFloor("1", new Point2D(2215, 1045));
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
    Map<String, Node> nodes = DBSession.getAllNodes();

    for (Node value : nodes.values()) {
      if (value.getFloor().equals(currentFloor)) {
        Circle dot = placeNode(value);
        nodeMap.put(dot, value);
      }
    }

    Platform.runLater(() -> pane.centreOn(p));
  }

  private Circle placeNode(Node node) {
    Circle dot = new Circle(node.getXCoord(), node.getYCoord(), 10, Bapp.blue);
    aPane.getChildren().add(dot);
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

  public void signIn() {
    Navigation.navigate(Screen.SIGN_IN);
  }
}
