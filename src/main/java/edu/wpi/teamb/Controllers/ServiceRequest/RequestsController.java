package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.*;
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

public class RequestsController {
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
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    for (String colName : columns) {
      TableColumn col = new TableColumn();
      t.getColumns().add(col);
      col.setText(colName);
      col.setCellValueFactory(new PropertyValueFactory<>(colName));
    }
    List<SanitationRequest> objectList = DBSession.getAllSRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    Label l = new Label();
    l.setText(name);
    l.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(l);
    mainVbox.getChildren().add(t);
  }

  private void makeTableTrans(List<String> columns, String name) {
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    for (String colName : columns) {
      TableColumn col = new TableColumn();
      t.getColumns().add(col);
      col.setText(colName);
      if (colName.equals("urgency")) {
        editableCols(col, t);
      }
      col.setCellValueFactory(new PropertyValueFactory<>(colName));
    }

    List<PatientTransportationRequest> objectList = DBSession.getAllPTRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    Label l = new Label();
    l.setText(name);
    l.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(l);
    mainVbox.getChildren().add(t);
  }

  public void editableCols(TableColumn col, TableView table) {
//    col.setCellFactory(TextFieldTableCell.forTableColumn(converter));
//    col.setOnEditCommit(
//        e -> {
//          Login login = e.getTableView().getItems().get(e.getTablePosition().getRow());
//          login.setAdmin(e.getNewValue());
//          DBSession.updateAdmin(login.getUsername(), login.getAdmin());
//        });
//
//    table.setEditable(true);
  }

  StringConverter<Boolean> converter =
      new StringConverter<Boolean>() {
        @Override
        public String toString(Boolean object) {
          return object.toString();
        }

        @Override
        public Boolean fromString(String string) {
          return (string.equals("true"));
        }
      };

  private void makeTableCom(List<String> columns, String name) {
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    for (String colName : columns) {
      TableColumn col = new TableColumn();
      t.getColumns().add(col);
      col.setText(colName);
      col.setCellValueFactory(new PropertyValueFactory<>(colName));
    }
    List<ComputerRequest> objectList = DBSession.getAllCRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    Label l = new Label();
    l.setText(name);
    l.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(l);
    mainVbox.getChildren().add(t);
  }
}
