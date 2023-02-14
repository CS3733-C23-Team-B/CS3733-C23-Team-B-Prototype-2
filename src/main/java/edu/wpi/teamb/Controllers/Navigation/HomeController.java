package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DAO.RequestDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeController {
  @FXML Label homeWelcome;
  @FXML VBox homeRequests;

  public void initialize() {
    homeWelcome.setText("Welcome, " + SigninController.currentUser.getFirstname());


  }
}
