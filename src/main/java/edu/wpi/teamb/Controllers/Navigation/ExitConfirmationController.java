package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;

public class ExitConfirmationController {
  @FXML MFXButton yesButton;
  @FXML MFXButton noButton;

  public void noButtonClicked() throws IOException {
    Popup.hidePopup(Screen.EXIT_CONFIRMATION);
  }

  public void yesButtonClicked() throws IOException {
    Bapp.getPrimaryStage().close();
  }
}
