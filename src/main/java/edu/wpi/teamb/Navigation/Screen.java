package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/Navigation/HomeScreen.fxml"),
  MESSAGE_BOX("views/Kiosk/KioskMessageBox.fxml"),
  KIOSK_VIEW("views/Kiosk/KioskView.fxml"),
  DATABASE_HELP("views/Database/DatabaseHelp.fxml"),
  KIOSK_EDIT("views/Kiosk/KioskEditor.fxml"),
  MAP_DATA_EDITOR("views/Database/MapDataEditor.fxml"),
  MAP_EDITOR("views/Database/MapEditor.fxml"),
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
  PATHFINDING("views/Database/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/ServiceRequest/PatientTransportation.fxml"),
  SANITATION("views/ServiceRequest/SanitationService.fxml"),
  COMPUTER_SERVICES("views/ServiceRequest/ComputerService.fxml"),
  AV_SERVICES("views/ServiceRequest/AVService.fxml"),
  TEMPLATE("views/ServiceRequest/Template.fxml"),
  MEDICAL_EQUIPMENT("views/ServiceRequest/MedicalEquipmentDelivery.fxml"),
  SIGN_IN("views/Profile/SignIn.fxml"),
  SUBMITTED_SERVICE_REQUESTS("views/ServiceRequest/SubmittedServiceRequests.fxml"),
  PROFILE("views/Profile/Profile2.fxml"),
  MAINHELP("views/Popups/MainHelpPage.fxml"),
  FOOTER("views/Navigation/Footer.fxml"),
  SUBMISSION_SUCCESS("views/Popups/SubmissionSuccess.fxml"),
  DEVELOPERS("views/Popups/ServiceRequestDevelopers.fxml"),
  CREDITS("views/Navigation/Credits.fxml"),
  SERVICE_REQUEST_SYSTEMS("views/ServiceRequest/ServiceRequestSystems.fxml"),
  CREATE_ACCOUNT("views/Profile/CreateAccount.fxml"),
  ALL_USERS("views/Profile/AllUsers2.fxml"),
  LANDING_PAGE_CREDITS("views/Navigation/LandingPageCredits.fxml"),
  SECURITY_SERVICES("views/ServiceRequest/Security.fxml"),
  FORGOT_PASSWORD("views/Profile/ForgotPass.fxml"),
  EXIT_CONFIRMATION("views/Navigation/ExitConfirmation.fxml"),
  ABOUT_PAGE("views/Navigation/About.fxml"),
  SERVICE_REQUEST_FORM_HELP("views/Popups/ServiceRequestFormHelp.fxml"),
  MAP_EDITOR_HELP_POP_UP("views/Popups/MapEditorHelpPopUp.fxml"),
  PATHFINDING_HELP_POP_UP("views/Popups/PathfindingHelpPopUp.fxml"),
  DATABASE_CONFIRMATION("views/Database/DatabaseConfirmation.fxml"),
  MEDICINE_DELIVERY_SERVICE_REQUEST("views/ServiceRequest/MedicineDelivery.fxml"),
  KAVYA_MANI("views/Popups/KavyaPopUp.fxml"),
  ADRIAN_JOHNSON("views/Popups/AdrianPopUp.fxml"),
  JOLIE_WALTS("views/Popups/JoliePopUp.fxml"),
  CHRISTINA_AUBE("views/Popups/ChristinaPopUp.fxml"),
  CIERRA_OGRADY("views/Popups/CierraPopUp.fxml"),
  LUKE_GRADY("views/Popups/LukePopUp.fxml"),
  SAM_COLEBOURN("views/Popups/SamPopUp.fxml"),
  SEAN_LENDRUM("views/Popups/SeanPopUp.fxml"),
  MICHAEL_GATTI("views/Popups/MichaelPopUp.fxml"),
  JACK_LAFORD("views/Popups/JackPopUp.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
