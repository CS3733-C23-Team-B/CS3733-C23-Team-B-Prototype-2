package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LandingPageController {
  @FXML VBox mainVbox;
  @FXML Label pageTitle;
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
    final var r = Bapp.class.getResource(Screen.PATIENT_TRANSPORTATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    pageTitle.setText("Internal Patient Transportation");
  }

  public void makeSani() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    pageTitle.setText("Sanitation Service");
  }

  public void makeSec() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    pageTitle.setText("Security Service");
  }

  public void makeCom() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    pageTitle.setText("Computer Service");
  }

  public void makeAV() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    pageTitle.setText("Audio/Video Service");
  }
}
