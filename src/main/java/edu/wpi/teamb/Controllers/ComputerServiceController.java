package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Entities.ComputerRequest;
import edu.wpi.teamb.Entities.SanitationRequest;
import edu.wpi.teamb.Navigation.Navigation;
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

public class ComputerServiceController extends BaseRequestController {
    // Lists for checkboxes
    ObservableList<String> typeOfRepairList =
            FXCollections.observableArrayList("New Hardware", "Broken Hardware", "Technical Issues");
    ObservableList<String> typeOfDevice =
            FXCollections.observableArrayList("Computer", "Phone", "")
    @FXML private TextField repairLocationField;
    @FXML private ChoiceBox typeOfRepairBox;

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
                repairLocationField,
                urgencyBox,
                typeOfRepairBox,
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
        typeOfRepairBox.setItems(typeOfRepairList);

        helpScreen = Screen.COMPUTER_HELP;
        super.initialize();
    }

    @FXML
    @Override
    public void submitButtonClicked() throws IOException {
        // handle retrieving values and saving

        ComputerRequest request = ComputerRequest.getInstance();

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

        request.setRepairLocation(repairLocationField.getText());

        var typeOfrepair = typeOfRepairBox.getValue();
        if (typeOfrepair == null) {
            typeOfrepair = "";
        }
        request.setTypeOfrepair(typeOfrepair.toString());

        // may need to clear fields can be done with functions made for clear
        clearButtonClicked();

        Navigation.navigate((Screen.SUBMISSION_SUCCESS));
    }
}
