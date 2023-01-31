package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {
  @FXML Button patientTransportationButton;

  @FXML Button sanitationServiceButton;

  public void patientTransportationButtonClicked() throws IOException {
    Navigation.navigate(Screen.PATIENT_TRANSPORTATION);
  }

  public void sanitationServiceButtonClicked() throws IOException {
    Navigation.navigate(Screen.SANITATION);
  }
}
