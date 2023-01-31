package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.TransportationDataset;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class PatientTransportationController extends BaseRequestController {
  // TODO: rename remaining vars with Field or Box appending them
  @FXML private ChoiceBox equipmentNeededBox;
  @FXML private TextField patientLocationField;
  @FXML private TextField patientDestinationField;
  @FXML private TextField patientIDField;

  private ObservableList<String> equipmentOptions =
      FXCollections.observableArrayList("Stretcher", "Wheelchair", "Restraints", "Stair Chair");

  /** Initialize the page by declaring choice-box options */
  @Override
  public void initialize() {
    // Create list of components; additionalNotesField MUST be last
    Control[] cl = {
      firstNameField,
      lastNameField,
      employeeIDField,
      emailField,
      equipmentNeededBox,
      urgencyBox,
      patientLocationField,
      patientDestinationField,
      patientIDField,
      // assignedEmployeeField, <-add in when it's actually in SceneBuilder
      additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(cl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof TextField) textFields.add((TextField) c);
      if (c instanceof ChoiceBox) choiceBoxes.add((ChoiceBox) c);
    }

    // Initialize the choice boxes with their options
    equipmentNeededBox.setItems(equipmentOptions);

    helpScreen = Screen.PATIENT_TRANSPORTATION_HELP;
    super.initialize();
  }

  /**
   * Remove all inputted data from the form
   *
   * @throws IOException
   */
  public void clearButtonClicked() throws IOException {
    for (TextField t : textFields) t.clear();

    for (ChoiceBox c : choiceBoxes) c.getSelectionModel().clearSelection();
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
    clearButtonClicked();

    // insert into database:
    TransportationDataset newRequest =
        new TransportationDataset(
            saveInfo[0],
            saveInfo[1],
            saveInfo[2],
            saveInfo[3],
            saveInfo[4],
            saveInfo[5],
            saveInfo[6],
            saveInfo[7],
            saveInfo[9],
            saveInfo[8],
            RequestStatus.PROCESSING);
    newRequest.insert();

    // TODO: show confirmation page
  }
}
