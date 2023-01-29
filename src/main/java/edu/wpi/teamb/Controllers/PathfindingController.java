package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class PathfindingController {

  @FXML ChoiceBox startNode;
  @FXML ChoiceBox endNode;
  @FXML Button pathfind;
  @FXML Label pathLabel;

  public void initialize() {
    startNode.setItems(getNodes());
    endNode.setItems(getNodes());
    pathfind.setOnAction((eventAction) -> findPath());
  }

  private void findPath() {
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    Node start = nodes.get(startNode.getValue());
    Node end = nodes.get(endNode.getValue());
    String path = Pathfinding.getShortestPath(start.getID(), end.getID());
    pathLabel.setText("Path: " + path);
  }

  static ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    nodes.forEach((key, value) -> list.add(key));

    return list;
  }
}
