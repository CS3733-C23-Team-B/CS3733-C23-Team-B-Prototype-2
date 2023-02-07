package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.*;
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
  Map<String, LocationName> locations = new HashMap<>();
  private String origType;
  private LocationName location;
  ObservableList<String> types = FXCollections.observableArrayList();

  /** Method run when controller is initialized */
  public void initialize() {
    locationBox.setItems(getLocations());
    Collections.addAll(
        types, "CONF", "DEPT", "HALL", "LABS", "REST", "SERV", "EXIT", "STAI", "ELEV");
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

  public void resetFields() {
    locationBox.clear();
    longNameField.clear();
    shortNameField.clear();
    locationTypeBox.clear();
    locationBox.setItems(getLocations());
  }

  private ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();
    List<LocationName> locationsDBList = DBSession.getAllLocationNames();

    locationsDBList.forEach(
        i -> {
          list.add(i.getLongName());
          locations.put(i.getLongName(), i);
        });

    Sorting.sort(list);
    return list;
  }

  public void submitClicked() {
    boolean changed = false;

    String newLongName = longNameField.getText();
    String newShortName = shortNameField.getText();
    String newLocationType = locationTypeBox.getValue();

    if (!newLongName.isEmpty()) changed = true;
    if (!newShortName.isEmpty()) changed = true;
    if (!newLocationType.equals(origType)) changed = true;

    if (changed) {
      LocationName newLN = new LocationName(newLongName, newShortName, newLocationType);
      DBSession.updateLocationName(newLN, location);
      Pathfinding.refreshData();
      location = newLN;
    }
    cancelClicked();
  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void newLocationClicked() {
    Navigation.navigate(Screen.LOCATION_CREATOR);
  }

  public void deleteClicked() {
    DBSession.delete(location);
    resetFields();
  }
}
