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

  @FXML private MFXTextField typeofmedicineField;
  @FXML private MFXTextField dosageField;
  @FXML private MFXTextField patientidField;
  // @FXML private MFXFilterComboBox deliverylocationLocationBox;

  /** Initialize the page by declaring choice-box options */
  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      urgencyBox,
      assignedStaffBox,
      typeofmedicineField,
      dosageField,
      // deliverylocationLocationBox,
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
   * Store the data from the form in a csv file and return to home screen
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
    if (destination == null) {
      destination = "";
    }
    request.setLocation(destination.toString());

    request.setMedicineType(this.typeofmedicineField.getText());
    request.setDoasage(this.dosageField.getText());
    request.setPatientID(this.patientidField.getText());
    request.setRequestType(RequestType.MEDICINE);
    DBSession.addRequest(request);

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();
    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
