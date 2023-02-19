package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.SanitationRequest;
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

public class SanitationServiceController extends BaseRequestController {
  // Lists for checkboxes
  ObservableList<String> typeOfCleanUpList =
      FXCollections.observableArrayList("Bathroom", "Spill", "Vacant Room", "Blood", "Chemicals");
  @FXML private MFXFilterComboBox<String> cleanUpLocationBox;
  @FXML private MFXFilterComboBox<String> typeOfCleanUpBox;

  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      cleanUpLocationBox, urgencyBox, typeOfCleanUpBox, assignedStaffBox, additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();
    cleanUpLocationBox.setItems(getLocations());

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }
    typeOfCleanUpBox.setItems(typeOfCleanUpList);

    helpScreen = Screen.SANITATION_HELP;
    super.initialize();
  }

  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    SanitationRequest request = new SanitationRequest();

    super.submit(request);

    var cleanUpLocation = cleanUpLocationBox.getValue();
    if (cleanUpLocation == null) {
      cleanUpLocation = "";
    }
    request.setCleanUpLocation(cleanUpLocation.toString());

    var typeOfcleanUp = typeOfCleanUpBox.getValue();
    if (typeOfcleanUp == null) {
      typeOfcleanUp = "";
    }
    request.setTypeOfCleanUp(typeOfcleanUp.toString());

    DBSession.addRequest(request);
    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();

    Navigation.navigate((Screen.SUBMISSION_SUCCESS));
  }
}
