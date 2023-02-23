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
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SubmittedServiceRequestsController {
  @FXML VBox mainVbox;
  @FXML Label RequestInformationTitle;
  @FXML VBox specificRequestInfoBox;
  @FXML VBox generalRequestInfoVbox;
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

  List<String> titles = new ArrayList<>();
  List<String> data = new ArrayList<>();
  @FXML Label field1label, field2label, field3label, field4label;
  List<Label> Labels = new ArrayList<>();
  @FXML Label field1text, field2text, field3text, field4text;
  List<Label> text = new ArrayList<>();

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
    resetRequestVboxes();
  }

  public void initLabels() {
    Labels.add(field1label);
    Labels.add(field2label);
    Labels.add(field3label);
    Labels.add(field4label);
    text.add(field1text);
    text.add(field2text);
    text.add(field3text);
    text.add(field4text);
  }

  public void resetRequestVboxes() {
    specificRequestInfoBox.getChildren().clear();
    generalRequestInfoVbox.getChildren().clear();
    generalRequestInfoVbox.getChildren().add(RequestInformationTitle);
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
    la.setFont(new Font("Ariel", 50));
    la.setText(name);
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

  @FXML
  public void mouseClicked(TableView table) {
    GeneralRequest r = (GeneralRequest) table.getSelectionModel().getSelectedItem();
    resetRequestVboxes();
    titles = new ArrayList<>();
    data = new ArrayList<>();

    if (r != null) {
      addCommonAttritbutes(r.getRequestType().toString(), r.getDate(), r.getUrgency().toString());
      if (r.getRequestType().equals(RequestType.PATIENTTRANSPOTATION)) {
        PatientTransportationRequest pt = (PatientTransportationRequest) r;
        addAttribute("Patient Destination:", pt.getPatientDestinationLocation());
        addAttribute("Patient ID:", pt.getPatientID());
        addAttribute("Patient Current Location:", pt.getPatientCurrentLocation());
        addAttribute("Equipment Needed:", pt.getEquipmentNeeded());
        setFields();
      } else if (r.getRequestType().equals(RequestType.SANITATION)) {
        SanitationRequest sr = (SanitationRequest) r;
        addAttribute("Clean Up Location:", sr.getCleanUpLocation());
        addAttribute("Type of Clean Up:", sr.getTypeOfCleanUp());
        setFields();
      } else if (r.getRequestType().equals(RequestType.COMPUTER)) {
        ComputerRequest cr = (ComputerRequest) r;
        addAttribute("Type of Repair:", cr.getTypeOfRepair());
        addAttribute("Type of Device:", cr.getDevice());
        addAttribute("Repair Location:", cr.getRepairLocation());
        setFields();
      } else if (r.getRequestType().equals(RequestType.AUDOVISUAL)) {
        AudioVideoRequest avr = (AudioVideoRequest) r;
        addAttribute("Audio Visual Type:", avr.getAVType());
        addAttribute("Location:", avr.getLocation());
        setFields();
      } else if (r.getRequestType().equals(RequestType.SECURITY)) {
        SecurityRequest secr = (SecurityRequest) r;
        addAttribute("Type Of Issue:", secr.getIssueType());
        addAttribute("Equipment Needed:", secr.getEquipmentNeeded());
        addAttribute("Number Required:", String.valueOf(secr.getNumberRequired()));
        setFields();
      }
    } else {

    }
  }

  private void addAttribute(String title, String field) {
    titles.add(title);
    data.add(field);
  }

  @FXML Label requestTypeText;
  @FXML Label dateText;
  @FXML Label urgencyText;
  @FXML Label UrgencyLabel;

  private void addCommonAttritbutes(String type, String date, String urgency) {
    requestTypeText.setText(type);
    generalRequestInfoVbox.getChildren().add(requestTypeText);
    dateText.setText(date);
    generalRequestInfoVbox.getChildren().add(dateText);
    urgencyText.setText(urgency);
    specificRequestInfoBox.getChildren().add(UrgencyLabel);
    specificRequestInfoBox.getChildren().add(urgencyText);
  }

  private void setFields() {
    for (int i = 0; i < data.size(); i++) {
      Labels.get(i).setText(titles.get(i));
      text.get(i).setText(data.get(i));
      specificRequestInfoBox.getChildren().add(Labels.get(i));
      specificRequestInfoBox.getChildren().add(text.get(i));
    }
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
