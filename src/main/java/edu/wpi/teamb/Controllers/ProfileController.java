package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class ProfileController {
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private ToggleButton showPasswordButton;
  @FXML private Button deleteAccountButton;
  private String passwordDisplay;
  private Login user;

  /** Initializes the page by populating the login fields */
  public void initialize() {
    user = SigninController.currentUser;
    usernameText.setText("Username: " + user.getUsername());
    hidePassword();
    if (user.getUsername().equals("")) deleteAccountButton.setDisable(true);
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

  /** Returns to the home page */
  public void backButtonClicked() {
    Navigation.navigate(Screen.HOME);
  }

  /** Deletes the login for the current user and returns to the sign-in page */
  public void deleteAccount() {
    DBSession.delete(user);
    Navigation.navigate(Screen.SIGN_IN);
  }
}
