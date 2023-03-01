package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.PatientTransportationRequest;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
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

public class PatientTransportationController extends BaseRequestController {
  // Lists for checkboxes
  private ObservableList<String> equipmentOptions =
      FXCollections.observableArrayList("Stretcher", "Wheelchair", "Restraints", "Stair Chair");
  @FXML private MFXFilterComboBox patientDestinationBox;
  @FXML private MFXComboBox equipmentNeededBox;
  @FXML private MFXTextField patientIDField;

  /** Initialize the page by creating lists of components and declaring choice-box options */
  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      urgencyBox,
      locationBox,
      patientDestinationBox,
      equipmentNeededBox,
      patientIDField,
      assignedStaffBox,
      additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }

    ObservableList<String> locations = getLocations();
    patientDestinationBox.setItems(locations);
    equipmentNeededBox.setItems(equipmentOptions);

    super.initialize();
  }

  /**
   * Store the user's input in the database and show confirmation popup
   *
   * @throws IOException
   */
  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    PatientTransportationRequest request = new PatientTransportationRequest();
    super.submit(request);

    var equipment = equipmentNeededBox.getValue();
    request.setEquipmentNeeded(equipment.toString());

    var destination = patientDestinationBox.getValue();
    request.setPatientDestinationLocation(destination.toString());

    request.setPatientID(this.patientIDField.getText());
    request.setRequestType(RequestType.PATIENTTRANSPOTATION);
    DBSession.addRequest(request);

    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
