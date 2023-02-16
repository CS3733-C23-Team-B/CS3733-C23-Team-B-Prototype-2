package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Entities.RequestStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

public class SubmittedServiceRequestsController {
  @FXML VBox mainVbox;
  @FXML MFXButton saniButton;
  @FXML MFXButton transButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton audioButton;

  public void initialize() {
    List<String> saniColumns =
        List.of(
            new String[] {
              "lastname",
              "firstname",
              "employeeID",
              "email",
              "urgency",
              "assignedEmployee",
              "typeOfCleanUp",
              "cleanUpLocation",
              "status",
              "notes"
            });
    List<String> transColumns =
        List.of(
            "lastname",
            "firstname",
            "employeeID",
            "email",
            "urgency",
            "assignedEmployee",
            "patientID",
            "patientCurrentLocation",
            "patientDestinationLocation",
            "equipmentNeeded",
            "status",
            "notes");
    List<String> comColumns =
        List.of(
            "lastname",
            "firstname",
            "employeeID",
            "email",
            "urgency",
            "assignedEmployee",
            "typeOfRepair",
            "repairLocation",
            "notes",
            "status",
            "device");
    saniButton.setOnAction(e -> makeTableSani(saniColumns, "Sanitation"));
    transButton.setOnAction(e -> makeTableTrans(transColumns, "Internal Patient Transportation"));
    comButton.setOnAction(e -> makeTableCom(comColumns, "Computer"));
    mainVbox.setPadding(new Insets(50, 20, 0, 20));
  }

  private void makeTableSani(List<String> columns, String name) {
    Login l = SigninController.getCurrentUser();
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    for (String colName : columns) {
      if ((colName.equals("status") && DBSession.isAdmin(l))) {
        TableColumn<SanitationRequest, RequestStatus> status = new TableColumn<>();
        t.getColumns().add(status);
        status.setText("status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        status.setOnEditCommit(
            e -> {
              SanitationRequest SRequest =
                  e.getTableView().getItems().get(e.getTablePosition().getRow());
              SRequest.setStatus(e.getNewValue());
              DBSession.updateRequest(SRequest);
            });
        status.setEditable(true);
      } else {
        TableColumn col = new TableColumn();
        t.getColumns().add(col);
        col.setText(colName);
        col.setCellValueFactory(new PropertyValueFactory<>(colName));
      }
      t.setEditable(true);
    }
    List<SanitationRequest> objectList = DBSession.getAllSanRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    Label la = new Label();
    la.setText(name);
    la.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(la);
    mainVbox.getChildren().add(t);
  }

  private void makeTableTrans(List<String> columns, String name) {
    Login l = SigninController.getCurrentUser();
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    t.setEditable(true);
    for (String colName : columns) {
      if ((colName.equals("status") && DBSession.isAdmin(l))) {
        TableColumn<PatientTransportationRequest, RequestStatus> status = new TableColumn<>();
        t.getColumns().add(status);
        status.setText("status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        status.setOnEditCommit(
            e -> {
              PatientTransportationRequest PTRequest =
                  e.getTableView().getItems().get(e.getTablePosition().getRow());
              PTRequest.setStatus(e.getNewValue());
              DBSession.updateRequest(PTRequest);
            });
        status.setEditable(true);
      } else {
        TableColumn col = new TableColumn();
        t.getColumns().add(col);
        col.setText(colName);
        col.setCellValueFactory(new PropertyValueFactory<>(colName));
      }
      t.setEditable(true);
    }

    List<PatientTransportationRequest> objectList = DBSession.getAllPTRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    Label la = new Label();
    la.setText(name);
    la.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(la);
    mainVbox.getChildren().add(t);
  }

  private void makeTableCom(List<String> columns, String name) {
    Login l = SigninController.getCurrentUser();
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    for (String colName : columns) {
      if ((colName.equals("status") && DBSession.isAdmin(l))) {
        TableColumn<ComputerRequest, RequestStatus> status = new TableColumn<>();
        t.getColumns().add(status);
        status.setText("status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        status.setOnEditCommit(
            e -> {
              ComputerRequest CRequest =
                  e.getTableView().getItems().get(e.getTablePosition().getRow());
              CRequest.setStatus(e.getNewValue());
              DBSession.addRequest(CRequest);
            });
        status.setEditable(true);
      } else {
        TableColumn col = new TableColumn();
        t.getColumns().add(col);
        col.setText(colName);
        col.setCellValueFactory(new PropertyValueFactory<>(colName));
      }
      t.setEditable(true);
    }
    List<ComputerRequest> objectList = DBSession.getAllCRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    Label la = new Label();
    la.setText(name);
    la.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(la);
    mainVbox.getChildren().add(t);
  }

  StringConverter<RequestStatus> converter =
      new StringConverter<>() {
        @Override
        public String toString(RequestStatus object) {
          if (object == null) return "";
          return object.toString();
        }

        @Override
        public RequestStatus fromString(String string) {
          switch (string) {
            case ("blank"):
              return RequestStatus.BLANK;
            case ("processing"):
              return RequestStatus.PROCESSING;
            case ("done"):
              return RequestStatus.DONE;
            default:
              return RequestStatus.PROCESSING;
          }
        }
      };
}
