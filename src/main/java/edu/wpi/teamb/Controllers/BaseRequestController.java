package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class BaseRequestController {
    // JavaFX components
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField employeeIDField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox urgencyBox;
    @FXML private TextField additionalNotesField;
    @FXML private Button cancelButton;
    @FXML private Button helpButton;
    @FXML private Button clearButton;
    @FXML private Button submitButton;

    // Choice-box options
    private ObservableList<String> urgencyOptions =
            FXCollections.observableArrayList("Low", "Moderate", "High", "Requires Immediate Attention");

    // List of all text fields and choice boxes for flexibility; when adding new input components to
    // form, add to this list
    private ArrayList<Control> components;
    private ArrayList<TextField> textFields;
    private ArrayList<ChoiceBox> choiceBoxes;

    private Screen helpScreen;

    /** Initialize the page by instantiating 3 arrays of all components,
     * text field components, and choice-box components,
     * as well as declaring choice-box options */
    @FXML
    public void initialize() {
        urgencyBox.setItems(urgencyOptions);
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
        Navigation.navigate(helpScreen);
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
     * Store the data from the form in a Java object and show confirmation screen
     *
     * @throws IOException
     */
    public void submitButtonClicked() throws IOException {
        // stub
    }

}
