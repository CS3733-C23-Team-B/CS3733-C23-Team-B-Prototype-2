package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NodeEditorController {
  @FXML Text nodeIDText;
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML ChoiceBox<String> floorBox;
  Node node = MapEditorController.getCurrentNode();
  String origFloor;

  public void initialize() {
    ObservableList<String> floors = FXCollections.observableArrayList();
    for (String s : new String[] {"L1", "L2"}) floors.add(s);
    xField.setPromptText("" + node.getXCoord());
    yField.setPromptText("" + node.getYCoord());
    origFloor = node.getFloor();
    floorBox.setItems(floors);
    floorBox.setValue(origFloor);
    nodeIDText.setText(node.getID());
  }

  public void submitClicked() {
    boolean changed = false;

    String newX = xField.getText();
    String newY = yField.getText();
    String newFloor = floorBox.getValue();

    if (!newX.isEmpty()) {
      node.setXCoord(Integer.parseInt(newX));
      changed = true;
    }
    if (!newY.isEmpty()) {
      node.setYCoord(Integer.parseInt(newY));
      changed = true;
    }
    if (!newFloor.equals(origFloor)) {
      node.setFloor(newFloor);
      changed = true;
    }

    if (changed) {
      try {
        node.update();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Pathfinding.refreshData();
    }
    cancelClicked();
  }

  public void cancelClicked() {
    Stage s = (Stage) yField.getScene().getWindow();
    s.close();
    MapEditorController.getInstance().refreshPopUp();
  }
}
