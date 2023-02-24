package edu.wpi.teamb.Controllers.Profile;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SigninController {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Label prompt;
  @FXML private Button exitButton;
  @FXML private MFXButton forgot;
  @FXML private Rectangle topRect;

  @FXML private Button backButton;
  public static Login currentUser;

  private Map<String, Login> usersMap = new HashMap<>();
  private static SigninController instance;

  public void initialize() {
    if (instance == null) instance = this;
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
    usersMap = DBSession.getAllLogins();
    boolean found = false;
    for (Login user : usersMap.values()) {
      if (user.getUsername().equals(usernameField.getText())
          && user.getPassword().equals(passwordField.getText())) {
        found = true;
        break;
      }
    }
    if (found) {
      return true;
    }
    prompt.setText("Invalid login");
    forgot.setVisible(true);
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
  public void signInButtonClicked() {
    if (!validateLogin()) return;
    currentUser = usersMap.get(usernameField.getText());
    Navigation.navigate(Screen.HOME);
  }

  /** Exits the application */
  public void exitApplication() {
    Popup.displayPopup(Screen.EXIT_CONFIRMATION);
  }

  @FXML
  private void newAccount() throws IOException {
    Popup.displayPopup(Screen.CREATE_ACCOUNT);
  }

  public void refresh() {
    usersMap = DBSession.getAllLogins();
  }

  public static SigninController getInstance() {
    return instance;
  }

  public static Login getCurrentUser() {
    return currentUser;
  }

  public void forgotClicked() {
    Popup.displayPopup(Screen.FORGOT_PASSWORD);
  }
}
