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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {
  @FXML private Button homeButton;
  @FXML private BorderPane border;
  @FXML private Button signInButton;

  @FXML private Button deleteAccountButton;
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private CheckBox newAccount;
  @FXML private Label prompt;

  private final String USER = "bodacious";
  private final String PASS = "badgers";

  private Map<String, Login> users;

  {
    try {
      users = Login.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void initialize() {
    if (deleteAccountButton == null) return;
    deleteAccountButton.setDisable(true);
  }

  public void handleKeyPress(KeyEvent event) throws IOException, SQLException {
    if (event.getCode().equals(KeyCode.ENTER)) signInButtonClicked();
    else checkEnableDeleteAccountButton();
  }

  public boolean validateLogin() throws SQLException {
    if (usernameField.getText().equals(USER) && passwordField.getText().equals(PASS)) return true;
    else if (users.containsKey(usernameField.getText())
        && users.get(usernameField.getText()).getPassword().equals(passwordField.getText()))
      return true;
    else if (newAccount.isSelected()) {
      if (users.containsKey(usernameField.getText())) return true;
      Login newLogin = new Login(usernameField.getText(), passwordField.getText());
      newLogin.insert();
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
  }

  public void featureOneButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATIENT_TRANSPORTATION);
  }

  public void featureTwoButtonClicked() throws IOException {
    Navigation.navigate(Screen.SANITATION);
  }

  public void pathfindingClicked() throws IOException {
    Navigation.navigate(Screen.PATHFINDING);
  }

  public void homeButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void databaseButtonClicked() throws IOException {
    Navigation.navigate(Screen.DATABASE_UI);
  }

  public void requestsButtonClicked() throws IOException {
    Navigation.navigate(Screen.REQUESTS);
  }

  public void exitButtonClicked() {
    Stage stage = (Stage) homeButton.getScene().getWindow();
    stage.close();
  }

  public void signOutClicked() {
    Navigation.navigate(Screen.SIGN_IN);
    Bapp.getRootPane().setTop(null);
  }

  public void deleteAccountClicked() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    Login user = users.get(username);
    if (user.getPassword().equals(password)) {
      try {
        user.delete();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    usernameField.clear();
    passwordField.clear();
    users.remove(username);
    prompt.setText("Account deleted");
    deleteAccountButton.setDisable(true);
  }

  public void checkEnableDeleteAccountButton() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.equals("") && password.equals("")) {
      deleteAccountButton.setDisable(true);
      return;
    }

    boolean enable =
        (users.containsKey(username) && users.get(username).getPassword().equals(password));
    deleteAccountButton.setDisable(!enable);
  }
}
