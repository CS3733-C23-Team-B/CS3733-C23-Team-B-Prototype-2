package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Database.Requests.PatientTransportationRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SubmittedTransportationRequestTable extends SubmittedBaseRequestTable {
  @FXML private TableColumn patientID = new TableColumn<>();
  @FXML private TableColumn patientCurrentLocation = new TableColumn<>();
  @FXML private TableColumn patientDestinationLocation = new TableColumn();
  @FXML private TableColumn equipmentNeeded = new TableColumn<>();

  @Override
  public void initialize() {
    super.initialize();
    addCol(patientID, "patientID");
    addCol(patientCurrentLocation, "patientCurrentLocation");
    addCol(patientDestinationLocation, "patientDestinationLocation");
    addCol(equipmentNeeded, "equipmentNeeded");
    super.setTable();
  }

  @Override
  public TableView getTable(RequestStatus status, String Employee) {
    table.getItems().clear();
    super.filterTable(status, Employee, convertObj());
    return table;
  }

  private List<GeneralRequest> convertObj() {
    List<GeneralRequest> grList = new ArrayList<>();
    List<PatientTransportationRequest> objectList = DBSession.getAllPTRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }

  protected List<GeneralRequest> getPTRequests() {
    return convertObj();
  }
}
