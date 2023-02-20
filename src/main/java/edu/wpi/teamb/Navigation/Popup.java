package edu.wpi.teamb.Navigation;

import edu.wpi.teamb.Bapp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Popup {
  public static void displayPopup(final Screen popup) {
    final String filename = popup.getFilename();
    final StackPane stackPane = Bapp.getStackPane();

    try {
      final var res = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(res);
      final Parent root = loader.load();
      root.setId("popup");
      stackPane.getChildren().add(root);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void hidePopup(final Screen popup) {
    final String filename = popup.getFilename();
    final StackPane stackPane = Bapp.getStackPane();

    try {
      final var res = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(res);
      final Parent root = loader.load();
      root.setId("popup");
      stackPane.getChildren().remove(1);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
