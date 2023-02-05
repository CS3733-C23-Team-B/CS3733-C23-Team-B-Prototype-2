package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/Home2.fxml"),
  DATABASE_HELP("views/DatabaseHelp.fxml"),
  MAP_DATA_EDITOR("views/MapDataEditor.fxml"),
  NAVIGATION("views/navigation2.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/PatientTransportation.fxml"),
  PATIENT_TRANSPORTATION_HELP("views/PatientTransportationHelpPage.fxml"),
  SANITATION("views/sanitationService.fxml"),
  SANITATION_HELP("views/sanitationHelpPage.fxml"),
  SIGN_IN("views/SignIn.fxml"),
  REQUESTS("views/requests.fxml"),
  PROFILE("views/Profile.fxml"),
  MAINHELP("views/MainHelpPage.fxml"),
  FOOTER("views/Footer.fxml"),
  SUBMISSION_SUCCESS("views/submissionSuccess.fxml"),
  ABOUT("views/About.fxml"),
  SERVICE_REQUEST_FORMS("views/serviceRequestForms.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
