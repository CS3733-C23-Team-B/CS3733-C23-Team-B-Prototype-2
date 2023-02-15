package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ExitConfirmationController {
  @FXML MFXButton yesButton;
  @FXML MFXButton noButton;

  public void noButtonClicked() throws IOException {
    Stage stage = (Stage) noButton.getScene().getWindow();
    stage.close();
  }

  public void yesButtonClicked() throws IOException {
    Bapp.getPrimaryStage().close();
    Stage stage = (Stage) yesButton.getScene().getWindow();
    stage.close();
  }
}
