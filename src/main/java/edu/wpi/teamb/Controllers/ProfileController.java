package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.sql.SQLException;
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

  public void initialize() {
    user = SigninController.currentUser;
    usernameText.setText("Username: " + user.getUsername());
    hidePassword();
    if (user.getUsername().equals("")) deleteAccountButton.setDisable(true);
  }

  public void handleButtonPress() {
    if (showPasswordButton.isSelected()) showPassword();
    else hidePassword();
  }

  private void updatePasswordText() {
    passwordText.setText(passwordDisplay);
  }

  public void showPassword() {
    passwordDisplay = "Password:  " + user.getPassword();
    updatePasswordText();
  }

  public void hidePassword() {
    passwordDisplay = "Password:  ";
    for (int i = 0; i < user.getPassword().length(); i++) passwordDisplay += "*";
    updatePasswordText();
  }

  public void backButtonClicked() {
    Navigation.navigate(Screen.HOME);
  }

  public void deleteAccount() {
    try {
      user.delete();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    Navigation.navigate(Screen.SIGN_IN);
    Bapp.getRootPane().setTop(null);
  }
}
