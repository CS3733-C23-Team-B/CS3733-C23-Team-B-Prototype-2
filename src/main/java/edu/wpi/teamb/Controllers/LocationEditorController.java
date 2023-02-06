package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LocationEditorController {
  @FXML MFXFilterComboBox<String> locationBox;
  @FXML Button submitButton;
  @FXML TextField longNameField;
  @FXML TextField shortNameField;
  @FXML MFXFilterComboBox<String> locationTypeBox;
  @FXML Button exit;
  Map<String, LocationName> locations;
  private String origType;
  private LocationName location;
  ObservableList<String> types = FXCollections.observableArrayList();

  /** Method run when controller is initialized */
  public void initialize() {
    locationBox.setItems(getLocations());
    Collections.addAll(types, "CONF", "DEPT", "HALL", "LABS", "REST", "SERV", "EXIT", "STAI", "ELEV");
  }

  public void setFields() {
    if (!locationBox.getValue().isEmpty()) {
      location = locations.get(locationBox.getValue());
      String longName = location.getLongName();
      String shortName = location.getShortName();
      String locationType = location.getLocationType();

      locationTypeBox.setItems(types);
      longNameField.setPromptText(longName);
      shortNameField.setPromptText(shortName);
      locationTypeBox.setValue(locationType);
      origType = locationType;
    }
  }

  private ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();
    try {
      locations = LocationName.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    locations.forEach((key, value) -> list.add(value.getLongName()));

    return list;
  }

//  public void submitClicked() {
//    boolean changed = false;
//
//    String newLongName = longNameField.getText();
//    String newShortName = shortNameField.getText();
//    String newLocationType = locationTypeBox.getValue();
//
//    if (!newLongName.isEmpty()) {
//      location.setLongName(newLongName);
//      changed = true;
//    }
//    if (!newShortName.isEmpty()) {
//      location.setShortName(newShortName);
//      changed = true;
//    }
//    if (!newLocationType.equals(origType)) {
//      node.setFloor(newFloor);
//      changed = true;
//    }
//
//    if (changed) {
//      try {
//        node.update();
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      }
//      Pathfinding.refreshData();
//    }
//    cancelClicked();
//  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void newLocationClicked() {

  }
}
