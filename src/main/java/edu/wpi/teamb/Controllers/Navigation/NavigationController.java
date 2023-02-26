package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class NavigationController {
  @FXML private MFXButton homeButton;
  @FXML private MFXButton forms;
  @FXML private MFXButton map;
  @FXML private MFXButton request;
  @FXML private MFXButton path;

  public void initialize() {
    Platform.runLater(
        () -> {
          if (SigninController.getInstance() != null) {
            if (!SigninController.getInstance().currentUser.getAdmin()) map.setVisible(false);
          }
          resetButtons();
        });
  }

  public void setActiveButton(MFXButton button) {
    resetButtons();
    //    button.setStyle("-fx-background-color: #6D9BF8;");
    //    button.setTextFill(Paint.valueOf("white"));
  }

  public void resetButtons() {
    //    forms.setStyle("-fx-background-color: transparent;");
    //    map.setStyle("-fx-background-color: transparent;");
    //    request.setStyle("-fx-background-color: transparent;");
    //    path.setStyle("-fx-background-color: transparent;");
    //
    //    homeButton.setTextFill(Paint.valueOf("#c5d3ea"));
    //    forms.setTextFill(Paint.valueOf("#c5d3ea"));
    //    map.setTextFill(Paint.valueOf("#c5d3ea"));
    //    request.setTextFill(Paint.valueOf("#c5d3ea"));
    //    path.setTextFill(Paint.valueOf("#c5d3ea"));
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
    Navigation.navigate(Screen.ABOUT_PAGE);
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
    Popup.displayPopup(Screen.EXIT_CONFIRMATION);
  }

  public void profileButtonClicked() {
    resetButtons();
    Navigation.navigate(Screen.PROFILE);
  }

  public void helpClicked() {
    resetButtons();
    Popup.displayPopup(Screen.MAINHELP);
  }

  public void signOutClicked() {
    Navigation.navigate(Screen.SIGN_IN);
    Bapp.getRootPane().setTop(null);
  }
}
