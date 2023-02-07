package edu.wpi.teamb.Navigation;

import edu.wpi.teamb.Bapp;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    removeChild(1, 0);
    try {
      final var resource = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      Bapp.getRootPane().add(loader.load(), 0, 1);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void removeChild(final int row, final int column) {
    ObservableList<Node> children = Bapp.getRootPane().getChildren();
    for (Node node : children) {
      if (Bapp.getRootPane().getRowIndex(node) == row
          && Bapp.getRootPane().getColumnIndex(node) == column) {
        Bapp.getRootPane().getChildren().remove(node);
        break;
      }
    }
  }
}
