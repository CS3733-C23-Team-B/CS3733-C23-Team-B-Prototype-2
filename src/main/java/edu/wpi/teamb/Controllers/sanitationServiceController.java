package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.CSVWriter;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

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

    // may need to clear fields can be done with functions made for clear
    resetChoiceBoxes();
    resetTextFields();
    CSVWriter.writeCsv("sanitationServiceCSV", saveInfo);
  }
}
