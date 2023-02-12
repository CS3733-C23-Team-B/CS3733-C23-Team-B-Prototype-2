package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class CreateAccountController {

  @FXML MFXTextField firstNameField;
  @FXML MFXTextField lastNameField;
  @FXML MFXTextField emailField;
  @FXML MFXTextField usernameField;
  @FXML MFXTextField passwordField;
  @FXML MFXButton cancelButton;
  @FXML Label notificationText;

  private Map<String, Login> usersMap = new HashMap<>();
  private List<Login> users;

  public void cancelClicked(ActionEvent actionEvent) {
    Stage s = (Stage) cancelButton.getScene().getWindow();
    s.close();
  }

  public void submitClicked(ActionEvent actionEvent) {
    if (!firstNameField.getText().equals("")
        && !lastNameField.getText().equals("")
        && !emailField.getText().equals("")
        && !usernameField.getText().equals("")
        && !passwordField.getText().equals("")) {
      usersMap = DBSession.getLogins();
      usersMap.forEach(
          (key, value) -> {
            users.add(value);
          });
      boolean exists = false;
      // check if username already exists
      for (Object user : users) {
        Login u = (Login) user;
        if (u.getUsername().equals(usernameField.getText())) {
          notificationText.setTextFill(Paint.valueOf("RED"));
          notificationText.setText("account with that username already exists");
          exists = true;
          break;
        }
      }
      if (!exists) {
        notificationText.setTextFill(Color.GREEN);
        notificationText.setText("creating new login");
        Login newLogin =
            new Login(
                usernameField.getText(),
                passwordField.getText(),
                emailField.getText(),
                firstNameField.getText(),
                lastNameField.getText());
        users.add(newLogin);
        DBSession.addLogin(newLogin);
        Stage s = (Stage) cancelButton.getScene().getWindow();
        s.close();
        SigninController.getInstance().refresh();
      }
    } else {
      notificationText.setTextFill(Color.RED);
      notificationText.setText("Please fill in all fields");
    }
  }
}
