package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KioskView {

  private GridPane centerPane = new GridPane();
  @FXML GridPane center;

  public void initialize() {
    popup();
  }

  private void popup() {
    center.add(centerPane, 0, 0);
    locationPopup();
    buttonsPopup();
  }

  private void locationPopup() {
    VBox vBox = new VBox();

    MFXButton message = new MFXButton("Upcoming Location Names");

    centerPane.addRow(1, message);
  }

  private void buttonsPopup() {
    VBox vBox = new VBox();
    HBox hBox = new HBox();

    MFXButton message = new MFXButton("Message Goes Here");

    MFXButton b = new MFXButton("sign in button");
    b.setOnAction(
        e -> {
          signIn();
        });

    MFXButton m = new MFXButton("Open Map");
    m.setOnAction(
        e -> {
          try {
            openMap();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    hBox.getChildren().add(b);
    hBox.getChildren().add(m);
    hBox.setPrefWidth(100);
    hBox.setSpacing(20);
    vBox.getChildren().add(message);

    vBox.getChildren().add(hBox);
    b.setStyle("-fx-background-color: blue");
    m.setStyle("-fx-background-color: green");

    centerPane.addRow(1, vBox);
  }

  private void openMap() throws IOException {
    final String filename = Screen.PATHFINDING.getFilename();
    final BorderPane rootPane = Bapp.getRootPane();
    final var r = Bapp.class.getResource(filename);
    final FXMLLoader loader = new FXMLLoader(r);
    final Parent root = loader.load();

    final String filename1 = Screen.SIGN_IN.getFilename();
    final var r1 = Bapp.class.getResource(filename1);
    final FXMLLoader loader1 = new FXMLLoader(r1);
    final Parent root1 = loader1.load();
    MFXButton b = new MFXButton();
    b.setText("Back to Sign In");

    Platform.runLater(
        () -> {
          rootPane.setCenter(root);
          rootPane.setTop(b);
        });

    b.setOnAction(
        e -> {
          Platform.runLater(
              () -> {
                rootPane.setCenter(null);
                rootPane.setTop(null);
                rootPane.setCenter(root1);
              });
        });
  }

  public void signIn() {
    Navigation.navigate(Screen.SIGN_IN);
  }
}
