package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/HomeScreen.fxml"),
  DATABASE_HELP("views/DatabaseHelp.fxml"),

  DATABASE_UI("views/DatabaseUI.fxml"),
  NAVIGATION("views/Navigation.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/PatientTransportation.fxml"),
  PATIENT_TRANSPORTATION_HELP("views/PatientTransportationHelpPage.fxml"),
  SANITATION("views/sanitationService.fxml"),
  SANITATION_HELP("views/sanitationHelpPage.fxml"),
  SIGN_IN("views/SignIn.fxml"),
  REQUESTS("views/requests.fxml"),
  PROFILE("views/Profile.fxml"),
  FOOTER("views/Footer.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
