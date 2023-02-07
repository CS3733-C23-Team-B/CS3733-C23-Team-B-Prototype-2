package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.ComputerRequest;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Navigation.Navigation;
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
  @FXML private MFXFilterComboBox repairLocationBox;
  @FXML private MFXFilterComboBox typeOfRepairBox;
  @FXML private MFXFilterComboBox<String> deviceBox;

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
      repairLocationBox,
      urgencyBox,
      typeOfRepairBox,
      deviceBox,
      assignedStaffField,
      additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();
    repairLocationBox.setItems(PathfindingController.getLocations());

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }
    typeOfRepairBox.setItems(typeOfRepairList);
    deviceBox.setItems(typeOfDeviceList);

    helpScreen = Screen.COMPUTER_SERVICES_HELP;
    super.initialize();
  }

  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    ComputerRequest request = new ComputerRequest();

    request.setFirstname(firstNameField.getText());
    request.setLastname(lastNameField.getText());
    request.setEmployeeID(employeeIDField.getText());
    request.setEmail(emailField.getText());
    request.setNotes(additionalNotesField.getText());
    request.setAssignedEmployee(assignedStaffField.getText());

    var urgency = urgencyBox.getValue();
    if (urgency == null) {
      urgency = "";
    }
    request.setUrgency(urgency.toString());

    request.setRepairLocation(repairLocationBox.getText());

    var typeOfrepair = typeOfRepairBox.getValue();
    if (typeOfrepair == null) {
      typeOfrepair = "";
    }
    var device = deviceBox.getValue();
    if (device == null) {
      device = "";
    }
    request.setDevice(device.toString());

    request.setAssignedEmployee(assignedStaffField.getText());
    request.setTypeOfRepair(typeOfrepair.toString());
    request.setStatus(RequestStatus.PROCESSING);
    DBSession.addORM(request);

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();

    Navigation.navigate((Screen.SUBMISSION_SUCCESS));
  }
}
