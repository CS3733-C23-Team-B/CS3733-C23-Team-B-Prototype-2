package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServiceRequestFormHelpController {
  @FXML private Button backButton;

  public void backButtonClicked() {
    Popup.hidePopup(Screen.SERVICE_REQUEST_FORM_HELP);
  }
}
