package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.LocationName;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LocationEditorController {
  @FXML MFXFilterComboBox<String> locationBox;
  @FXML Button submitChange;
  @FXML TextField longNameField;
  @FXML TextField shortNameField;
  @FXML MFXFilterComboBox<String> locationTypeBox;
  @FXML Button exit;
  Map<String, LocationName> locations;
  private String origType;

  /** Method run when controller is initialized */
  public void initialize() {
    locationBox.setItems(getLocations());
  }

  public void setFields() {
    if (!locationBox.getValue().isEmpty()) {
      LocationName location = locations.get(locationBox.getValue());
      String longName = location.getLongName();
      String shortName = location.getShortName();
      String locationType = location.getLocationType();

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
}
