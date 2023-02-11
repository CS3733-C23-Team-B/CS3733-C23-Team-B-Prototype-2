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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SigninController {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private CheckBox newAccount;
  @FXML private Label prompt;
  @FXML private Button exitButton;

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

  /**
   * Handles a keypress in one of the two text fields. If it was the enter key, login is attempted
   *
   * @param event the keyEvent
   * @throws IOException
   * @throws SQLException
   */
  public void handleKeyPress(KeyEvent event) throws IOException, SQLException {
    if (event.getCode().equals(KeyCode.ENTER)) signInButtonClicked();
  }

  /**
   * Compares the provided login against the database of logins
   *
   * @return true if the login is valid according to the database, false otherwise
   * @throws SQLException
   */
  public boolean validateLogin() throws SQLException {
    if (usernameField.getText().equals(USER) && passwordField.getText().equals(PASS)) return true;
    else if (users.containsKey(usernameField.getText())
        && users.get(usernameField.getText()).getPassword().equals(passwordField.getText()))
      return true;
    else if (newAccount.isSelected()) {
      if (users.containsKey(usernameField.getText())) return true;
      Login newLogin = new Login(usernameField.getText(), passwordField.getText());
      users.put(usernameField.getText(), newLogin);
      newLogin.insert();
      return true;
    }
    prompt.setText("\tInvalid login");
    prompt.setTextFill(Color.RED);
    usernameField.clear();
    passwordField.clear();
    return false;
  }

  /**
   * Signs into the application and navigates to the home screen
   *
   * @throws IOException
   * @throws SQLException
   */
  public void signInButtonClicked() throws IOException, SQLException {
    if (!validateLogin()) return;
    final String filename = Screen.NAVIGATION.getFilename();
    final String footer = Screen.FOOTER.getFilename();

    try {
      final var resource = Bapp.class.getResource(filename);
      final var res = Bapp.class.getResource(footer);
      final FXMLLoader loader = new FXMLLoader(resource);
      final FXMLLoader loader2 = new FXMLLoader(res);

      // Bapp.getRootPane().setConstraints(loader.load(), 0, 0, 1, 1, HPos.CENTER, VPos.TOP);

      Bapp.getRootPane().add(loader.load(), 0, 0, 1, 1);
      Bapp.getRootPane().add(loader2.load(), 0, 2, 1, 1);
      // GridPane.setHalignment(loader.load(), HPos.RIGHT); this line as well as line 96 make all
      // other components afterwards not render
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }

    Navigation.navigate(Screen.HOME);
    currentUser = users.get(usernameField.getText());
  }

  /** Exits the application */
  public void exitApplication() {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
  }
}
