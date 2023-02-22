package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Database.Requests.*;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Entities.Urgency;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SubmittedServiceRequestsController {
  @FXML VBox mainVbox;
  @FXML VBox specificRequestInfoBox;
  @FXML VBox filterVbox;
  @FXML MFXButton clearFiltersButton;
  @FXML MFXComboBox requestStatusFilter;
  @FXML MFXComboBox assignedStaffFilter;
  @FXML MFXComboBox requestTypeFilter;
  @FXML MFXComboBox requestUrgencyFilter;
  @FXML MFXCheckbox myRequestsFilter;
  @FXML ImageView helpButton;
  SubmittedSanitationRequestTable saniTable = new SubmittedSanitationRequestTable();
  SubmittedTransportationRequestTable ptTable = new SubmittedTransportationRequestTable();
  SubmittedComputerRequestTable comTable = new SubmittedComputerRequestTable();
  SubmittedAVRequestTable avTable = new SubmittedAVRequestTable();
  SubmittedSecurityRequestTable securityTable = new SubmittedSecurityRequestTable();
  SubmittedGeneralRequestTable allTable = new SubmittedGeneralRequestTable();

  @FXML Label la;

  String page = "none";
  Boolean myrequests = true;
  private ObservableList<RequestStatus> Status =
      FXCollections.observableArrayList(
          RequestStatus.BLANK, RequestStatus.PROCESSING, RequestStatus.DONE);
  protected ObservableList<Urgency> urgency =
      FXCollections.observableArrayList(
          Urgency.LOW, Urgency.MODERATE, Urgency.HIGH, Urgency.REQUIRESIMMEADIATEATTENTION);
  private ObservableList<String> requestType =
      FXCollections.observableArrayList(
          "All Requests",
          "Sanitation",
          "Internal Patient Transportation",
          "Audio and Visual",
          "Security",
          "Computer");
  private ObservableList<String> staff = DBSession.getStaff();

  private Login currUser = SigninController.getInstance().currentUser;

  public void initialize() {
    saniTable.initialize();
    ptTable.initialize();
    comTable.initialize();
    avTable.initialize();
    securityTable.initialize();
    allTable.initialize();
    makeTable("All Requests");
    if (currUser.getAdmin()) {
      myRequestsFilter.setOnAction(
          e -> {
            myrequests = myRequestsFilter.isSelected();
            filter();
          });
    }
    requestTypeFilter.setOnAction(e -> makeTable((String) requestTypeFilter.getValue()));
    clearFiltersButton.setOnAction(e -> clearFilters());
    requestStatusFilter.setOnAction(e -> filter());
    assignedStaffFilter.setOnAction(e -> filter());
    requestUrgencyFilter.setOnAction(e -> filter());
    myRequestsFilter.setOnAction(e -> filter());
    assignedStaffFilter.setOnAction(e -> filter());
    requestUrgencyFilter.setOnAction(e -> filter());

    mainVbox.setPadding(new Insets(50, 20, 0, 20));
    setFilters();
    requestStatusFilter.setItems(Status);
    assignedStaffFilter.setItems(staff);
    requestTypeFilter.setItems(requestType);
    requestUrgencyFilter.setItems(urgency);
    requestTypeFilter.setText("All Request");
    requestStatusFilter.setText("--Select--");
    assignedStaffFilter.setText("--Select--");
    requestUrgencyFilter.setText("--Select--");
    specificRequestInfoBox.getChildren().clear();
    RequestInformationTitle.setText("Request Info");
    specificRequestInfoBox.getChildren().add(RequestInformationTitle);
  }

  public void helpButtonClicked() throws IOException {
    Navigation.navigate(Screen.SERVICE_REQUEST_SYSTEMS);
  }

  private void makeTable(String name) {
    page = name;
    TableView table = new TableView<>();

    mainVbox.getChildren().clear();
    if (page.equals("Sanitation")) {
      table =
          saniTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedStaffFilter.getValue(),
              (Urgency) requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals("Internal Patient Transportation")) {
      table =
          ptTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedStaffFilter.getValue(),
              (Urgency) requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals("Computer")) {
      table =
          comTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedStaffFilter.getValue(),
              (Urgency) requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals("Audio and Visual")) {
      table =
          avTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedStaffFilter.getValue(),
              (Urgency) requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals("Security")) {
      table =
          securityTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedStaffFilter.getValue(),
              (Urgency) requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals("All Requests")) {
      table =
          allTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedStaffFilter.getValue(),
              (Urgency) requestUrgencyFilter.getValue(),
              myrequests);
    }
    TableView finalTable = table;
    table.setOnMouseClicked(e -> mouseClicked(finalTable));
    setLabel(name);
    mainVbox.getChildren().add(table);
  }

  private void setLabel(String name) {
    la.setText(name);
    la.setFont(new Font("Ariel", 25));
  }

  public void clearFilters() {
    assignedStaffFilter.setValue(null);
    requestStatusFilter.setValue(null);
    requestUrgencyFilter.setValue(null);
    requestTypeFilter.setValue(page);
    myRequestsFilter.setSelected(false);
    requestStatusFilter.setText("--Select--");
    assignedStaffFilter.setText("--Select--");
    requestUrgencyFilter.setText("--Select--");
    filter();
  }

  @FXML Label RequestInformationTitle;

  @FXML
  public void mouseClicked(TableView table) {
    GeneralRequest r = (GeneralRequest) table.getSelectionModel().getSelectedItem();
    specificRequestInfoBox.getChildren().clear();
    RequestInformationTitle.setText("Request Info");
    specificRequestInfoBox.getChildren().add(RequestInformationTitle);

    if (r != null) {
      addCommonAttritbutes(r.getRequestType().toString(), r.getDate(), r.getUrgency().toString());
      if (r.getRequestType().equals(RequestType.PATIENTTRANSPOTATION)) {
        PatientTransportationRequest pt = (PatientTransportationRequest) r;
        Label patientDestination =
            addAttribute("Patient Destination:", pt.getPatientDestinationLocation());
        Label patientID = addAttribute("Patient ID:", pt.getPatientID());
        Label patientCurrent =
            addAttribute("Patient Current Location:", pt.getPatientCurrentLocation());
        Label equipmentNeeded = addAttribute("Equipment Needed:", pt.getEquipmentNeeded());

      } else if (r.getRequestType().equals(RequestType.SANITATION)) {
        SanitationRequest sr = (SanitationRequest) r;
        Label cleanUpLocation = addAttribute("Clean Up Location:", sr.getCleanUpLocation());
        Label typeOfCleanUp = addAttribute("Type of Clean Up:", sr.getTypeOfCleanUp());
      } else if (r.getRequestType().equals(RequestType.COMPUTER)) {
        ComputerRequest cr = (ComputerRequest) r;
        Label typeOfRepair = addAttribute("Type of Repair:", cr.getTypeOfRepair());
        Label device = addAttribute("Type of Device:", cr.getDevice());
        Label repairLocation = addAttribute("Repair Location:", cr.getRepairLocation());
      } else if (r.getRequestType().equals(RequestType.AUDOVISUAL)) {
        AudioVideoRequest avr = (AudioVideoRequest) r;
        Label avType = addAttribute("Audio Visual Type:", avr.getAVType());
        Label location = addAttribute("Location:", avr.getLocation());
      } else if (r.getRequestType().equals(RequestType.SECURITY)) {
        SecurityRequest secr = (SecurityRequest) r;
        Label issueType = addAttribute("Type Of Issue:", secr.getIssueType());
        Label equipmentNeeded = addAttribute("Equipment Needed:", secr.getEquipmentNeeded());
        Label NumberRequired =
            addAttribute("Number Required:", String.valueOf(secr.getNumberRequired()));
      }
    } else {

    }
  }

  private Label addAttribute(String title, String field) {
    Label t = new Label();
    int fontSize = 16;
    String font = "Nunito";
    Label l = new Label();
    Font f = new Font(font, fontSize);
    l.setFont(Font.font(font, fontSize));
    l.setText(field);
    t.setFont(Font.font(font, FontWeight.LIGHT, fontSize));
    t.setText(title);

    specificRequestInfoBox.getChildren().add(t);
    specificRequestInfoBox.getChildren().add(l);
    return l;
  }

  private Label addCommonAttritbute(String field) {
    Label t = new Label();
    int fontSize = 22;
    String font = "Nunito";
    t.setFont(Font.font(font, fontSize));
    t.setText(field);
    specificRequestInfoBox.getChildren().add(t);
    return t;
  }

  private void addCommonAttritbutes(String type, String date, String urgency) {
    addCommonAttritbute(type);
    addCommonAttritbute(date);
    addAttribute("Request Urgency:", urgency);
  }

  private void setFilters() {
    filterVbox.getChildren().clear();
    filterVbox.getChildren().add(requestTypeFilter);
    filterVbox.getChildren().add(requestUrgencyFilter);
    filterVbox.getChildren().add(requestStatusFilter);
    filterVbox.getChildren().add(assignedStaffFilter);
    if (currUser.getAdmin()) {
      filterVbox.getChildren().add(myRequestsFilter);
    }
    filterVbox.getChildren().add(clearFiltersButton);
  }

  public void filter() {
    makeTable(page);
  }
}
