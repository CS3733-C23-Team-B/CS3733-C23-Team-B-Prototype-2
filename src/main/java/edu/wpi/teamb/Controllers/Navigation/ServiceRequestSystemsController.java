package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;

public class ServiceRequestSystemsController {
  @FXML GridPane mainGridPane;
  @FXML Label pageTitle;
  @FXML MFXButton transButton;
  @FXML MFXButton saniButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton AVButton;

  @FXML Label headerText;
  @FXML Label timeLabel;
  @FXML Label dateLabel;

  public void initialize() {
    mainGridPane.setPadding(new Insets(20, 20, 0, 20));

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    String formattedDate = currentDate.format(formatter);

    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0),
                event -> {
                  LocalDateTime currentTime = LocalDateTime.now();
                  DateTimeFormatter timefmt = DateTimeFormatter.ofPattern("h:mm a");
                  timeLabel.setText(currentTime.format(timefmt));
                }),
            new KeyFrame(Duration.seconds(1)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    dateLabel.setText(formattedDate);
  }

  public void makeTrans() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.PATIENT_TRANSPORTATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());
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
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.SANITATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());
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
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.SECURITY_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

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
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.COMPUTER_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());
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
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.AV_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());
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
