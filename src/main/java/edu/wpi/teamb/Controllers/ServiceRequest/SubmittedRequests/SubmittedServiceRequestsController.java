package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Entities.RequestStatus;
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
  @FXML MFXButton saniButton;
  @FXML MFXButton transButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton audioButton;
  @FXML MFXButton clearFiltersButton;
  @FXML MFXComboBox requestStatusFilter;
  @FXML MFXComboBox assignedEmployeeFilter;
  SubmittedSanitationRequestTable saniTable = new SubmittedSanitationRequestTable();
  SubmittedTransportationRequestTable ptTable = new SubmittedTransportationRequestTable();
  SubmittedComputerRequestTable comTable = new SubmittedComputerRequestTable();
  SubmittedAVRequestTable avTable = new SubmittedAVRequestTable();
  SubmittedSecurityRequestTable securityTable = new SubmittedSecurityRequestTable();

  TableView table = new TableView<>();

  private ObservableList<RequestStatus> Status =
      FXCollections.observableArrayList(
          RequestStatus.BLANK, RequestStatus.PROCESSING, RequestStatus.DONE);
  private ObservableList<String> staff = DBSession.getStaff();
  String page = "none";

  public void initialize() {
    saniTable.initialize();
    ptTable.initialize();
    comTable.initialize();
    avTable.initialize();
    securityTable.initialize();
    saniButton.setOnAction(e -> makeTable("Sanitation"));
    transButton.setOnAction(e -> makeTable("Internal Patient Transportation"));
    comButton.setOnAction(e -> makeTable("Computer"));
    audioButton.setOnAction(e -> makeTable("Audio and Visual"));
    secButton.setOnAction(e -> makeTable("Security"));
    clearFiltersButton.setOnAction(e -> clearFilters());
    requestStatusFilter.setOnAction(e -> filter());
    assignedEmployeeFilter.setOnAction(e -> filter());
    mainVbox.setPadding(new Insets(50, 20, 0, 20));

    requestStatusFilter.setItems(Status);
    assignedEmployeeFilter.setItems(staff);
    requestStatusFilter.setText("--Select--");
    assignedEmployeeFilter.setText("--Select--");
  }

  private void makeTable(String name) {
    page = name;
    mainVbox.getChildren().clear();
    table.getItems().clear();
    if (page.equals("Sanitation")) {
      table =
          saniTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue());
    } else if (page.equals("Internal Patient Transportation")) {
      table =
          ptTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue());
    } else if (page.equals("Computer")) {
      table =
          comTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue());
    } else if (page.equals("Audio and Visual")) {
      table =
          avTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue());
    } else if (page.equals("Security")) {
      table =
          securityTable.getTable(
              (RequestStatus) requestStatusFilter.getValue(),
              (String) assignedEmployeeFilter.getValue());
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
    requestStatusFilter.setText("--Select--");
    assignedEmployeeFilter.setText("--Select--");
    filter();
  }

  public void filter() {
    makeTable(page);
  }
}
