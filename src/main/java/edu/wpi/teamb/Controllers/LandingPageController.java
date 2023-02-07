package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;

public class LandingPageController {
  @FXML private MFXButton patientTransportationButton;
  @FXML private MFXButton sanitationServiceButton;
  @FXML private MFXButton computerServiceButton;

  public void patientTransportationButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATIENT_TRANSPORTATION);
  }

  public void sanitationServiceButtonClicked() throws IOException {
    Navigation.navigate(Screen.SANITATION);
  }

  public void computerServiceButtonClicked() throws IOException {
    Navigation.navigate(Screen.COMPUTER_SERVICES);
  }

  public void creditsButtonClicked() throws IOException {
    Navigation.navigate(Screen.LANDING_PAGE_CREDITS);
  }
}
