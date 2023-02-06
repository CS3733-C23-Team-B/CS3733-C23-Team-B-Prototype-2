package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.LogIn;
import edu.wpi.teamb.Entities.ORMType;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
  public static LogIn currentUser;
  private List<Object> users;

  public void initialize() {
    users = DBSession.getAll(ORMType.LOGIN);
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
    boolean found = false;
    for (Object user : users) {
      LogIn u = (LogIn) user;
      if (u.getUsername().equals(usernameField.getText())
          && u.getPassword().equals(passwordField.getText())) {
        found = true;
        break;
      }
    }
    if (found) {
      return true;
    } else if (newAccount.isSelected()) {
      for (Object user : users) {
        LogIn u = (LogIn) user;
        if (u.getUsername().equals(usernameField.getText())) {
          found = true;
          break;
        }
      }
      if (!found) {
        LogIn newLogin = new LogIn(usernameField.getText(), passwordField.getText());
        users.add(newLogin);
        DBSession.addORM(newLogin);
        return true;
      }
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

      Bapp.getRootPane().setTop(loader.load());
      Bapp.getRootPane().setBottom(loader2.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }

    Navigation.navigate(Screen.HOME);

    for (Object user : users) {
      LogIn u = (LogIn) user;
      if (u.getUsername().equals(usernameField.getText())) {
        currentUser = u;
        break;
      }
    }
  }

  /** Exits the application */
  public void exitApplication() {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
  }
}
