package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NavigationController {
  @FXML private Button homeButton;

  public void serviceRequestFormsButtonClicked() throws IOException {
    Navigation.navigate(Screen.SERVICE_REQUEST_SYSTEMS);
  }

  public void pathfindingButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATHFINDING);
  }

  public void aboutButtonClicked() {
    Navigation.navigate(Screen.ABOUT_PAGE);
  }

  public void homeButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void submittedRequestsButtonClicked() throws IOException {
    Navigation.navigate(Screen.SUBMITTED_SERVICE_REQUESTS);
  }

  public void mapEditorButtonClicked() throws IOException {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void exitButtonClicked() {
    Stage newWindow = new Stage();
    final String filename = Screen.EXIT_CONFIRMATION.getFilename();
    try {
      final var resource = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      Scene scene = new Scene(loader.load(), 700, 300);
      newWindow.setScene(scene);
      newWindow.show();
    } catch (NullPointerException | IOException e) {
      e.printStackTrace();
    }
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
