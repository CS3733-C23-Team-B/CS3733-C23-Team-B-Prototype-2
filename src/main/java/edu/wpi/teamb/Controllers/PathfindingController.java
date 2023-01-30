package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class PathfindingController {

  @FXML ChoiceBox startLoc;
  @FXML ChoiceBox endLoc;
  @FXML Button pathfind;
  @FXML Label pathLabel;

  public void initialize() {
    startLoc.setItems(getLocations());
    endLoc.setItems(getLocations());
    pathfind.setOnAction((eventAction) -> findPath());
  }

  private void findPath() {
    String start = (String) startLoc.getValue();
    String end = (String) endLoc.getValue();
    String path = Pathfinding.getShortestPath(start, end);
    pathLabel.setText("Path: " + path);
  }

  static ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();

    List<Move> moves;
    try {
      moves = Move.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    for (Move move : moves) if (!list.contains(move.getLongName())) list.add(move.getLongName());

    return list;
  }
}
