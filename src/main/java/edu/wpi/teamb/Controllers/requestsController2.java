package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.ORMType;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class requestsController2 {

  // Transportation Columns
  @FXML TableView TransportationTable;
  @FXML TableView SanitationTable;
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

  @FXML TableColumn saniNameColumn;
  @FXML TableColumn saniTypeColumn;
  @FXML TableColumn saniUrgencyColumn;
  @FXML TableColumn saniLocationColumn;
  @FXML TableColumn saniAssignmentColumn;
  @FXML TableColumn saniStatusColumn;
  @FXML TableColumn saniNotesColumn;

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

  public void initialize() {
    List<Object> TransportRequestList;
    List<Object> SanitationRequestList;
    List<Object> ComputerRequestList;

    tranLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    tranFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    tranEmployeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
    tranEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    tranUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    tranAssignedStaffColumn.setCellValueFactory(new PropertyValueFactory<>("assignedEmployee"));
    tranPatientIDColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));
    tranCurrentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    tranDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
    tranEquipmentColumn.setCellValueFactory(new PropertyValueFactory<>("equipment"));
    tranNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    tranStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    saniUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    saniTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfCleanUp"));
    saniNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    saniLocationColumn.setCellValueFactory(new PropertyValueFactory<>("cleanUpLocation"));
    saniAssignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignedEmployee"));
    saniStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    saniNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

    comLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    comFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    comEmployeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
    comEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    comUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    comAssignedStaffColumn.setCellValueFactory(new PropertyValueFactory<>("assignedstaff"));
    comTypeOfRepairColumn.setCellValueFactory(new PropertyValueFactory<>("typeofrepair"));
    comLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    comNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    comStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    TransportRequestList = DBSession.getAll(ORMType.PTREQUEST);
    TransportRequestList.forEach(
        (value) -> {
          TransportationTable.getItems().add(value);
        });
    SanitationRequestList = DBSession.getAll(ORMType.SREQUEST);
    SanitationRequestList.forEach(
        (value) -> {
          SanitationTable.getItems().add(value);
        });
    ComputerRequestList = DBSession.getAll(ORMType.CREQUEST);
    ComputerRequestList.forEach(
        (value) -> {
          ComputerTable.getItems().add(value);
        });
    Button b = new Button();
    b.setText("Back");
    b.setOnAction(e -> Navigation.navigate(Screen.REQUESTS));
  }
}
