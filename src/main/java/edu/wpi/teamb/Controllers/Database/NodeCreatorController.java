package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NodeCreatorController {
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML Text bigText;
  @FXML MFXFilterComboBox<String> floorBox;
  @FXML MFXFilterComboBox<String> buildingBox;

  public void initialize() {
    ObservableList<String> floors = FXCollections.observableArrayList();
    ObservableList<String> buildings = FXCollections.observableArrayList();
    Collections.addAll(floors, "1", "2", "3", "L1", "L2");
    Collections.addAll(buildings, "Shapiro", "Tower", "45 Francis", "BTM", "15 Francis");
    Sorting.quickSort(buildings);
    floorBox.setItems(floors);
    buildingBox.setItems(buildings);
  }

  public void submitClicked() {

    String newX = xField.getText();
    String newY = yField.getText();
    String newFloor = floorBox.getText();
    String newBuilding = buildingBox.getText();

    if (newX.isEmpty() || newY.isEmpty() || newFloor.isEmpty() || newBuilding.isEmpty()) {
      bigText.setText("Missing fields");
      bigText.setFill(Color.RED);
      return;
    }

    Node newNode = new Node();
    newNode.setXCoord(Integer.parseInt(newX));
    newNode.setYCoord(Integer.parseInt(newY));
    newNode.setFloor(newFloor);
    newNode.setBuilding(newBuilding);
    newNode.setNodeID(newNode.buildID());
    DBSession.addNode(newNode);
    Pathfinding.refreshData();

    cancelClicked();
  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }
}
