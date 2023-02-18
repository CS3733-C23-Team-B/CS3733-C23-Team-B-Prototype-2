package edu.wpi.teamb.Navigation;

import edu.wpi.teamb.Bapp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    Bapp.getRootPane().getChildren().clear();

    try {
      final var r = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(r);

      Bapp.getRootPane().setCenter(loader.load());

      final String header = Screen.NAVIGATION.getFilename();
      final String footer = Screen.FOOTER.getFilename();

      if (!filename.equals("views/Profile/SignIn.fxml")) {
        final var resource = Bapp.class.getResource(header);
        // final var res = Bapp.class.getResource(footer);
        final FXMLLoader loader2 = new FXMLLoader(resource);
        // final FXMLLoader loader3 = new FXMLLoader(res);

        Bapp.getRootPane().setTop(loader2.load());
        // Bapp.getRootPane().setBottom(loader3.load());
      }
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
