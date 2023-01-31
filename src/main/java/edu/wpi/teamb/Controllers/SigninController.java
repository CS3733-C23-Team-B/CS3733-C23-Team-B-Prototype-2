package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class SigninController {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private CheckBox newAccount;
  @FXML private Label prompt;

  private final String USER = "bodacious";
  private final String PASS = "badgers";
  public static Login currentUser;
  private Map<String, Login> users;

  {
    try {
      users = Login.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void handleKeyPress(KeyEvent event) throws IOException, SQLException {
    if (event.getCode().equals(KeyCode.ENTER)) signInButtonClicked();
  }

  public boolean validateLogin() throws SQLException {
    if (usernameField.getText().equals(USER) && passwordField.getText().equals(PASS)) return true;
    else if (users.containsKey(usernameField.getText())
        && users.get(usernameField.getText()).getPassword().equals(passwordField.getText()))
      return true;
    else if (newAccount.isSelected()) {
      if (users.containsKey(usernameField.getText())) return true;
      Login newLogin = new Login(usernameField.getText(), passwordField.getText());
      users.put(usernameField.getText(), newLogin);
      return true;
    }
    prompt.setText("\tInvalid login");
    prompt.setTextFill(Color.RED);
    usernameField.clear();
    passwordField.clear();
    return false;
  }

  public void signInButtonClicked() throws IOException, SQLException {
    if (!validateLogin()) return;
    final String filename = Screen.NAVIGATION.getFilename();

    try {
      final var resource = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      Bapp.getRootPane().setTop(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }

    Navigation.navigate(Screen.HOME);
    currentUser = users.get(usernameField.getText());
  }
}
