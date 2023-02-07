package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Entities.PatientTransportationRequest;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class PatientTransportationController extends BaseRequestController {
  // Lists for checkboxes
  private ObservableList<String> equipmentOptions =
      FXCollections.observableArrayList("Stretcher", "Wheelchair", "Restraints", "Stair Chair");
  @FXML private MFXFilterComboBox patientCurrentLocationBox;
  @FXML private MFXFilterComboBox patientDestinationLocationBox;
  @FXML private MFXFilterComboBox urgencyBox;
  @FXML private MFXComboBox equipmentNeededBox;
  @FXML private TextField patientIDField;
  @FXML private TextField additionalNotesField;

  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      firstNameField,
      lastNameField,
      employeeIDField,
      emailField,
      urgencyBox,
      assignedStaffField,
      patientCurrentLocationBox,
      patientDestinationLocationBox,
      equipmentNeededBox,
      patientIDField,
      additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();
    patientCurrentLocationBox.setItems(PathfindingController.getLocations());
    patientDestinationLocationBox.setItems(PathfindingController.getLocations());

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }
    equipmentNeededBox.setItems(equipmentOptions);

    helpScreen = Screen.PATIENT_TRANSPORTATION_HELP;
    super.initialize();
  }

  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    PatientTransportationRequest request = PatientTransportationRequest.getInstance();

    request.setFirstName(firstNameField.getText());
    request.setLastName(lastNameField.getText());
    request.setEmployeeID(employeeIDField.getText());
    request.setEmail(emailField.getText());
    request.setNotes(additionalNotesField.getText());
    request.setAssignedEmployee(assignedStaffField.getText());

    var urgency = urgencyBox.getValue();
    if (urgency == null) {
      urgency = "";
    }
    request.setUrgency(urgency.toString());

    var patientCurrentLocation = patientCurrentLocationBox.getValue();
    if (patientCurrentLocation == null) {
      patientCurrentLocation = "";
    }
    request.setPatientCurrentLocation(patientCurrentLocation.toString());

    var patientDestinationLocation = patientDestinationLocationBox.getValue();
    if (patientDestinationLocation == null) {
      patientDestinationLocation = "";
    }
    request.setPatientDestinationLocation(patientDestinationLocation.toString());

    var equipmentNeeded = equipmentNeededBox.getValue();
    if (equipmentNeeded == null) {
      equipmentNeeded = "";
    }
    request.setEquipmentNeeded(equipmentNeeded.toString());

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();

    Navigation.navigate((Screen.SUBMISSION_SUCCESS));
  }
}
