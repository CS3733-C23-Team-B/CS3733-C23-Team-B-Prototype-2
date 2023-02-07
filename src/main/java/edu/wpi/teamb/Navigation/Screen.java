package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/HomeScreen.fxml"),
  DATABASE_HELP("views/DatabaseHelp.fxml"),
  MAP_DATA_EDITOR("views/MapDataEditor.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),
  NODE_EDITOR("views/EditNodePopup.fxml"),
  LOCATION_EDITOR("views/LocationEditor.fxml"),
  NAVIGATION("views/Navigation.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/PatientTransportation.fxml"),
  PATIENT_TRANSPORTATION_HELP("views/PatientTransportationHelpPage.fxml"),
  SANITATION("views/SanitationService.fxml"),
  SANITATION_HELP("views/SanitationHelpPage.fxml"),
  COMPUTER_SERVICES("views/ComputerService.fxml"),
  COMPUTER_SERVICES_HELP("views/ComputerServiceHelpPage.fxml"),
  SIGN_IN("views/SignIn.fxml"),
  REQUESTS("views/Requests.fxml"),
  PROFILE("views/Profile.fxml"),
  MAINHELP("views/MainHelpPage.fxml"),
  FOOTER("views/Footer.fxml"),
  SUBMISSION_SUCCESS("views/SubmissionSuccess.fxml"),
  ABOUT("views/About.fxml"),
  LANDING_PAGE("views/LandingPage.fxml"),
  LANDING_PAGE_CREDITS("views/LandingPageCredits.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
