package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Entities.RequestStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import java.util.List;
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

  TableView saniTableView = new TableView<>();
  TableView ptTableView = new TableView<>();
  TableView comTableView = new TableView<>();
  TableView avTableView = new TableView<>();
  TableView securityTableView = new TableView<>();

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
    saniButton.setOnAction(e -> makeTableSani("Sanitation"));
    transButton.setOnAction(e -> makeTableTrans("Internal Patient Transportation"));
    comButton.setOnAction(e -> makeTableCom("Computer"));
    audioButton.setOnAction(e -> makeTableAV("Audio and Visual"));
    secButton.setOnAction(e -> makeTableSecurity("Security"));
    clearFiltersButton.setOnAction(e -> clearFilters());
    requestStatusFilter.setOnAction(e -> filter());
    assignedEmployeeFilter.setOnAction(e -> filter());
    mainVbox.setPadding(new Insets(50, 20, 0, 20));

    requestStatusFilter.setItems(Status);
    assignedEmployeeFilter.setItems(staff);
    requestStatusFilter.setText("--Select--");
    assignedEmployeeFilter.setText("--Select--");
  }

  private void makeTableSani(String name) {
    page = name;
    mainVbox.getChildren().clear();
    saniTableView.getItems().clear();
    saniTableView = saniTable.getTable();
    List<SanitationRequest> objectList = DBSession.getAllSanRequests();
    List<SanitationRequest> filtered = new ArrayList<>();
    objectList.forEach(
        (value) -> {
          if (requestStatusFilter.getValue() != null) {
            if (requestStatusFilter.getValue() == RequestStatus.DONE
                && value.getStatus() == RequestStatus.DONE) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.PROCESSING
                && value.getStatus() == RequestStatus.PROCESSING) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.BLANK
                && value.getStatus() == RequestStatus.BLANK) {
              filtered.add(value);
            }
          } else {
            filtered.add(value);
          }
        });

    filtered.forEach(
        (value) -> {
          if (assignedEmployeeFilter.getValue() != null) {
            if (value.getAssignedEmployee().equals(assignedEmployeeFilter.getValue())) {
              saniTableView.getItems().add(value);
            }
          } else {
            saniTableView.getItems().add(value);
          }
        });

    setLabel(name);
    mainVbox.getChildren().add(saniTableView);
  }

  private void makeTableTrans(String name) {
    page = name;
    mainVbox.getChildren().clear();
    ptTableView.getItems().clear();
    ptTableView =
        ptTable.getTable(
            (RequestStatus) requestStatusFilter.getValue(),
            (String) assignedEmployeeFilter.getValue());
    setLabel(name);
    mainVbox.getChildren().add(ptTableView);
  }

  private void makeTableCom(String name) {
    page = name;
    mainVbox.getChildren().clear();
    comTableView.getItems().clear();
    comTableView = comTable.getTable();

    List<ComputerRequest> objectList = DBSession.getAllCRequests();
    List<ComputerRequest> filtered = new ArrayList<>();
    objectList.forEach(
        (value) -> {
          if (requestStatusFilter.getValue() != null) {
            if (requestStatusFilter.getValue() == RequestStatus.DONE
                && value.getStatus() == RequestStatus.DONE) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.PROCESSING
                && value.getStatus() == RequestStatus.PROCESSING) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.BLANK
                && value.getStatus() == RequestStatus.BLANK) {
              filtered.add(value);
            }
          } else {
            filtered.add(value);
          }
        });

    filtered.forEach(
        (value) -> {
          if (assignedEmployeeFilter.getValue() != null) {
            if (value.getAssignedEmployee().equals(assignedEmployeeFilter.getValue())) {
              comTableView.getItems().add(value);
            }
          } else {
            comTableView.getItems().add(value);
          }
        });

    setLabel(name);
    mainVbox.getChildren().add(ptTableView);
  }

  private void makeTableAV(String name) {
    page = name;
    mainVbox.getChildren().clear();
    avTableView.getItems().clear();
    avTableView = avTable.getTable();

    //    change the datatype here to avRequests once it exists. Setting up controller for it to
    // work.
    List<GeneralRequest> objectList = DBSession.getAllRequests();
    List<GeneralRequest> filtered = new ArrayList<>();
    objectList.forEach(
        (value) -> {
          if (requestStatusFilter.getValue() != null) {
            if (requestStatusFilter.getValue() == RequestStatus.DONE
                && value.getStatus() == RequestStatus.DONE) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.PROCESSING
                && value.getStatus() == RequestStatus.PROCESSING) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.BLANK
                && value.getStatus() == RequestStatus.BLANK) {
              filtered.add(value);
            }
          } else {
            filtered.add(value);
          }
        });

    filtered.forEach(
        (value) -> {
          if (assignedEmployeeFilter.getValue() != null) {
            if (value.getAssignedEmployee().equals(assignedEmployeeFilter.getValue())) {
              avTableView.getItems().add(value);
            }
          } else {
            avTableView.getItems().add(value);
          }
        });

    setLabel(name);
    mainVbox.getChildren().add(avTableView);
  }

  private void makeTableSecurity(String name) {
    page = name;
    mainVbox.getChildren().clear();
    securityTableView.getItems().clear();
    securityTableView = securityTable.getTable();

    //    change the datatype here to avRequests once it exists. Setting up controller for it to
    // work.
    List<GeneralRequest> objectList = DBSession.getAllRequests();
    List<GeneralRequest> filtered = new ArrayList<>();
    objectList.forEach(
        (value) -> {
          if (requestStatusFilter.getValue() != null) {
            if (requestStatusFilter.getValue() == RequestStatus.DONE
                && value.getStatus() == RequestStatus.DONE) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.PROCESSING
                && value.getStatus() == RequestStatus.PROCESSING) {
              filtered.add(value);
            } else if (requestStatusFilter.getValue() == RequestStatus.BLANK
                && value.getStatus() == RequestStatus.BLANK) {
              filtered.add(value);
            }
          } else {
            filtered.add(value);
          }
        });

    filtered.forEach(
        (value) -> {
          if (assignedEmployeeFilter.getValue() != null) {
            if (value.getAssignedEmployee().equals(assignedEmployeeFilter.getValue())) {
              securityTableView.getItems().add(value);
            }
          } else {
            securityTableView.getItems().add(value);
          }
        });

    setLabel(name);
    mainVbox.getChildren().add(securityTableView);
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
    if (page.equals("Sanitation")) {
      makeTableSani(page);
    } else if (page.equals("Internal Patient Transportation")) {
      makeTableTrans(page);
    } else if (page.equals("Computer")) {
      makeTableCom(page);
    } else if (page.equals("Audio and Visual")) {
      makeTableAV(page);
    } else if (page.equals("Security")) {
      makeTableSecurity(page);
    }
  }
}
