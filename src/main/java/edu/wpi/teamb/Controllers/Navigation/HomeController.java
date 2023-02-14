package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
  }
}
