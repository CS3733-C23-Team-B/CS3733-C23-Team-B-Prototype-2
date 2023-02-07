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
    tranEmployeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeIDname"));
    tranEmailColumn.setCellValueFactory(new PropertyValueFactory<>("emailname"));
    tranUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgencyname"));
    tranAssignedStaffColumn.setCellValueFactory(new PropertyValueFactory<>("assignedstaffname"));
    tranPatientIDColumn.setCellValueFactory(new PropertyValueFactory<>("patientIDname"));
    tranCurrentLocationColumn.setCellValueFactory(
        new PropertyValueFactory<>("currentlocationname"));
    tranDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationname"));
    tranEquipmentColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentname"));
    tranNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notesname"));
    tranStatusColumn.setCellValueFactory(new PropertyValueFactory<>("statusname"));

    saniUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    saniTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfCleanUp"));
    saniNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    saniLocationColumn.setCellValueFactory(new PropertyValueFactory<>("cleanUpLocation"));
    saniAssignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignedEmployee"));
    saniStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    saniNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

    comLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    comFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    comEmployeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeIDname"));
    comEmailColumn.setCellValueFactory(new PropertyValueFactory<>("emailname"));
    comUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgencyname"));
    comAssignedStaffColumn.setCellValueFactory(new PropertyValueFactory<>("assignedstaffname"));
    comTypeOfRepairColumn.setCellValueFactory(new PropertyValueFactory<>("typeofrepairname"));
    comLocationColumn.setCellValueFactory(new PropertyValueFactory<>("locationname"));
    comNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notesname"));
    comStatusColumn.setCellValueFactory(new PropertyValueFactory<>("statusname"));

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
