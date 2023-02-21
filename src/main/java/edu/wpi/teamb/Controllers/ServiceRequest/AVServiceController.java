package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.AudioVideoRequest;
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

public class AVServiceController extends BaseRequestController {
  ObservableList<String> typeOfEquipment =
      FXCollections.observableArrayList("TV", "Radio", "iPad", "Headphones");

  @FXML private MFXFilterComboBox locationBox;
  @FXML private MFXFilterComboBox<String> typeOfEquipmentBox;

  @FXML
  @Override
  public void initialize() {
    // initialization goes here
    // Create list of components; additionalNotesField MUST be last
    Control[] ctrl = {
      locationBox, urgencyBox, typeOfEquipmentBox, assignedStaffBox, additionalNotesField
    };
    components = new ArrayList<>(Arrays.asList(ctrl));
    textFields = new ArrayList<>();
    choiceBoxes = new ArrayList<>();
    locationBox.setItems(getLocations());

    // Create lists of text fields and choice boxes
    for (Control c : components) {
      if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
      if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
    }
    typeOfEquipmentBox.setItems(typeOfEquipment);

    super.initialize();
  }

  @FXML
  @Override
  public void submitButtonClicked() throws IOException {
    // handle retrieving values and saving

    AudioVideoRequest request = new AudioVideoRequest();

    super.submit(request);

    request.setLocation(locationBox.getText());

    var equipment = typeOfEquipmentBox.getValue();
    if (equipment == null) {
      equipment = "";
    }
    request.setAVType(equipment.toString());

    request.setRequestType(RequestType.AUDOVISUAL);
    DBSession.addRequest(request);

    // may need to clear fields can be done with functions made for clear
    clearButtonClicked();

    Popup.displayPopup(Screen.SUBMISSION_SUCCESS);
  }
}
