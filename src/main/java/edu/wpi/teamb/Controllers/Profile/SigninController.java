package edu.wpi.teamb.Controllers.Profile;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Entities.ORMType;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SigninController {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Label prompt;
  @FXML private Button exitButton;
  public static Login currentUser;
  private List<Object> users;
  private static SigninController instance;

  public void initialize() {
    instance = this;
    Thread newThread =
        new Thread(
            () -> {
              users = DBSession.getAll(ORMType.LOGIN);
            });
    newThread.start();
  }

  public void handleKeyPress(KeyEvent event) throws IOException, SQLException {
    if (event.getCode().equals(KeyCode.ENTER)) signInButtonClicked();
  }
  /**
   * Compares the provided login against the database of logins
   *
   * @return true if the login is valid according to the database, false otherwise
   */
  public boolean validateLogin() {
    boolean found = false;
    for (Object user : users) {
      Login u = (Login) user;
      if (u.getUsername().equals(usernameField.getText())
          && u.getPassword().equals(passwordField.getText())) {
        found = true;
        break;
      }
    }
    if (found) {
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
  public void signInButtonClicked() throws SQLException {
    if (!validateLogin()) return;

    Navigation.navigate(Screen.HOME);

    for (Object user : users) {
      Login u = (Login) user;
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

  @FXML
  private void newAccount() throws IOException {
    Stage newWindow = new Stage();
    final String filename = Screen.CREATE_ACCOUNT.getFilename();
    try {
      final var resource = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      Scene scene = new Scene(loader.load(), 800, 487);
      newWindow.setScene(scene);
      newWindow.show();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  public void refresh() {
    users = DBSession.getAll(ORMType.LOGIN);
  }

  public static SigninController getInstance() {
    return instance;
  }
}
