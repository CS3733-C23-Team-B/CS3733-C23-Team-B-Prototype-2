package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class ServiceRequestSystemsController {
  @FXML VBox mainVbox;
  @FXML Label pageTitle;
  @FXML MFXButton transButton;
  @FXML MFXButton saniButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton AVButton;

  @FXML Label headerText;

  public void initialize() {
    mainVbox.setPadding(new Insets(20, 20, 0, 20));
  }

  public void makeTrans() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.PATIENT_TRANSPORTATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    Font font = Font.font("System", FontPosture.ITALIC, 48);
    pageTitle.setFont(font);
    pageTitle.setText("Internal Patient Transportation");
    headerText.setText("");
    transButton.setStyle("-fx-background-color: #6D9BF8");
    saniButton.setStyle("-fx-background-color: #21357E");
    secButton.setStyle("-fx-background-color:  #21357E");
    comButton.setStyle("-fx-background-color:  #21357E");
    AVButton.setStyle("-fx-background-color: #21357E");
  }

  public void makeSani() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.SANITATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    Font font = Font.font("System", FontPosture.ITALIC, 48);
    pageTitle.setFont(font);
    pageTitle.setText("Sanitation Service");
    headerText.setText("");
    transButton.setStyle("-fx-background-color: #21357E");
    saniButton.setStyle("-fx-background-color: #6D9BF8");
    secButton.setStyle("-fx-background-color:  #21357E");
    comButton.setStyle("-fx-background-color:  #21357E");
    AVButton.setStyle("-fx-background-color: #21357E");
  }

  public void makeSec() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.TEMPLATE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());

    Font font = Font.font("System", FontPosture.ITALIC, 48);
    pageTitle.setFont(font);
    pageTitle.setText("Security Service");
    headerText.setText("");
    transButton.setStyle("-fx-background-color: #21357E");
    saniButton.setStyle("-fx-background-color: #21357E");
    secButton.setStyle("-fx-background-color:  #6D9BF8");
    comButton.setStyle("-fx-background-color:  #21357E");
    AVButton.setStyle("-fx-background-color: #21357E");
  }

  public void makeCom() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.COMPUTER_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    Font font = Font.font("System", FontPosture.ITALIC, 48);
    pageTitle.setFont(font);
    pageTitle.setText("Computer Service");
    headerText.setText("");
    transButton.setStyle("-fx-background-color: #21357E");
    saniButton.setStyle("-fx-background-color: #21357E");
    secButton.setStyle("-fx-background-color:  #21357E");
    comButton.setStyle("-fx-background-color:  #6D9BF8");
    AVButton.setStyle("-fx-background-color: #21357E");
  }

  public void makeAV() throws IOException {
    mainVbox.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.AV_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainVbox.getChildren().add(loader.load());
    Font font = Font.font("System", FontPosture.ITALIC, 48);
    pageTitle.setFont(font);
    pageTitle.setText("Audio/Video Service");
    headerText.setText("");
    transButton.setStyle("-fx-background-color: #21357E");
    saniButton.setStyle("-fx-background-color: #21357E");
    secButton.setStyle("-fx-background-color:  #21357E");
    comButton.setStyle("-fx-background-color:  #21357E");
    AVButton.setStyle("-fx-background-color: #6D9BF8");
  }
}
