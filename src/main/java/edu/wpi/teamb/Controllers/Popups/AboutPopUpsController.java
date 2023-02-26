package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;

public class AboutPopUpsController {
  @FXML protected MFXButton backButton;

  public void backButtonClicked() throws IOException {
    Popup.hidePopup(Screen.ABOUT_PAGE);
    Navigation.navigate(Screen.ABOUT_PAGE);
  }
}
