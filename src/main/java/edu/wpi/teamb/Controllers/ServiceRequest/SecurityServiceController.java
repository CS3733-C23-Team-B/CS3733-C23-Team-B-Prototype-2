package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.SecurityRequest;
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

public class SecurityServiceController extends BaseRequestController {
  ObservableList<String> typeOfEquipment =
      FXCollections.observableArrayList("Restraints", "Taser", "Barricade");

  ObservableList<String> typeOfIssue =
      FXCollections.observableArrayList(
          "Unruly Patient",
          "Intruder",
          "General Danger",
          "Escalated Disagreement",
          "Suspicious Activity");

  ObservableList<String> numberList =
      FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

  @FXML private MFXFilterComboBox<String> issueBox;
  @FXML private MFXFilterComboBox<String> equipNeededBox;
  @FXML private MFXFilterComboBox<String> numbReqBox;

  /** Initialize the page by creating lists of components and declaring choice-box options */
  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      equipNeededBox, urgencyBox, issueBox, assignedStaffBox, numbReqBox, additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }

    equipNeededBox.setItems(typeOfEquipment);
    issueBox.setItems(typeOfIssue);
    numbReqBox.setItems(numberList);

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

    SecurityRequest request = new SecurityRequest();

    super.submit(request);

    request.setEquipmentNeeded(equipNeededBox.getText());
    request.setIssueType(issueBox.getText());
    request.setNumberRequired(Integer.parseInt(numbReqBox.getText()));

    request.setRequestType(RequestType.SECURITY);
    DBSession.addRequest(request);

    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
