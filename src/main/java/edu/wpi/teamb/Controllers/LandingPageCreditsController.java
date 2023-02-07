package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;

public class LandingPageCreditsController {
  @FXML private MFXButton backButton;

  public void backButtonClicked() throws IOException {
    Navigation.navigate(Screen.LANDING_PAGE);
  }
}
