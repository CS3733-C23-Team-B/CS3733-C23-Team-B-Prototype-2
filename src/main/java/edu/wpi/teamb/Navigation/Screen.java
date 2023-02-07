package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/Navigation/HomeScreen.fxml"),
  DATABASE_HELP("views/Database/DatabaseHelp.fxml"),
  MAP_DATA_EDITOR("views/Database/MapDataEditor.fxml"),
  MAP_EDITOR("views/Database/MapEditor.fxml"),
  NODE_EDITOR("views/Database/NodeEditor.fxml"),
  NODE_CREATOR("views/Database/NodeCreator.fxml"),
  LOCATION_EDITOR("views/Database/LocationEditor.fxml"),
  LOCATION_CREATOR("views/Database/LocationCreator.fxml"),
  NAVIGATION("views/Navigation/Navigation.fxml"),
  PATHFINDING("views/Database/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/ServiceRequest/PatientTransportation.fxml"),
  PATIENT_TRANSPORTATION_HELP("views/ServiceRequest/PatientTransportationHelpPage.fxml"),
  SANITATION("views/ServiceRequest/SanitationService.fxml"),
  SANITATION_HELP("views/ServiceRequest/SanitationHelpPage.fxml"),
  COMPUTER_SERVICES("views/ServiceRequest/ComputerService.fxml"),
  COMPUTER_SERVICES_HELP("views/ServiceRequest/ComputerServiceHelpPage.fxml"),
  SIGN_IN("views/Profile/SignIn.fxml"),
  REQUESTS("views/ServiceRequest/Requests.fxml"),
  PROFILE("views/Profile/Profile.fxml"),
  MAINHELP("views/Navigation/MainHelpPage.fxml"),
  FOOTER("views/Navigation/Footer.fxml"),
  SUBMISSION_SUCCESS("views/ServiceRequest/SubmissionSuccess.fxml"),
  ABOUT("views/Navigation/About.fxml"),
  LANDING_PAGE("views/Navigation/LandingPage.fxml"),
  LANDING_PAGE_CREDITS("views/Navigation/LandingPageCredits.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
