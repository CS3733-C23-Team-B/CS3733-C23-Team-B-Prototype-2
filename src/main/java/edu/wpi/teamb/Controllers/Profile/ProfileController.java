package edu.wpi.teamb.Controllers.Profile;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class ProfileController {
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private ToggleButton showPasswordButton;
  @FXML private MFXButton deleteAccountButton;
  @FXML private MFXTextField firstName;
  @FXML private MFXTextField lastName;
  @FXML private MFXTextField email;
  @FXML MFXButton save;
  @FXML Label messege;
  @FXML VBox vBox;

  @FXML MFXButton adminButton;
  private String passwordDisplay;
  private Login user;

  /** Initializes the page by populating the login fields */
  public void initialize() {
    user = SigninController.currentUser;
    firstName.setPromptText(user.getFirstname());
    lastName.setPromptText(user.getLastname());
    email.setPromptText(user.getEmail());
    save.setOnAction(e -> saveClicked());
    usernameText.setText("Username: " + user.getUsername());
    hidePassword();
    if (user.getUsername().equals("")) deleteAccountButton.setDisable(true);
    if (user.getAdmin() == true) {
      adminButton.setVisible(true);
      adminButton.setDisable(false);
      adminButton.setOnAction(e -> viewAllUsers());
    }
  }

  private void viewAllUsers() {
    Navigation.navigate(Screen.ALL_USERS);
  }

  private void saveClicked() {
    String newFirstName = firstName.getText().isEmpty() ? user.getFirstname() : firstName.getText();
    String newLastName = lastName.getText().isEmpty() ? user.getLastname() : lastName.getText();
    String newEmail = email.getText().isEmpty() ? user.getEmail() : email.getText();

    user.setFirstname(newFirstName);
    user.setLastname(newLastName);
    user.setEmail(newEmail);

    DBSession.updateUser(
        user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());
    messege.setText("Update Success!");
    messege.setTextFill(Paint.valueOf("GREEN"));

    resetFields();
  }

  private void resetFields() {
    firstName.setPromptText(user.getFirstname());
    firstName.clear();
    lastName.setPromptText(user.getLastname());
    lastName.clear();
    email.setPromptText(user.getEmail());
    email.clear();
  }

  /** Handles a press of the show password button by toggling on/off password visibility */
  public void handleButtonPress() {
    if (showPasswordButton.isSelected()) showPassword();
    else hidePassword();
  }

  /** Updates that text displayed in the password text box */
  private void updatePasswordText() {
    passwordText.setText(passwordDisplay);
  }

  /** Sets the password as the actual password */
  public void showPassword() {
    passwordDisplay = "Password:  " + user.getPassword();
    updatePasswordText();
  }

  /** Sets the password text to asterisks representing the hidden password */
  public void hidePassword() {
    passwordDisplay = "Password:  ";
    for (int i = 0; i < user.getPassword().length(); i++) passwordDisplay += "*";
    updatePasswordText();
  }
  /** Deletes the login for the current user and returns to the sign-in page */
  public void deleteAccount() {
    DBSession.deleteLogin(user);
    Navigation.navigate(Screen.SIGN_IN);
  }
}
