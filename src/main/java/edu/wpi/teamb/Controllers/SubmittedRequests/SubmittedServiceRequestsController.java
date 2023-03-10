package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Database.Requests.*;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Entities.Urgency;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SubmittedServiceRequestsController {
  @FXML VBox mainVbox;
  @FXML Label RequestInformationTitle;
  @FXML VBox specificRequestInfoBox;
  @FXML VBox filterVbox;
  @FXML MFXButton clearFiltersButton;
  @FXML MFXComboBox<RequestStatus> requestStatusFilter;
  @FXML MFXFilterComboBox<String> assignedStaffFilter;
  @FXML MFXFilterComboBox<RequestType> requestTypeFilter;
  @FXML MFXComboBox<Urgency> requestUrgencyFilter;
  @FXML MFXComboBox<String> requestReporterFilter;
  @FXML MFXCheckbox myRequestsFilter;
  @FXML MFXButton helpButton;
  @FXML Label dateLabel;
  @FXML Label timeLabel;
  SubmittedSanitationRequestTable saniTable = new SubmittedSanitationRequestTable();
  SubmittedTransportationRequestTable ptTable = new SubmittedTransportationRequestTable();
  SubmittedComputerRequestTable comTable = new SubmittedComputerRequestTable();
  SubmittedAVRequestTable avTable = new SubmittedAVRequestTable();
  SubmittedFacilitiesRequestTable securityTable = new SubmittedFacilitiesRequestTable();
  SubmittedGeneralRequestTable allTable = new SubmittedGeneralRequestTable();
  SubmittedMedicineRequestTable medicineTable = new SubmittedMedicineRequestTable();
  SubmittedMedicalEquipmentRequestTable equipTable = new SubmittedMedicalEquipmentRequestTable();
  SubmittedFacilitiesRequestTable facTable = new SubmittedFacilitiesRequestTable();
  List<String> titles = new ArrayList<>();
  List<String> data = new ArrayList<>();
  @FXML Label field1label, field2label, field3label, field4label;
  List<Label> Labels = new ArrayList<>();
  @FXML Label field1text, field2text, field3text, field4text;
  List<Label> text = new ArrayList<>();

  @FXML Label la;

  String page = "none";
  RequestType cur;
  Boolean myrequests = false;
  private ObservableList<RequestStatus> Status =
      FXCollections.observableArrayList(
          RequestStatus.BLANK, RequestStatus.PROCESSING, RequestStatus.DONE);
  protected ObservableList<Urgency> urgency =
      FXCollections.observableArrayList(
          Urgency.LOW, Urgency.MODERATE, Urgency.HIGH, Urgency.REQUIRESIMMEADIATEATTENTION);
  private ObservableList<RequestType> requestType =
      FXCollections.observableArrayList(
          RequestType.ALLREQUESTS,
          RequestType.AUDIOVIDEO,
          RequestType.COMPUTER,
          RequestType.FACILITIES,
          RequestType.PATIENTTRANSPORTATION,
          RequestType.MEDICALEQUIPMENT,
          RequestType.MEDICINE,
          RequestType.SANITATION,
          RequestType.SECURITY);
  private ObservableList<String> staff = DBSession.getStaff();

  private Login currUser = SigninController.getInstance().currentUser;

  public void initialize() {
    if (!currUser.getAdmin()) myrequests = true;
    saniTable.initialize();
    ptTable.initialize();
    comTable.initialize();
    avTable.initialize();
    securityTable.initialize();
    allTable.initialize();
    medicineTable.initialize();
    equipTable.initialize();
    facTable.initialize();
    initLabels();
    makeTable(RequestType.ALLREQUESTS);
    myRequestsFilter.setOnAction(
        e -> {
          myrequests = myRequestsFilter.isSelected();
          filter();
        });
    requestTypeFilter.setOnAction(e -> makeTable((RequestType) requestTypeFilter.getValue()));
    clearFiltersButton.setOnAction(e -> clearFilters());
    requestStatusFilter.setOnAction(e -> filter());
    assignedStaffFilter.setOnAction(e -> filter());
    requestUrgencyFilter.setOnAction(e -> filter());
    requestReporterFilter.setOnAction(e -> filter());

    mainVbox.setPadding(new Insets(0, 20, 0, 20));

    requestStatusFilter.setItems(Status);
    assignedStaffFilter.setItems(staff);
    requestTypeFilter.setItems(requestType);
    requestUrgencyFilter.setItems(urgency);
    requestReporterFilter.setItems(staff);
    requestTypeFilter.setText("All Requests");
    requestTypeFilter.setValue(RequestType.ALLREQUESTS);
    requestStatusFilter.setText("--Select--");
    assignedStaffFilter.setText("--Select--");
    requestUrgencyFilter.setText("--Select--");
    requestReporterFilter.setText("--Select--");
    setFilters();
    resetRequestVboxes();

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    String formattedDate = currentDate.format(formatter);

    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0),
                event -> {
                  LocalDateTime currentTime = LocalDateTime.now();
                  DateTimeFormatter timefmt = DateTimeFormatter.ofPattern("h:mm a");
                  timeLabel.setText(currentTime.format(timefmt));
                }),
            new KeyFrame(Duration.seconds(1)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    dateLabel.setText(formattedDate);
  }

  public void displayChart() {
    ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
            new PieChart.Data(
                "Patient Transportation Requests", DBSession.getAllPTRequests().size()),
            new PieChart.Data("Sanitation Requests", DBSession.getAllSanRequests().size()),
            new PieChart.Data("Computer Requests", DBSession.getAllCRequests().size()),
            new PieChart.Data("A/V Requests", DBSession.getAllAVRequests().size()),
            new PieChart.Data("Security Requests", DBSession.getAllSecRequests().size()),
            new PieChart.Data("Medicine Delivery Requests", DBSession.getAllMDRequests().size()),
            new PieChart.Data("Equipment Delivery Requests", DBSession.getAllMEDRequests().size()),
            new PieChart.Data(
                "Facilities Maintenance Requests", DBSession.getAllFacRequests().size()));

    PieChart pieChart = new PieChart(pieChartData);
    //    pieChart.setTitle("Report");
    mainVbox.getChildren().add(pieChart);
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
    specificRequestInfoBox.getChildren().clear();
    RequestInformationTitle.setText("Request Info");
    specificRequestInfoBox.getChildren().add(RequestInformationTitle);
  }

  public void helpButtonClicked() throws IOException {
    Popup.displayPopup(Screen.SUBMITTED_REQUESTS_HELP);
  }

  private void makeTable(RequestType name) {
    page = name.toString();
    this.cur = name;
    TableView table = new TableView<>();
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    mainVbox.getChildren().clear();
    if (page.equals(RequestType.SANITATION.toString())) {

      table =
          saniTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.PATIENTTRANSPORTATION.toString())) {
      table =
          ptTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.COMPUTER.toString())) {
      table =
          comTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.AUDIOVIDEO.toString())) {
      table =
          avTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.SECURITY.toString())) {
      table =
          securityTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.ALLREQUESTS.toString())) {
      table =
          allTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.MEDICINE.toString())) {
      table =
          medicineTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.MEDICALEQUIPMENT.toString())) {
      table =
          equipTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    } else if (page.equals(RequestType.FACILITIES.toString())) {
      table =
          facTable.getTable(
              requestStatusFilter.getValue(),
              assignedStaffFilter.getValue(),
              requestReporterFilter.getValue(),
              requestUrgencyFilter.getValue(),
              myrequests);
    }
    TableView finalTable = table;
    table.setOnMouseClicked(e -> mouseClicked(finalTable));
    setLabel(page);
    mainVbox.getChildren().add(table);
    if (page.equals(RequestType.ALLREQUESTS.toString()) && currUser.getAdmin()) {
      displayChart();
    }
  }

  private void setLabel(String name) {
    la.setFont(new Font("Ariel", 50));
    la.setText(name);
  }

  public void clearFilters() {
    assignedStaffFilter.getSelectionModel().clearSelection();
    assignedStaffFilter.setValue(null);
    requestStatusFilter.getSelectionModel().clearSelection();
    requestStatusFilter.setValue(null);
    requestUrgencyFilter.getSelectionModel().clearSelection();
    requestUrgencyFilter.setValue(null);
    requestReporterFilter.getSelectionModel().clearSelection();
    requestReporterFilter.setValue(null);
    requestTypeFilter.setValue(cur);

    if (currUser.getAdmin()) myRequestsFilter.setSelected(false);
    else myRequestsFilter.setSelected(true);
    requestStatusFilter.setText("--Select--");
    assignedStaffFilter.setText("--Select--");
    requestUrgencyFilter.setText("--Select--");
    requestReporterFilter.setText("--Select--");
    filter();
  }

  @FXML
  public void mouseClicked(TableView table) {
    GeneralRequest r = (GeneralRequest) table.getSelectionModel().getSelectedItem();
    resetRequestVboxes();
    titles = new ArrayList<>();
    data = new ArrayList<>();

    if (r != null) {
      //      get request info
      String type = r.getRequestType().toString();
      String date = r.getDate();
      String requestor = r.getFirstname() + " " + r.getLastname();
      String employeeId = r.getEmployeeID();
      String email = r.getEmail();
      String urgency = r.getUrgency().toString();
      String assignedEmployee = r.getAssignedEmployee();
      String status = r.getStatus().toString();
      String notes = r.getNotes();
      //      type, date, requestor, employeeId, email, urgency, assignedEmployee
      addCommonAttritbutes(type, date, requestor, employeeId, email, urgency, assignedEmployee);
      if (r.getRequestType().equals(RequestType.PATIENTTRANSPORTATION)) {
        PatientTransportationRequest pt = (PatientTransportationRequest) r;
        addAttribute("Patient ID:", pt.getPatientID());
        addAttribute("Patient Destination:", pt.getPatientDestinationLocation());
        addAttribute("Patient Current Location:", r.getLocation());
        addAttribute("Equipment Needed:", pt.getEquipmentNeeded());
        setFields();
      } else if (r.getRequestType().equals(RequestType.SANITATION)) {
        SanitationRequest sr = (SanitationRequest) r;
        addAttribute("Clean Up Location:", r.getLocation());
        addAttribute("Type of Clean Up:", sr.getTypeOfCleanUp());
        setFields();
      } else if (r.getRequestType().equals(RequestType.COMPUTER)) {
        ComputerRequest cr = (ComputerRequest) r;
        addAttribute("Repair Location:", r.getLocation());
        addAttribute("Type of Repair:", cr.getTypeOfRepair());
        addAttribute("Type of Device:", cr.getDevice());
        setFields();
      } else if (r.getRequestType().equals(RequestType.AUDIOVIDEO)) {
        AudioVideoRequest avr = (AudioVideoRequest) r;
        addAttribute("Location:", avr.getLocation());
        addAttribute("Audio Visual Type:", avr.getAVType());
        setFields();
      } else if (r.getRequestType().equals(RequestType.SECURITY)) {
        SecurityRequest secr = (SecurityRequest) r;
        addAttribute("Location: ", secr.getLocation());
        addAttribute("Type Of Issue:", secr.getIssueType());
        addAttribute("Equipment Needed:", secr.getEquipmentNeeded());
        addAttribute("Number Required:", String.valueOf(secr.getNumberRequired()));
        setFields();
      } else if (r.getRequestType().equals(RequestType.MEDICINE)) {
        MedicineDeliveryRequest medr = (MedicineDeliveryRequest) r;
        addAttribute("Location:", medr.getLocation());
        addAttribute("Type of Medicine:", medr.getMedicineType());
        addAttribute("Dosage:", medr.getDosage());
        addAttribute("Patient ID:", medr.getPatientID());
        setFields();
      } else if (r.getRequestType().equals(RequestType.MEDICALEQUIPMENT)) {
        MedicalEquipmentDeliveryRequest equipr = (MedicalEquipmentDeliveryRequest) r;
        addAttribute("Equipment Destination:", equipr.getLocation());
        addAttribute("Type of Equipment:", equipr.getEquipmentType());
        setFields();
      } else if (r.getRequestType().equals(RequestType.FACILITIES)) {
        FacilitiesRequest fr = (FacilitiesRequest) r;
        addAttribute("Location:", fr.getLocation());
        addAttribute("Type of Maintenance", fr.getMaintenanceType());
        setFields();
      }
      addStatusAndNotes(status, notes);
    } else {

    }
  }

  private void addAttribute(String title, String field) {
    titles.add(title);
    data.add(field);
  }

  @FXML Label requestTypeText;
  @FXML Label dateText;
  @FXML Label requestorText;
  @FXML Label employeeIdText;
  @FXML Label emailText;
  @FXML Label UrgencyLabel;
  @FXML Label urgencyText;
  @FXML Label assignedEmployeeLabel;
  @FXML Label assignedEmployeeText;

  private void addCommonAttritbutes(
      String type,
      String date,
      String requestor,
      String employeeId,
      String email,
      String urgency,
      String assignedEmployee) {
    addAttribute(requestTypeText, type);
    addAttribute(dateText, date);
    addAttribute(requestorText, "Requestor: " + requestor);
    addAttribute(employeeIdText, "Employee ID: " + employeeId);
    addAttribute(emailText, "Email: " + email);
    addAttribute(UrgencyLabel, urgencyText, urgency);
    addAttribute(assignedEmployeeLabel, assignedEmployeeText, assignedEmployee);
  }

  private void addAttribute(Label title, Label text, String s) {
    specificRequestInfoBox.getChildren().add(title);
    addAttribute(text, s);
  }

  @FXML Label statusLabel;
  @FXML Label statusText;
  @FXML Label notesLabel;
  @FXML Label notesText;

  private void addStatusAndNotes(String status, String notes) {
    addAttribute(statusLabel, statusText, status);
    addAttribute(notesLabel, notesText, notes);
  }

  private void addAttribute(Label l, String s) {
    l.setText(s);
    specificRequestInfoBox.getChildren().add(l);
  }

  private void setFields() {
    for (int i = 0; i < data.size(); i++) {
      Labels.get(i).setText(titles.get(i));
      text.get(i).setText(data.get(i));
      specificRequestInfoBox.getChildren().add(Labels.get(i));
      specificRequestInfoBox.getChildren().add(text.get(i));
    }
  }

  @FXML Label requestTypeBox;
  @FXML Label urgencyBox;
  @FXML Label requestStatusBox;
  @FXML Label assignedStaffBox;
  @FXML Label requestReporterBox;

  private void setFilters() {
    filterVbox.getChildren().clear();
    filterVbox.getChildren().add(requestTypeBox);
    filterVbox.getChildren().add(requestTypeFilter);
    addFilter(urgencyBox, requestUrgencyFilter);
    addFilter(requestStatusBox, requestStatusFilter);
    filterVbox.getChildren().add(assignedStaffBox);
    filterVbox.getChildren().add(assignedStaffFilter);

    if (currUser.getAdmin()) {
      addFilter(requestReporterBox, requestReporterFilter);
      filterVbox.getChildren().add(myRequestsFilter);
    }

    // filterVbox.getChildren().add(clearFiltersButton);
  }

  private void addFilter(Label l, MFXComboBox b) {
    filterVbox.getChildren().add(l);
    filterVbox.getChildren().add(b);
  }

  public void filter() {
    makeTable(cur);
  }
}
