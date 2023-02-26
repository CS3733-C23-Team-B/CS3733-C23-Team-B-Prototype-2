package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AboutPopUpsController {
  @FXML protected MFXButton backButton;

  public void backButtonClicked() throws IOException {
    Popup.hidePopup(Screen.ABOUT_PAGE);
    Navigation.navigate(Screen.ABOUT_PAGE);
  }

}
