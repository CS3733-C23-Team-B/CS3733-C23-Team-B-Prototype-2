package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Entities.SanitationRequest;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class sanitationServiceController {
  // Lists for checkboxes
  ObservableList<String> urgencyList =
      FXCollections.observableArrayList("Low", "Moderate", "High", "Requires Immediate Attention");
  ObservableList<String> typeOfCleanUpList =
      FXCollections.observableArrayList("Bathroom", "Spill", "Vacant Room", "Blood", "Chemicals");
  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private TextField employeeIDField;
  @FXML private TextField emailField;
  @FXML private TextField cleanUpLocationField;
  @FXML private ChoiceBox urgencyBox;
  @FXML private ChoiceBox typeOfCleanUpBox;
  @FXML private TextField additionalNotesField;
  @FXML private Button helpButton;
  @FXML private Button cancelButton;
  @FXML private Button submitButton;

  private ArrayList<Control> components;
  private ArrayList<TextField> textFields;
  private ArrayList<ChoiceBox> choiceBoxes;

  @FXML
  public void initialize() {
    // initialization goes here
    // Create list of components
    Control[] ctrl = {
      firstNameField,
      lastNameField,
      employeeIDField,
      emailField,
      cleanUpLocationField,
      urgencyBox,
      typeOfCleanUpBox,
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
    urgencyBox.setItems(urgencyList);
    typeOfCleanUpBox.setItems(typeOfCleanUpList);
  }

  public void clearButtonClicked() throws IOException {
    // Stuff to handle the clearing of fields goes here
    resetTextFields();
    resetChoiceBoxes();
  }

  private void resetTextFields() {
    // clear text fields
    for (TextField t : textFields) t.clear();
  }

  private void resetChoiceBoxes() throws IOException {
    // clear choices
    // not sure if this function is the right one to clear it yet
    for (ChoiceBox c : choiceBoxes) c.valueProperty().set(null);
  }

  public void helpButtonClicked() throws IOException {
    Navigation.navigate(Screen.SANITATION_HELP);
  }

  public void cancelButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
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

    System.out.println(request);
    // may need to clear fields can be done with functions made for clear
    resetChoiceBoxes();
    resetTextFields();

    // have to make FXML file for submission success
    // this will then change to the success screen
    Navigation.navigate(Screen.HOME);
  }
}
