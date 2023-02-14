package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class LandingPageController {
  @FXML VBox mainVbox;
  @FXML MFXButton saniButton;
  @FXML MFXButton transButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton audioButton;

  public void initialize() {
    saniButton.setOnAction(e -> makeSani());
    transButton.setOnAction(e -> makeTrans());
    comButton.setOnAction(e -> makeCom());
    mainVbox.setPadding(new Insets(50, 20, 0, 20));
  }

  private void makeSani() {
    mainVbox.getChildren().clear();
    Navigation.navigate(Screen.SANITATION);
    // mainVbox.getChildren().add(t);
  }

  private void makeTrans() {
    mainVbox.getChildren().clear();
    Navigation.navigate(Screen.PATIENT_TRANSPORTATION);
    // mainVbox.getChildren().add(t);
  }

  private void makeCom() {
    mainVbox.getChildren().clear();
    Navigation.navigate(Screen.COMPUTER_SERVICES);
    // mainVbox.getChildren().add(t);
  }
}
