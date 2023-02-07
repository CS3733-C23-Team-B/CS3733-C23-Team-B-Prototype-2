package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/HomeScreen.fxml"),
  DATABASE_HELP("views/DatabaseHelp.fxml"),
  MAP_DATA_EDITOR("views/MapDataEditor.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),
  NODE_EDITOR("views/NodeEditor.fxml"),
  NODE_CREATOR("views/NodeCreator.fxml"),
  LOCATION_EDITOR("views/LocationEditor.fxml"),
  LOCATION_CREATOR("views/LocationCreator.fxml"),
  NAVIGATION("views/navigation2.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/PatientTransportation.fxml"),
  PATIENT_TRANSPORTATION_HELP("views/PatientTransportationHelpPage.fxml"),
  SANITATION("views/sanitationService.fxml"),
  SANITATION_HELP("views/sanitationHelpPage.fxml"),
  COMPUTER_SERVICES("views/ComputerService.fxml"),
  COMPUTER_SERVICES_HELP("views/ComputerServiceHelpPage.fxml"),
  SIGN_IN("views/SignIn.fxml"),
  REQUESTS("views/requests.fxml"),
  PROFILE("views/Profile.fxml"),
  MAINHELP("views/MainHelpPage.fxml"),
  FOOTER("views/Footer.fxml"),
  SUBMISSION_SUCCESS("views/submissionSuccess.fxml"),
  ABOUT("views/About.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
