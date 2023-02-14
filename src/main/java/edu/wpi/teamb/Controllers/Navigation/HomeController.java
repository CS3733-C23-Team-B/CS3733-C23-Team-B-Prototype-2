package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomeController {
  @FXML Label homeWelcome;
  @FXML VBox homeRequests;
  @FXML Label date;

  public void initialize() {
    homeWelcome.setText("Welcome, " + SigninController.currentUser.getFirstname());
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    String formattedDate = currentDate.format(formatter);
    date.setText(formattedDate);
    List<GeneralRequest> l =
        DBSession.getAllRequestsWithEmpID(Integer.toString(SigninController.currentUser.getId()));
    if (l.size() > 0) {
      for (int i = 0; i < l.size(); i++) {
        HBox box = new HBox();
        GeneralRequest r = l.get(i);
        Label label = new Label();
        label.setText("Request #" + r.getId() + ", Status: " + r.getStatus() + " ");
        label.setPadding(new Insets(15, 0, 15, 20));
        label.setFont(new Font("Nunito", 20));
        box.getChildren().add(label);
        box.setAlignment(Pos.CENTER_LEFT);
        ImageView im = new ImageView();
        im.setFitHeight(20);
        im.setFitWidth(20);
        if (r.getStatus() == RequestStatus.PROCESSING) {
          im.setImage(new Image("/media/status/clock.png"));
        } else if (r.getStatus() == RequestStatus.DONE) {
          im.setImage(new Image("/media/status/done.png"));
        }
        box.getChildren().add(im);
        homeRequests.getChildren().add(box);
      }
    }
  }

  public void allRequests() {
    Navigation.navigate(Screen.REQUESTS);
  }
}
