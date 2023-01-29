package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.CSVWriter;
import edu.wpi.teamb.Database.TransportationDataset;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class PatientTransportationController {
  @FXML private TextField firstName;
  @FXML private TextField lastName;
  @FXML private TextField employeeID;
  @FXML private TextField email;
  @FXML private ChoiceBox equipmentNeeded;
  @FXML private ChoiceBox urgency;
  @FXML private TextField patientLocation;
  @FXML private TextField patientDestination;
  @FXML private TextField patientID;
  @FXML private TextField notes;
  @FXML private Button cancelButton;
  @FXML private Button helpButton;
  @FXML private Button clearButton;
  @FXML private Button submitButton;

  // List of all text fields and choice boxes for flexibility; when adding new input components to
  // form, add to this list
  private ArrayList<Control> components;
  private ArrayList<TextField> textFields;
  private ArrayList<ChoiceBox> choiceBoxes;

  private ObservableList<String> equipmentOptions =
      FXCollections.observableArrayList("Stretcher", "Wheelchair", "Restraints", "Stair Chair");
  private ObservableList<String> urgencyOptions =
      FXCollections.observableArrayList("Low", "Moderate", "High", "Requires Immediate Attention");

  /** Initialize the page by declaring choice-box options */
  public void initialize() {
    // Create list of components
    Control[] cl = {
      firstName,
      lastName,
      employeeID,
      email,
      equipmentNeeded,
      urgency,
      patientLocation,
      patientDestination,
      patientID,
      notes
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
    equipmentNeeded.setItems(equipmentOptions);
    urgency.setItems(urgencyOptions);
  }

  /**
   * Return to the home screen without saving form data
   *
   * @throws IOException
   */
  public void cancelButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  /**
   * Display the help page
   *
   * @throws IOException
   */
  public void helpButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATIENT_TRANSPORTATION_HELP);
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
  public void submitButtonClicked() throws IOException, SQLException, SQLException {
    String[] saveInfo = new String[components.size()];

    // Add all input components to the list of data
    for (int i = 0; i < components.size(); i++) {
      Control c = components.get(i);
      if (c instanceof TextField) saveInfo[i] = ((TextField) c).getText();
      if (c instanceof ChoiceBox) {
        String s = (String) ((ChoiceBox) c).getValue();
        if (s == null) s = "None";
        saveInfo[i] = s;
      }
    }

    CSVWriter.writeCsv("patientTransportationRequests", saveInfo);
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
            saveInfo[8]);
    newRequest.insert();

    // TODO: show confirmation page

  }
}
