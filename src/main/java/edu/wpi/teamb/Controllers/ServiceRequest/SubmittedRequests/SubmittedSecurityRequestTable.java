package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Database.Requests.SecurityRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;

public class SubmittedSecurityRequestTable extends SubmittedBaseRequestTable {

  //  add the right tablecols here for this request
  //  @FXML private TableColumn device = new TableColumn<>();
  //  @FXML private TableColumn typeOfRepair = new TableColumn<>();
  //  @FXML private TableColumn repairLocation = new TableColumn();

  @Override
  public void initialize() {
    super.initialize();
    //    addCol(device, "device");
    //    addCol(typeOfRepair, "typeOfRepair");
    //    addCol(repairLocation, "repairLocation");
    setTable();
  }

  @Override
  public TableView getTable(RequestStatus status, String Employee, Urgency urgency) {
    table.getItems().clear();
    super.filterTable(status, Employee, convertObj(), urgency);
    return table;
  }

  private List<GeneralRequest> convertObj() {
    List<GeneralRequest> grList = new ArrayList<>();
    List<SecurityRequest> objectList = DBSession.getAllSecRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }

  protected List<GeneralRequest> getSecRequests() {
    return convertObj();
  }
}
