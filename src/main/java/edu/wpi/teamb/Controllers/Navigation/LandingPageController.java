package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class LandingPageController {
  @FXML VBox mainVbox;
  @FXML MFXButton transButton;
  @FXML MFXButton saniButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton AVButton;

  public void initialize() {
    mainVbox.setPadding(new Insets(20, 20, 0, 20));
  }

  public void makeTrans() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    // VBox vbox = (VBox) loader.load();
    mainVbox.getChildren().add(loader.load());
  }

  public void makeSani() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    VBox vbox = (VBox) loader.load();
    mainVbox.getChildren().setAll(vbox);
  }

  public void makeSec() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    VBox vbox = (VBox) loader.load();
    mainVbox.getChildren().setAll(vbox);
  }

  public void makeCom() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    VBox vbox = (VBox) loader.load();
    mainVbox.getChildren().setAll(vbox);
  }

  public void makeAV() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    VBox vbox = (VBox) loader.load();
    mainVbox.getChildren().setAll(vbox);
  }
}
