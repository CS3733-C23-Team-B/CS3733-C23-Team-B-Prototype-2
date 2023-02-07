package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NavigationController {
  @FXML private Button homeButton;
  @FXML private BorderPane border;

  public void featureOneButtonClicked() throws IOException {
    Navigation.navigate(Screen.LANDING_PAGE);
  }

  public void featureTwoButtonClicked() throws IOException {
    Navigation.navigate(Screen.SANITATION);
  }
// may not need this but i cant tell - Jolie While merging
  public void computerButtonClicked() {
    Navigation.navigate(Screen.COMPUTER_SERVICES);
  }
  public void serviceRequestFormsButtonClicked() throws IOException {
    Navigation.navigate(Screen.LANDING_PAGE);
  }

  public void pathfindingButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATHFINDING);
  }

  public void locationNodeEditorButtonClicked() throws IOException {
    Navigation.navigate(Screen.MAP_DATA_EDITOR);
  }

  public void aboutButtonClicked() {
    Navigation.navigate(Screen.ABOUT);
  }

  public void homeButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void databaseButtonClicked() throws IOException {
    Navigation.navigate(Screen.MAP_DATA_EDITOR);
  }

  public void submittedRequestsButtonClicked() throws IOException {
    Navigation.navigate(Screen.REQUESTS);
  }
  public void mapEditorButtonClicked() throws IOException {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void exitButtonClicked() {
    Stage stage = (Stage) homeButton.getScene().getWindow();
    stage.close();
  }

  public void profileButtonClicked() {
    Navigation.navigate(Screen.PROFILE);
  }

  public void helpClicked() {
    Navigation.navigate(Screen.MAINHELP);
  }

  public void signOutClicked() {
    Navigation.navigate(Screen.SIGN_IN);
    Bapp.getRootPane().setTop(null);
  }
}
