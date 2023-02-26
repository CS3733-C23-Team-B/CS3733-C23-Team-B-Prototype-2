package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;

public class AboutPageController {

  @FXML MFXButton backButton;

  public void backButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void kavyaManiClicked() {
    Popup.displayPopup(Screen.KAVYA_MANI);
  }

  public void cierraOGradyClicked() {
    Popup.displayPopup(Screen.CIERRA_OGRADY);
  }

  public void adrianJohnsonClicked() {
    Popup.displayPopup(Screen.ADRIAN_JOHNSON);
  }

  public void jolieWaltsClicked() {
    Popup.displayPopup(Screen.JOLIE_WALTS);
  }

  public void lukeGradyClicked() {
    Popup.displayPopup(Screen.LUKE_GRADY);
  }

  public void jackLafordClicked() {
    Popup.displayPopup(Screen.JACK_LAFORD);
  }

  public void christinaAubeClicked() {
    Popup.displayPopup(Screen.CHRISTINA_AUBE);
  }
}
