package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.ComputerRequest;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

public class ComputerServiceController extends BaseRequestController {
  // Lists for checkboxes
  ObservableList<String> typeOfRepairList =
      FXCollections.observableArrayList("New Hardware", "Broken Hardware", "Technical Issues");
  ObservableList<String> typeOfDeviceList =
      FXCollections.observableArrayList("Computer", "Phone", "Monitor");

  @FXML private MFXFilterComboBox typeOfRepairBox;
  @FXML private MFXFilterComboBox<String> typeOfDeviceBox;

  /** Initialize the page by creating lists of components and declaring choice-box options */
  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      locationBox,
      urgencyBox,
      typeOfRepairBox,
      typeOfDeviceBox,
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

    typeOfRepairBox.setItems(typeOfRepairList);
    typeOfDeviceBox.setItems(typeOfDeviceList);

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

    ComputerRequest request = new ComputerRequest();

    super.submit(request);

    var typeOfrepair = typeOfRepairBox.getValue();
    request.setTypeOfRepair(typeOfrepair.toString());

    var device = typeOfDeviceBox.getValue();
    request.setDevice(device.toString());

    request.setRequestType(RequestType.COMPUTER);
    DBSession.addRequest(request);

    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
