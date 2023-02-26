package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.MedicineDeliveryRequest;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

public class MedicineDeliveryServiceController extends BaseRequestController {

  ObservableList<String> locations = getLocations();

  @FXML private MFXTextField typeOfMedicineField;
  @FXML private MFXTextField dosageField;
  @FXML private MFXTextField patientIDField;

  /** Initialize the page by creating lists of components and declaring choice-box options */
  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      urgencyBox,
      assignedStaffBox,
      locationBox,
      typeOfMedicineField,
      dosageField,
      patientIDField,
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

    assignedStaffBox.setItems(staffMembers);

    locationBox.setItems(locations);

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

    MedicineDeliveryRequest request = new MedicineDeliveryRequest();
    super.submit(request);

    var destination = locationBox.getValue();
    request.setLocation(destination.toString());

    request.setMedicineType(this.typeOfMedicineField.getText());
    request.setDoasage(this.dosageField.getText());
    request.setPatientID(this.patientIDField.getText());

    request.setRequestType(RequestType.MEDICINE);
    DBSession.addRequest(request);

    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
