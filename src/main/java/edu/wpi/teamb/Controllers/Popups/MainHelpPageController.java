package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainHelpPageController {
  @FXML private Button backButton;

  public void backButtonClicked() {
    if (SigninController.currentUser.getAdmin()) {
      Popup.hidePopup(Screen.ADMINMAINHELP);
    } else {
      Popup.hidePopup(Screen.STAFFMAINHELP);
    }
  }
}
