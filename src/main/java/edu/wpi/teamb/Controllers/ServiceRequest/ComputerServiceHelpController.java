package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ComputerServiceHelpController {

  @FXML private Button backButton;

  public void backButtonClicked() throws IOException {
    Navigation.navigate(Screen.COMPUTER_SERVICES);
  }
}
