package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

public class BaseRequestController {
  // JavaFX components

  @FXML protected MFXFilterComboBox<Urgency> urgencyBox;
  @FXML protected MFXFilterComboBox<String> assignedStaffBox;
  @FXML protected MFXTextField additionalNotesField;
  private RequestStatus request;
  @FXML protected MFXButton backButton;
  @FXML protected MFXButton helpButton;
  @FXML protected MFXButton clearButton;

  @FXML protected MFXButton submitButton;

  // Choice-box options
  protected ObservableList<Urgency> urgencyOptions =
      FXCollections.observableArrayList(
          Urgency.LOW, Urgency.MODERATE, Urgency.HIGH, Urgency.REQUIRESIMMEADIATEATTENTION);
  protected ObservableList<String> staffMembers = DBSession.getStaff();

  // List of all text fields and choice boxes for flexibility; when adding new input components to
  // form, add to this list
  protected ArrayList<Control> components;
  protected ArrayList<MFXTextField> textFields;
  protected ArrayList<MFXFilterComboBox<String>> choiceBoxes;

  protected Screen submissionScreen;

  protected Login currUser;

  /**
   * Initialize the page by instantiating 3 arrays of all components, text field components, and
   * choice-box components, as well as declaring choice-box options
   */
  @FXML
  public void initialize() {
    submitButton.setDisable(true);
    urgencyBox.setItems(urgencyOptions);
    assignedStaffBox.setItems(staffMembers);
    currUser = SigninController.getCurrentUser();
  }

  /**
   * Return to the home screen without saving form data
   *
   * @throws IOException
   */
  public void backButtonClicked() throws IOException {
    Navigation.navigate(Screen.SERVICE_REQUEST_SYSTEMS);
  }

  /**
   * Display the help page
   *
   * @throws IOException
   */
  public void helpButtonClicked() throws IOException {
    Popup.displayPopup(Screen.SERVICE_REQUEST_FORM_HELP);
  }

  /**
   * Remove all inputted data from the form
   *
   * @throws IOException
   */
  public void clearButtonClicked() throws IOException {
    for (MFXTextField t : textFields) t.clear();

    for (MFXFilterComboBox c : choiceBoxes) c.getSelectionModel().clearSelection();
  }

  /**
   * Store the data from the form in a Java object and show confirmation screen
   *
   * @throws IOException
   */
  public void submitButtonClicked() throws IOException, SQLException {
    Navigation.navigate(submissionScreen);
  }

  /**
   * Get the locations in the hospital
   *
   * @return an alphabetized list of Strings
   */
  protected ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();

    Map<String, LocationName> locationNames = DBSession.getAllLocationNames();
    locationNames.forEach((key, value) -> list.add(value.getLongName()));

    Sorting.quickSort(list);
    return list;
  }

  /**
   * Returns the text in the given component, whether it's a MFXTextField or ChoiceBox
   *
   * @param component the MFXTextField or ChoiceBox
   * @return a String containing the inputted text
   */
  protected String getText(Control component) {
    if (component instanceof MFXTextField) return ((MFXTextField) component).getText();
    else if (component instanceof MFXFilterComboBox) {
      String s = (String) ((MFXFilterComboBox) component).getValue();
      if (s == null) s = "";
      return s;
    } else {
      return "";
    }
  }

  /**
   * Whenever a key is released, updates disable status of clearButton and submitButton
   *
   * @throws IOException
   */
  public void buttonControl() throws IOException {
    boolean submitEnable = isFormFull();
    submitButton.setDisable(!submitEnable);
  }

  /**
   * Determine whether every field (except notes) in the request form is full
   *
   * @return true if form is full, false otherwise
   */
  private boolean isFormFull() {
    return isFormFull(0);
  }

  /**
   * Determine whether every field (except notes) in the request form is full
   *
   * @param i iterator i should be initialized as 0
   * @return true if form is full, false otherwise
   */
  private boolean isFormFull(int i) {
    // Skip final item, which should be the notes field
    if (i == components.size() - 2) return !getText(components.get(i)).equals("");
    return !getText(components.get(i)).equals("") && isFormFull(i + 1);
  }

  protected void submit(GeneralRequest request) {
    request.setFirstname(currUser.getFirstname());
    request.setLastname(currUser.getLastname());
    request.setEmployeeID(String.valueOf(currUser.getId()));
    request.setEmail(currUser.getEmail());
    request.setNotes(additionalNotesField.getText());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    request.setDate(dtf.format(now));
    var staff = assignedStaffBox.getValue();
    if (staff == null) {
      staff = "";
    }
    request.setAssignedEmployee(staff.toString());

    var urgency = urgencyBox.getValue();
    if (urgency == null) {
      urgency = Urgency.LOW;
    }
    request.setUrgency(urgency);
    request.setStatus(RequestStatus.PROCESSING);
  }
}
