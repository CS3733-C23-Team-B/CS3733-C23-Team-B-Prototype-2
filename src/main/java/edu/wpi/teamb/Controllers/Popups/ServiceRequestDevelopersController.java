package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServiceRequestDevelopersController {
  @FXML private Button backButton;

  public void backButtonClicked() {
    Popup.hidePopup(Screen.DEVELOPERS);
  }
}
