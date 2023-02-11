package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.input.MouseEvent;

public class FooterController {

  public void helpClicked() {
    Navigation.navigate(Screen.MAINHELP);
  }

  public void youtubeClick(MouseEvent mouseEvent) throws URISyntaxException, IOException {
    System.out.println("youtube");
    Desktop.getDesktop().browse(new URI("https://youtu.be/3FbYcu5NhtQ?t=199"));
  }
}
