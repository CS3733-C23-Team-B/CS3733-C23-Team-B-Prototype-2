package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Database.apiConnection;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class HomeController {
  @FXML Label homeWelcome;
  @FXML VBox homeRequests;
  @FXML Label date;
  @FXML Label time;
  @FXML VBox news;

  public void initialize() throws IOException {
    homeWelcome.setText("Welcome, " + SigninController.currentUser.getFirstname());
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    String formattedDate = currentDate.format(formatter);

    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0),
                event -> {
                  LocalDateTime currentTime = LocalDateTime.now();
                  DateTimeFormatter timefmt = DateTimeFormatter.ofPattern("h:mm:ss a");
                  time.setText(currentTime.format(timefmt));
                }),
            new KeyFrame(Duration.seconds(1)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    date.setText(formattedDate);
    List<GeneralRequest> l =
        DBSession.getAllRequestsWithEmpID(Integer.toString(SigninController.currentUser.getId()));
    if (l.size() > 0) {
      for (int i = 0; i < l.size(); i++) {
        if (i > 3) continue;
        HBox rbox = new HBox();
        HBox sbox = new HBox();
        HBox v = new HBox();
        v.setAlignment(Pos.CENTER_LEFT);
        v.setPrefWidth(998);
        sbox.setPrefWidth(300);
        rbox.setPrefWidth(698);
        GeneralRequest r = l.get(i);
        Label rName = new Label();
        rName.setText(r.getDate() + ", " + r.getRequestType() + " Request: #" + +r.getId());
        Label rStatus = new Label();
        rStatus.setText("Status: " + r.getStatus() + " ");
        rName.setPadding(new Insets(15, 0, 15, 20));
        rName.setFont(new Font("Nunito", 20));
        rStatus.setFont(new Font("Nunito", 20));
        rbox.getChildren().add(rName);
        sbox.getChildren().add(rStatus);
        rbox.setAlignment(Pos.CENTER_LEFT);
        sbox.setAlignment(Pos.CENTER_RIGHT);
        ImageView im = new ImageView();
        im.setFitHeight(20);
        im.setFitWidth(20);
        if (r.getStatus() == RequestStatus.PROCESSING) {
          im.setImage(new Image("/media/status/clock.png"));
        } else if (r.getStatus() == RequestStatus.DONE) {
          im.setImage(new Image("/media/status/done.png"));
        }
        sbox.getChildren().add(im);
        homeRequests.getChildren().add(v);
        v.getChildren().add(rbox);
        v.getChildren().add(sbox);
      }
    } else {
      HBox box = new HBox();
      Label label = new Label();
      label.setText("You have no service requests");
      label.setPadding(new Insets(15, 0, 15, 20));
      label.setFont(new Font("Nunito", 20));
      box.getChildren().add(label);
      homeRequests.getChildren().add(box);
    }
    String[] newsList = apiConnection.getNewsList(apiConnection.sendGET());
    for (String s : newsList) {
      Label newsLabel = new Label();
      newsLabel.setText(s);
      newsLabel.setFont(new Font("Nunito", 20));
      newsLabel.setUnderline(true);
      news.getChildren().add(newsLabel);
    }
  }

  public void allRequests() {
    Navigation.navigate(Screen.SUBMITTED_SERVICE_REQUESTS);
  }
}
