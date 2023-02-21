package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class NavigationController {
  @FXML private MFXButton homeButton;
  @FXML private MFXButton forms;
  @FXML private MFXButton map;
  @FXML private MFXButton request;
  @FXML private MFXButton path;
  @FXML private Label timeLabel;
  @FXML private Label dateLabel;
  @FXML private HBox buttonsBox;

  private static final PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");
  private final ObjectProperty<MFXButton> selectedButton = new SimpleObjectProperty<>();

  public void initialize() {

    Platform.runLater(
        () -> {
          resetButtons();
          if (SigninController.getInstance() != null) {
            if (!SigninController.getInstance().currentUser.getAdmin()) map.setVisible(false);
          }
          //          if (!(Navigation.currentScreen == Screen.HOME)) {
          //            LocalDate currentDate = LocalDate.now();
          //            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd,
          // yyyy");
          //            String formattedDate = currentDate.format(formatter);
          //
          //            Timeline timeline =
          //                new Timeline(
          //                    new KeyFrame(
          //                        Duration.seconds(0),
          //                        event -> {
          //                          LocalDateTime currentTime = LocalDateTime.now();
          //                          DateTimeFormatter timefmt =
          // DateTimeFormatter.ofPattern("h:mm:ss a");
          //                          timeLabel.setText(currentTime.format(timefmt));
          //                        }),
          //                    new KeyFrame(Duration.seconds(1)));
          //            timeline.setCycleCount(Timeline.INDEFINITE);
          //            timeline.play();
          //            dateLabel.setText(formattedDate);
          //          }
          selectedButton.addListener(
              (obs, oldSelection, newSelection) -> {
                if (oldSelection != null) {
                  oldSelection.pseudoClassStateChanged(SELECTED, false);
                }
                if (newSelection != null) {
                  newSelection.pseudoClassStateChanged(SELECTED, true);
                }
              });
        });
  }

  public void setActiveButton(MFXButton button) {
    selectedButton.set(button);
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
    Navigation.navigate(Screen.MAINHELP);
  }

  public void signOutClicked() {
    Navigation.navigate(Screen.SIGN_IN);
    Bapp.getRootPane().setTop(null);
  }
}
