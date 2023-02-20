package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class NavigationController {
  @FXML private MFXButton homeButton;
  @FXML private MFXButton forms;
  @FXML private MFXButton map;
  @FXML private MFXButton request;
  @FXML private MFXButton path;

  public void initialize() {
    Platform.runLater(
        () -> {
          resetButtons();
        });
  }

  public void setActiveButton(MFXButton button) {
    resetButtons();
    button.setStyle("-fx-background-color: #6D9BF8;");
    button.setTextFill(Paint.valueOf("white"));
  }

  public void resetButtons() {
    homeButton.setStyle("-fx-background-color: transparent;");
    forms.setStyle("-fx-background-color: transparent;");
    map.setStyle("-fx-background-color: transparent;");
    request.setStyle("-fx-background-color: transparent;");
    path.setStyle("-fx-background-color: transparent;");

    homeButton.setTextFill(Paint.valueOf("#c5d3ea"));
    forms.setTextFill(Paint.valueOf("#c5d3ea"));
    map.setTextFill(Paint.valueOf("#c5d3ea"));
    request.setTextFill(Paint.valueOf("#c5d3ea"));
    path.setTextFill(Paint.valueOf("#c5d3ea"));
  }

  public void serviceRequestFormsButtonClicked() throws IOException {
    Navigation.navigate(Screen.SERVICE_REQUEST_SYSTEMS);
    setActiveButton(forms);
  }

  public void pathfindingButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATHFINDING);
    setActiveButton(path);
  }

  public void aboutButtonClicked() {
    Navigation.navigate(Screen.ABOUT);
    resetButtons();
  }

  public void creditsButtonClicked() {
    Navigation.navigate(Screen.CREDITS);
    // resetButtons();
  }

  public void homeButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
    resetButtons();
  }

  public void submittedRequestsButtonClicked() throws IOException {
    Navigation.navigate(Screen.SUBMITTED_SERVICE_REQUESTS);
    setActiveButton(request);
  }

  public void mapEditorButtonClicked() throws IOException {
    Navigation.navigate(Screen.MAP_EDITOR);
    setActiveButton(map);
  }

  public void exitButtonClicked() {
    resetButtons();
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
    resetButtons();
    Navigation.navigate(Screen.PROFILE);
  }

  public void helpClicked() {
    resetButtons();
    Navigation.navigate(Screen.MAINHELP);
  }

  public void signOutClicked() {
    Navigation.navigate(Screen.SIGN_IN);
    Bapp.getRootPane().setTop(null);
  }
}
