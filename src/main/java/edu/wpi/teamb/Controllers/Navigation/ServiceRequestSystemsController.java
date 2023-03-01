package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class ServiceRequestSystemsController {
  @FXML GridPane mainGridPane;
  @FXML MFXButton transButton;
  @FXML MFXButton saniButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton AVButton;
  @FXML MFXButton equipButton;
  @FXML MFXButton medDeliveryButton;
  @FXML MFXButton facMainButton;
  @FXML Label timeLabel;
  @FXML Label dateLabel;

  private ArrayList<Button> buttons;

  /** Create a list of buttons and display the time in the header */
  public void initialize() {
    Button[] btns = {
      transButton,
      saniButton,
      secButton,
      comButton,
      AVButton,
      equipButton,
      medDeliveryButton,
      facMainButton
    };
    buttons = new ArrayList<>(Arrays.asList(btns));

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

  public void viewDevsButtonClicked() throws IOException {
    Popup.displayPopup(Screen.DEVELOPERS);
  }

  /**
   * Display the Internal Patient Transportation page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeTrans() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.PATIENT_TRANSPORTATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(transButton);
  }

  /**
   * Display the Sanitation page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeSani() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.SANITATION.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(saniButton);
  }

  /**
   * Display the Security page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeSec() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.SECURITY_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(secButton);
  }

  /**
   * Display the Computer page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeCom() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.COMPUTER_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(comButton);
  }

  /**
   * Display the A/V page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeAV() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.AV_SERVICES.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(AVButton);
  }

  /**
   * Display the Medical Equipment Delivery page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeMedEquip() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.MEDICAL_EQUIPMENT.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(equipButton);
  }

  /**
   * Display the Medicine Delivery page and highlight its associated button
   *
   * @throws IOException
   */
  public void makeMedDelivery() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.MEDICINE_DELIVERY_SERVICE_REQUEST.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(medDeliveryButton);
  }

  public void makeFacilitiesMaintenance() throws IOException {
    mainGridPane.getChildren().clear();
    final var r = Bapp.class.getResource(Screen.FACILITIES_MAINTENANCE.getFilename());
    final FXMLLoader loader = new FXMLLoader(r);
    mainGridPane.getChildren().add(loader.load());

    highlightButton(facMainButton);
  }
  // makeFacilitiesMaintenance
  /**
   * Make the given button light blue, and reset all others to dark blue
   *
   * @param button the highlighted button
   */
  private void highlightButton(Button button) {
    for (Button b : buttons) {
      if (b.equals(button)) {
        b.setStyle("-fx-background-color: #6D9BF8; -fx-background-radius: 10");
        HBox labelBox = (HBox) b.getChildrenUnmodifiable().get(1);
        HBox subBox = (HBox) labelBox.getChildren().get(1);
        Label la = (Label) subBox.getChildren().get(0);
        la.setTextFill(Color.color(1, 1, 1));
      } else {
        b.setStyle("-fx-background-color: #21357E; -fx-background-radius: 10");
        HBox labelBox = (HBox) b.getChildrenUnmodifiable().get(1);
        HBox subBox = (HBox) labelBox.getChildren().get(1);
        Label la = (Label) subBox.getChildren().get(0);
        la.setTextFill(Paint.valueOf("#c5d3ea"));
      }
    }
  }
}
