package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Database.Node;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LocationEditorController {
  @FXML MFXFilterComboBox<String> locationBox;
  @FXML Button submitButton;
  @FXML TextField longNameField;
  @FXML TextField shortNameField;
  @FXML MFXFilterComboBox<String> locationTypeBox;
  @FXML MFXFilterComboBox<String> nodeBox;
  @FXML Text bigText;
  Map<String, LocationName> locations = new HashMap<>();
  private String origType;
  private String origNode;
  private LocationName location;
  ObservableList<String> types = FXCollections.observableArrayList();
  ObservableList<String> nodes = FXCollections.observableArrayList();

  /** Method run when controller is initialized */
  public void initialize() {
    locationBox.setItems(getLocations());
    Collections.addAll(
        types, "CONF", "DEPT", "HALL", "LABS", "REST", "SERV", "EXIT", "STAI", "ELEV");
    nodes = getNodes();
  }

  public void setFields() {
    if (!locationBox.getValue().isEmpty()) {
      location = locations.get(locationBox.getValue());
      origNode = DBSession.getMostRecentNodeID(location.getLongName());

      String longName = location.getLongName();
      String shortName = location.getShortName();
      String locationType = location.getLocationType();

      locationTypeBox.setItems(types);
      nodeBox.setItems(nodes);
      longNameField.setPromptText(longName);
      shortNameField.setPromptText(shortName);
      locationTypeBox.setValue(locationType);
      nodeBox.setValue(origNode);

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

  private ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, Node> nodeDBMap = DBSession.getAllNodes();

    nodeDBMap.forEach((key, value) -> list.add(value.getNodeID()));

    Sorting.sort(list);
    return list;
  }

  public void submitClicked() {
    boolean changed = false;

    String newLongName = location.getLongName();
    String newShortName = location.getShortName();
    String newLocationType = location.getLocationType();

    if (!longNameField.getText().isEmpty()) {
      changed = true;
      newLongName = longNameField.getText();
    }
    if (!shortNameField.getText().isEmpty()) {
      changed = true;
      newShortName = shortNameField.getText();
    }
    if (!newLocationType.equals(origType)) {
      changed = true;
      newLocationType = locationTypeBox.getValue();
    }

    if (changed) {
      LocationName newLN = new LocationName(newLongName, newShortName, newLocationType);
      if (nodeBox.getValue().equals(origNode)) DBSession.updateLocationName(newLN, location);
      else DBSession.switchMoveLN(nodeBox.getValue(), origNode, newLN);
      Pathfinding.refreshData();
      location = newLN;
    } else if (!nodeBox.getValue().equals(origNode))
      DBSession.switchMoveLN(nodeBox.getValue(), origNode, location);
    else {
      bigText.setText("No Change");
      bigText.setFill(Color.RED);
      return;
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
