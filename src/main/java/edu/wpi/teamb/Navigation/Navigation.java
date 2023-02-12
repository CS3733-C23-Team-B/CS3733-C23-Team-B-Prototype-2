package edu.wpi.teamb.Navigation;

import edu.wpi.teamb.Bapp;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    // removeChild(1, 0);
    try {
      final var r = Bapp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(r);

      Bapp.getRootPane().add(loader.load(), 0, 1);

      final String header = Screen.NAVIGATION.getFilename();
      final String footer = Screen.FOOTER.getFilename();

      if (!filename.equals("views/Profile/SignIn.fxml")) {
        final var resource = Bapp.class.getResource(header);
        final var res = Bapp.class.getResource(footer);
        final FXMLLoader loader2 = new FXMLLoader(resource);
        final FXMLLoader loader3 = new FXMLLoader(res);

        // Bapp.getRootPane().setConstraints(loader2.load(), 0, 0, 1, 1, HPos.CENTER, VPos.TOP);
        // Bapp.getRootPane().add(loader2.load(), 0, 0, 1, 1);
        Bapp.getRootPane().add(loader3.load(), 0, 2, 1, 1);
        // GridPane.setHalignment(loader.load(), HPos.RIGHT); this line as well as line 30 make all
        // other components afterwards not render
      }
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
