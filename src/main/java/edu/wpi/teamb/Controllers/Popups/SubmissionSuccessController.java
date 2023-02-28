package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Controllers.Navigation.NavigationController;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SubmissionSuccessController {
  @FXML private Button backButton;
  @FXML private Button returnButton;
  @FXML private Button newrequestsButton;

  public void backButtonClicked() {
    Popup.hidePopup(Screen.SUBMISSION_SUCCESS);
    Navigation.navigate(Screen.HOME);
    NavigationController.getInstance().resetButtons();
  }

  public void requestsButtonClicked() throws IOException {
    Popup.hidePopup(Screen.SUBMISSION_SUCCESS);
    Navigation.navigate(Screen.SUBMITTED_SERVICE_REQUESTS);
  }

  public void newrequestsButtonClicked() {
    Popup.hidePopup(Screen.SUBMISSION_SUCCESS);
    Navigation.navigate(Screen.SERVICE_REQUEST_SYSTEMS);
    NavigationController.getInstance().resetButtons();
  }
}
