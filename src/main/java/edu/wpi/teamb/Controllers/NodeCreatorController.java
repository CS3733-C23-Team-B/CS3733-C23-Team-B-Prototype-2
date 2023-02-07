package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NodeCreatorController {
  @FXML Button submitButton;
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML Text bigText;
  @FXML MFXFilterComboBox<String> floorBox;
  @FXML MFXFilterComboBox<String> buildingBox;
  ObservableList<String> floors = FXCollections.observableArrayList();
  ObservableList<String> buildings = FXCollections.observableArrayList();

  public void initialize() {
    Collections.addAll(floors, "1", "2", "3", "L1", "L2");
    Collections.addAll(buildings, "SHAPIRO", "TOWER", "45 Francis", "BTM", "15 Francis");
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

    Node newNode =
        new Node("", Integer.parseInt(newX), Integer.parseInt(newY), newFloor, newBuilding);
    DBSession.addORM(newNode);

    cancelClicked();
  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }
}
