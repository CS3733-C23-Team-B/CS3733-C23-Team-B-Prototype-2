package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class ProfileController {
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private ToggleButton showPasswordButton;
  @FXML private Button deleteAccountButton;

  @FXML private MFXTextField firstName;
  @FXML private MFXTextField lastName;
  @FXML private MFXTextField email;
  @FXML MFXButton save;
  @FXML Label messege;
  @FXML AnchorPane anchor;
  private String passwordDisplay;
  private Login user;

  /** Initializes the page by populating the login fields */
  public void initialize() {
    user = SigninController.currentUser;
    firstName.setText(user.getFirstname());
    lastName.setText(user.getLastname());
    email.setText(user.getEmail());
    save.setOnAction(e -> saveClicked());
    usernameText.setText("Username: " + user.getUsername());
    hidePassword();
    if (user.getUsername().equals("")) deleteAccountButton.setDisable(true);
    if (user.getAdmin() == true) {
      MFXButton adminButton = new MFXButton();
      adminButton.setText("View All Users");
      adminButton.setLayoutX(25);
      adminButton.setLayoutY(490);
      adminButton.setTextFill(Paint.valueOf("BLUE"));
      adminButton.setOnAction(e -> viewAllUsers());
      anchor.getChildren().add(adminButton);
    }
  }

  private void viewAllUsers() {
    Navigation.navigate(Screen.ALL_USERS);
  }

  private void saveClicked() {
    user.setFirstname(firstName.getText());
    user.setLastname(lastName.getText());
    user.setEmail(email.getText());
    DBSession.updateUser(
        user.getUsername(), firstName.getText(), lastName.getText(), email.getText());
    messege.setText("Update Success!");
    messege.setTextFill(Paint.valueOf("GREEN"));
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
    DBSession.delete(user);
    Navigation.navigate(Screen.SIGN_IN);
  }
}
