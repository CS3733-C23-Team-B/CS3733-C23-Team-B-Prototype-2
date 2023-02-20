package edu.wpi.teamb.Navigation;

import edu.wpi.teamb.Bapp;
import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();
    final BorderPane rootPane = Bapp.getRootPane();

    // Show loading indicator
    if (screen == Screen.MAP_EDITOR || screen == Screen.PATHFINDING) {
      final ProgressIndicator progressIndicator = new ProgressIndicator();
      progressIndicator.setMaxSize(100, 100);
      progressIndicator.setStyle("-fx-progress-color: #21357E;");
      rootPane.setCenter(progressIndicator);
    }
    try {
      if (!filename.equals("views/Profile/SignIn.fxml")) {
        final String header = Screen.NAVIGATION.getFilename();
        final var resource = Bapp.class.getResource(header);
        final FXMLLoader loader2 = new FXMLLoader(resource);
        final Parent headerRoot = loader2.load();
        if (rootPane.getTop() == null) Platform.runLater(() -> rootPane.setTop(headerRoot));
      }
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
    // Load FXML files in background thread
    final Task<Void> loadTask =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            final var r = Bapp.class.getResource(filename);
            final FXMLLoader loader = new FXMLLoader(r);
            final Parent root = loader.load();
            Platform.runLater(() -> rootPane.setCenter(root));
            return null;
          }
        };
    new Thread(loadTask).start();
  }
}
