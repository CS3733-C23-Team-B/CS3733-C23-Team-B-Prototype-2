package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.PatientTransportationRequest;
import edu.wpi.teamb.Entities.RequestStatus;
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

  /** Initialize the page by declaring choice-box options */
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

    ObservableList<String> locations = getLocations();

    patientCurrentLocationBox.setItems(locations);
    patientDestinationLocationBox.setItems(locations);

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }
    equipmentNeededBox.setItems(equipmentOptions);

    helpScreen = Screen.PATIENT_TRANSPORTATION_HELP;
    super.initialize();
  }

  /**
   * Store the data from the form in a csv file and return to home screen
   *
   * @throws IOException
   */
  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    PatientTransportationRequest request = new PatientTransportationRequest();
    request.setFirstname(firstNameField.getText());
    request.setLastname(lastNameField.getText());
    request.setEmployeeID(employeeIDField.getText());
    request.setEmail(emailField.getText());
    request.setStatus(RequestStatus.PROCESSING);
    request.setAssignedEmployee(assignedStaffField.getText());
    request.setNotes(additionalNotesField.getText());

    var urgency = urgencyBox.getValue();
    if (urgency == null) {
      urgency = "";
    }

    var equipment = equipmentNeededBox.getValue();
    if (equipment == null) {
      equipment = "";
    }
    var destination = patientDestinationLocationBox.getValue();
    if (destination == null) {
      destination = "";
    }
    var curLocation = patientCurrentLocationBox.getValue();
    if (curLocation == null) {
      curLocation = "";
    }
    request.setUrgency(urgency.toString());
    request.setEquipmentNeeded(equipment.toString());
    request.setPatientCurrentLocation(curLocation.toString());
    request.setPatientDestinationLocation(destination.toString());
    request.setPatientID(this.patientIDField.getText());
    DBSession.addORM(request);

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();
    Navigation.navigate((Screen.SUBMISSION_SUCCESS));
  }
}
