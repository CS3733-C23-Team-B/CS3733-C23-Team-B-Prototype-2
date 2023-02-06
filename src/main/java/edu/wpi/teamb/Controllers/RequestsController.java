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

public class RequestsController {
  @FXML TableView requestsTable;
  @FXML TableView serviceTable;
  @FXML TableColumn nameColumn;
  @FXML TableColumn equipmentColumn;
  @FXML TableColumn urgencyColumn;
  @FXML TableColumn locationColumn;
  @FXML TableColumn destinationColumn;
  @FXML TableColumn notesColumn;
  @FXML TableColumn statusColumn;

  @FXML TableColumn saniUrgencyColumn;
  @FXML TableColumn saniTypeColumn;
  @FXML TableColumn saniNameColumn;
  @FXML TableColumn saniLocationColumn;
  @FXML TableColumn saniAssignmentColumn;
  @FXML TableColumn saniStatusColumn;
  @FXML TableColumn saniNotesColumn;

  public void initialize() {
    List<Object> TransportRequestList;
    List<Object> SanitationRequestList;
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    equipmentColumn.setCellValueFactory(new PropertyValueFactory<>("equipment"));
    urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
    notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    saniUrgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    saniTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfCleanUp"));
    saniNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    saniLocationColumn.setCellValueFactory(new PropertyValueFactory<>("cleanUpLocation"));
    saniAssignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignedEmployee"));
    saniStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    saniNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

    TransportRequestList = DBSession.getAll(ORMType.PTREQUEST);
    TransportRequestList.forEach(
        (value) -> {
          requestsTable.getItems().add(value);
        });

    SanitationRequestList = DBSession.getAll(ORMType.SREQUEST);
    SanitationRequestList.forEach(
        (value) -> {
          serviceTable.getItems().add(value);
        });
    Button b = new Button();
    b.setText("Back");
    b.setOnAction(e -> Navigation.navigate(Screen.REQUESTS));
  }
}
