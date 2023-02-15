package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LocationCreatorController {
  @FXML TextField longNameField;
  @FXML TextField shortNameField;
  @FXML MFXFilterComboBox<String> locationTypeBox;
  @FXML Text bigText;
  Map<String, LocationName> locations = new HashMap<>();

  /** Method run when controller is initialized */
  public void initialize() {
    ObservableList<String> types = FXCollections.observableArrayList();
    locations = DBSession.getAllLocationNames();
    Collections.addAll(
        types, "CONF", "DEPT", "HALL", "LABS", "REST", "SERV", "EXIT", "STAI", "ELEV");
    locationTypeBox.setItems(types);
  }

  public void resetFields() {
    longNameField.clear();
    shortNameField.clear();
    locationTypeBox.clear();
  }

  public void submitClicked() {
    String newLongName = longNameField.getText();
    String newShortName = shortNameField.getText();
    String newLocationType = locationTypeBox.getText();

    if (newLongName.isEmpty() || newShortName.isEmpty() || newLocationType.isEmpty()) {
      bigText.setText("Missing fields");
      bigText.setFill(Color.RED);
      return;
    }

    if (locations.containsKey(newLongName)) {
      resetFields();
      bigText.setText("Location already exists");
      bigText.setFill(Color.RED);
      return;
    }

    LocationName newLN = new LocationName();
    newLN.setShortName(newShortName);
    newLN.setLongName(newLongName);
    newLN.setLocationType(newLocationType);
    DBSession.addLocationName(newLN);
    backClicked();
  }

  public void backClicked() {
    Navigation.navigate(Screen.LOCATION_EDITOR);
  }
}
