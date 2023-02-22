package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/Navigation/HomeScreen.fxml"),
  DATABASE_HELP("views/Database/DatabaseHelp.fxml"),
  MAP_DATA_EDITOR("views/Database/MapDataEditor.fxml"),
  MAP_EDITOR("views/Database/NewMapEditor.fxml"),
  NODE_EDITOR("views/Database/NodeEditor.fxml"),
  SIDE_NODE_EDITOR("views/Database/SideNodeEditor.fxml"),
  NODE_CREATOR("views/Database/NodeCreator.fxml"),
  LOCATION_EDITOR("views/Database/LocationEditor.fxml"),
  LOCATION_CREATOR("views/Database/LocationCreator.fxml"),
  MOVE_CREATOR("views/Database/MoveCreator.fxml"),
  FUTURE_MOVES("views/Database/FutureMoves.fxml"),
  EDGE_EDITOR("views/Database/EdgeEditor.fxml"),
  EDGE_CREATOR("views/Database/EdgeCreator.fxml"),
  EDGE_CLICK_CREATOR("views/Database/EdgeClickCreator.fxml"),
  EDGE_REPAIR("views/Database/EdgeRepair.fxml"),
  NAVIGATION("views/Navigation/Navigation.fxml"),
  PATHFINDING("views/Database/NewPathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/ServiceRequest/PatientTransportation.fxml"),
  SANITATION("views/ServiceRequest/SanitationService.fxml"),
  COMPUTER_SERVICES("views/ServiceRequest/ComputerService.fxml"),
  AV_SERVICES("views/ServiceRequest/AVService.fxml"),
  SIGN_IN("views/Profile/SignIn.fxml"),
  SUBMITTED_SERVICE_REQUESTS("views/ServiceRequest/SubmittedServiceRequests.fxml"),
  PROFILE("views/Profile/Profile.fxml"),
  MAINHELP("views/Popups/MainHelpPage.fxml"),
  FOOTER("views/Navigation/Footer.fxml"),
  SUBMISSION_SUCCESS("views/Popups/SubmissionSuccess.fxml"),
  CREDITS("views/Navigation/Credits.fxml"),
  SERVICE_REQUEST_SYSTEMS("views/ServiceRequest/ServiceRequestSystems.fxml"),
  CREATE_ACCOUNT("views/Profile/CreateAccount.fxml"),
  ALL_USERS("views/Profile/AllUsers.fxml"),
  LANDING_PAGE_CREDITS("views/Navigation/LandingPageCredits.fxml"),
  TEMPLATE("views/ServiceRequest/Template2.fxml"),
  SECURITY_SERVICES("views/ServiceRequest/Security.fxml"),
  FORGOT_PASSWORD("views/Profile/ForgotPass.fxml"),
  EXIT_CONFIRMATION("views/Navigation/ExitConfirmation.fxml"),
  ABOUT_PAGE("views/Navigation/About.fxml"),
  SERVICE_REQUEST_FORM_HELP("views/Popups/ServiceRequestFormHelp.fxml"),
  MAP_EDITOR_HELP_POP_UP("views/Popups/MapEditorHelpPopUp.fxml"),
  PATHFINDING_HELP_POP_UP("views/Popups/PathfindingHelpPopUp.fxml"),
  DATABASE_CONFIRMATION("views/Database/DatabaseConfirmation.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
