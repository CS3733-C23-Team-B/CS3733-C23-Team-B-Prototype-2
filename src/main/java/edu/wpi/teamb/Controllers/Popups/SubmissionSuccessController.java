package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.userinterface.GameScreen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SubmissionSuccessController {
  @FXML private Button backButton;
  @FXML private Button returnButton;

  @FXML private Button playButton;

  public void backButtonClicked() {
    Popup.hidePopup(Screen.SUBMISSION_SUCCESS);
    Navigation.navigate(Screen.HOME);
  }

  public void playButtonClicked() {
    GameScreen.startGame();
  }

  public void requestsButtonClicked() throws IOException {
    Popup.hidePopup(Screen.SUBMISSION_SUCCESS);
    Navigation.navigate(Screen.SUBMITTED_SERVICE_REQUESTS);
  }
}
