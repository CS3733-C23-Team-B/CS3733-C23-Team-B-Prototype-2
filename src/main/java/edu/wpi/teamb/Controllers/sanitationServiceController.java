package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Entities.SanitationRequest;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class sanitationServiceController extends BaseRequestController {
  // Lists for checkboxes
  ObservableList<String> typeOfCleanUpList =
      FXCollections.observableArrayList("Bathroom", "Spill", "Vacant Room", "Blood", "Chemicals");
  @FXML private TextField cleanUpLocationField;
  @FXML private ChoiceBox typeOfCleanUpBox;

  @FXML private TextField assignedStaffField;

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
      cleanUpLocationField,
      urgencyBox,
      typeOfCleanUpBox,
      assignedStaffField,
      additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof TextField) textFields.add((TextField) c);
      if (c instanceof ChoiceBox) choiceBoxes.add((ChoiceBox) c);
    }
    typeOfCleanUpBox.setItems(typeOfCleanUpList);

    helpScreen = Screen.SANITATION_HELP;
    super.initialize();
  }

  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    SanitationRequest request = SanitationRequest.getInstance();

    request.setFirstName(firstNameField.getText());
    request.setLastName(lastNameField.getText());
    request.setEmployeeID(employeeIDField.getText());
    request.setEmail(emailField.getText());
    request.setNotes(additionalNotesField.getText());

    var urgency = urgencyBox.getValue();
    if (urgency == null) {
      urgency = "";
    }
    request.setUrgency(urgency.toString());

    request.setCleanUpLocation(cleanUpLocationField.getText());

    var typeOfcleanUp = typeOfCleanUpBox.getValue();
    if (typeOfcleanUp == null) {
      typeOfcleanUp = "";
    }
    request.setTypeOfCleanUp(typeOfcleanUp.toString());

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();
  }
}
