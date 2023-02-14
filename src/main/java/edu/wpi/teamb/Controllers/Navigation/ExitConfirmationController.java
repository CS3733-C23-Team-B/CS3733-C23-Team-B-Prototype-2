package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ExitConfirmationController {
  @FXML MFXButton yesButton;
  @FXML MFXButton noButton;

  public void noButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void yesButtonClicked() throws IOException {
    Stage stage = (Stage) yesButton.getScene().getWindow();
    stage.close();
  }
}
