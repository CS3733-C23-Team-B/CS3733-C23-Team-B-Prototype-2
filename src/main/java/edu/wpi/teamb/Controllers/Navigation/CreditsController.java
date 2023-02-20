package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class CreditsController {
  @FXML private MFXButton backButton;

  public void backButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  // Images
  public void link1Clicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop()
        .browse(
            new URI(
                "https://www.massgeneralbrigham.org/en/patient-care/international/about/brigham-and-womens-hospital"));
  }

  public void link2Clicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop()
        .browse(
            new URI("https://innovation.massgeneralbrigham.org/about/about-mass-general-brigham"));
  }

  // Icons

  // Profile Icon
  public void link3Clicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.flaticon.com/free-icons/ui"));
  }

  // Stretcher Icon
  public void stretcherClicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.flaticon.com/free-icons/stretcher"));
  }

  // Cleaning Icon
  public void cleaningCLicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.flaticon.com/free-icons/cleaning"));
  }

  // Security Icon
  public void securityClicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.flaticon.com/free-icons/security"));
  }

  // Laptop Icon
  public void laptopClicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.flaticon.com/free-icons/laptop "));
  }

  // Audio/Visual Icon
  public void audioVisualClicked(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.flaticon.com/free-icons/video-and-audio"));
  }
}
