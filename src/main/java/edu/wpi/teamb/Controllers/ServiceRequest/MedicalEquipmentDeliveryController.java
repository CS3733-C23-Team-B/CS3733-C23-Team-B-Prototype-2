package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.PatientTransportationRequest;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MedicalEquipmentDeliveryController extends BaseRequestController {
  // Lists for checkboxes
  private ObservableList<String> equipmentOptions =
      FXCollections.observableArrayList("Stretcher", "Wheelchair", "Restraints", "Stair Chair");
  @FXML private MFXFilterComboBox patientCurrentLocationBox;
  @FXML private MFXFilterComboBox patientDestinationLocationBox;
  @FXML private MFXComboBox equipmentNeededBox;
  @FXML private MFXTextField patientIDField;

  /** Initialize the page by declaring choice-box options */
  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      urgencyBox,
      assignedStaffBox,
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
    super.submit(request);

    var equipment = equipmentNeededBox.getValue();
    if (equipment == null) {
      equipment = "";
    }
    request.setEquipmentNeeded(equipment.toString());

    var destination = patientDestinationLocationBox.getValue();
    if (destination == null) {
      destination = "";
    }
    request.setPatientDestinationLocation(destination.toString());

    var curLocation = patientCurrentLocationBox.getValue();
    if (curLocation == null) {
      curLocation = "";
    }
    request.setPatientCurrentLocation(curLocation.toString());

    request.setPatientID(this.patientIDField.getText());
    request.setRequestType(RequestType.PATIENTTRANSPOTATION);
    DBSession.addRequest(request);

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();
    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
