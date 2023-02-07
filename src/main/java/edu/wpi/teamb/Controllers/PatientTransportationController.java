package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.PatientTransportationRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.sql.SQLException;
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

  /**
   * Store the data from the form in a csv file and return to home screen
   *
   * @throws IOException
   */
  @Override
  public void submitButtonClicked() throws IOException, SQLException {
    String[] saveInfo = new String[components.size()];

    // Add all input components to the list of data
    for (int i = 0; i < components.size(); i++) {
      Control c = components.get(i);
      saveInfo[i] = getText(c);
    }

    // CSVWriter.writeCsv("patientTransportationRequests", saveInfo);

    PatientTransportationRequest request = new PatientTransportationRequest();
    request.setFirstname(firstNameField.getText());
    request.setLastname(lastNameField.getText());
    request.setEmployeeID(employeeIDField.getText());
    request.setEmail(emailField.getText());
    //    add status
    request.setAssignedEmployee(this.assignedStaffField.getText());
    request.setNotes(additionalNotesField.getText());

    var urgency = urgencyBox.getValue();
    if (urgency == null) {
      urgency = "";
    }
    request.setUrgency(urgency.toString());

    var equipment = equipmentNeededBox.getValue();
    if (equipment == null) {
      equipment = "";
    }
    request.setEquipment(equipment.toString());
    request.setLocation(this.patientCurrentLocationBox.getText());
    request.setDestination(this.patientDestinationLocationBox.getText());
    request.setPatientID(this.patientIDField.getText());
    request.setStatus(RequestStatus.PROCESSING);
    DBSession.addORM(request);

    clearButtonClicked();
    Navigation.navigate(Screen.SUBMISSION_SUCCESS);
  }
}
