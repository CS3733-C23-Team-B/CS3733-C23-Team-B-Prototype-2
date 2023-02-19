package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SubmittedServiceRequestsController {
  @FXML VBox mainVbox;
  @FXML MFXButton clearFiltersButton;
  @FXML MFXComboBox requestStatusFilter;
  @FXML MFXComboBox assignedEmployeeFilter;
  @FXML MFXComboBox requestTypeFilter;
  @FXML MFXComboBox urgencyFilter;
  SubmittedSanitationRequestTable saniTable = new SubmittedSanitationRequestTable();
  SubmittedTransportationRequestTable ptTable = new SubmittedTransportationRequestTable();
  SubmittedComputerRequestTable comTable = new SubmittedComputerRequestTable();
  SubmittedAVRequestTable avTable = new SubmittedAVRequestTable();
  SubmittedSecurityRequestTable securityTable = new SubmittedSecurityRequestTable();
  SubmittedGeneralRequestTable allTable = new SubmittedGeneralRequestTable();

  TableView table = new TableView<>();
  String page = "none";

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
          "Security");
  private ObservableList<String> staff = DBSession.getStaff();

  public void initialize() {
    saniTable.initialize();
    ptTable.initialize();
    comTable.initialize();
    avTable.initialize();
    securityTable.initialize();
    allTable.initialize();
    requestTypeFilter.setOnAction(
        e -> {
          makeTable((String) requestTypeFilter.getValue());
        });
    clearFiltersButton.setOnAction(e -> clearFilters());
    requestStatusFilter.setOnAction(e -> filter());
    assignedEmployeeFilter.setOnAction(e -> filter());
    urgencyFilter.setOnAction(e -> filter());
    mainVbox.setPadding(new Insets(50, 20, 0, 20));

    requestStatusFilter.setItems(Status);
    assignedEmployeeFilter.setItems(staff);
    requestTypeFilter.setItems(requestType);
    urgencyFilter.setItems(urgency);
    requestStatusFilter.setText("--Select--");
    assignedEmployeeFilter.setText("--Select--");
    requestTypeFilter.setText("--Select--");
    urgencyFilter.setText("--Select--");
  }

  private void makeTable(String name) {
    page = name;
    mainVbox.getChildren().clear();
    //    table.getItems().clear();
    if (page.equals("Sanitation")) {
      table =
          saniTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue(),
              (Urgency) urgencyFilter.getValue());
    } else if (page.equals("Internal Patient Transportation")) {
      table =
          ptTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue(),
              (Urgency) urgencyFilter.getValue());
    } else if (page.equals("Computer")) {
      table =
          comTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue(),
              (Urgency) urgencyFilter.getValue());
      table =
          avTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue(),
              (Urgency) urgencyFilter.getValue());
    } else if (page.equals("Security")) {
      table =
          securityTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue(),
              (Urgency) urgencyFilter.getValue());
    } else if (page.equals("All Requests")) {
      table =
          allTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue(),
              (Urgency) urgencyFilter.getValue());
    }

    setLabel(name);
    mainVbox.getChildren().add(table);
  }

  private void setLabel(String name) {
    Label la = new Label();
    la.setText(name);
    la.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(la);
  }

  public void clearFilters() {
    assignedEmployeeFilter.setValue(null);
    requestStatusFilter.setValue(null);
    urgencyFilter.setValue(null);
    requestTypeFilter.setValue(page);
    requestStatusFilter.setText("--Select--");
    assignedEmployeeFilter.setText("--Select--");
    urgencyFilter.setText("--Select--");
  }

  public void filter() {
    makeTable(page);
  }
}
