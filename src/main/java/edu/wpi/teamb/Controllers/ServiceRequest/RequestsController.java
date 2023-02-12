package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.ORMType;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class RequestsController {

  // Transportation Columns
  @FXML TableView<Object> TransportationTable;
  @FXML TableView<Object> SanitationTable;
  @FXML TableView ComputerTable;
  @FXML TableColumn tranLastNameColumn;
  @FXML TableColumn tranFirstNameColumn;
  @FXML TableColumn tranEmployeeIDColumn;
  @FXML TableColumn tranEmailColumn;
  @FXML TableColumn tranUrgencyColumn;
  @FXML TableColumn tranAssignedStaffColumn;
  @FXML TableColumn tranPatientIDColumn;
  @FXML TableColumn tranCurrentLocationColumn;
  @FXML TableColumn tranDestinationColumn;
  @FXML TableColumn tranEquipmentColumn;
  @FXML TableColumn tranNotesColumn;
  @FXML TableColumn tranStatusColumn;

  @FXML TableColumn saniLastNameColumn;
  @FXML TableColumn saniFirstNameColumn;
  @FXML TableColumn saniEmployeeIDColumn;
  @FXML TableColumn saniEmailColumn;
  @FXML TableColumn saniUrgencyColumn;
  @FXML TableColumn saniAssignedStaffColumn;
  @FXML TableColumn saniTypeOfCleanupColumn;
  @FXML TableColumn saniLocationColumn;
  @FXML TableColumn saniNotesColumn;
  @FXML TableColumn saniStatusColumn;

  @FXML TableColumn comLastNameColumn;
  @FXML TableColumn comFirstNameColumn;
  @FXML TableColumn comEmployeeIDColumn;
  @FXML TableColumn comEmailColumn;
  @FXML TableColumn comUrgencyColumn;
  @FXML TableColumn comAssignedStaffColumn;
  @FXML TableColumn comTypeOfRepairColumn;
  @FXML TableColumn comDeviceColumn;
  @FXML TableColumn comLocationColumn;
  @FXML TableColumn comNotesColumn;
  @FXML TableColumn comStatusColumn;
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
    saniButton.setOnAction(e -> makeTable(saniColumns, ORMType.SREQUEST));
    transButton.setOnAction(e -> makeTable(transColumns, ORMType.PTREQUEST));
    comButton.setOnAction(e -> makeTable(comColumns, ORMType.CREQUEST));
    mainVbox.setPadding(new Insets(50, 20, 0, 20));
  }

  private void makeTable(List<String> columns, ORMType type) {
    mainVbox.getChildren().clear();
    TableView t = new TableView();
    for (String colName : columns) {
      TableColumn col = new TableColumn();
      t.getColumns().add(col);
      col.setText(colName);
      col.setCellValueFactory(new PropertyValueFactory<>(colName));
    }
    List<Object> objectList = DBSession.getAll(type);
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    mainVbox.getChildren().add(t);
  }
}
