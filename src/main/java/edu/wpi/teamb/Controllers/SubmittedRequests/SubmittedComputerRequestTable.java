package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.ComputerRequest;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SubmittedComputerRequestTable extends SubmittedBaseRequestTable {

  @FXML private TableColumn device = new TableColumn<>();
  @FXML private TableColumn typeOfRepair = new TableColumn<>();
  @FXML private TableColumn repairLocation = new TableColumn();

  @Override
  public void initialize() {
    super.initialize();
    //    addCol(device, "device");
    //    addCol(typeOfRepair, "typeOfRepair");
    //    addCol(repairLocation, "repairLocation");
    setTable();
  }

  public TableView getTable(
      RequestStatus status,
      String Employee,
      String requestor,
      Urgency urgency,
      Boolean myRequests) {
    table.getItems().clear();
    super.filterTable(status, Employee, requestor, convertObj(), urgency, myRequests);
    return table;
  }

  private List<GeneralRequest> convertObj() {
    List<GeneralRequest> grList = new ArrayList<>();
    List<ComputerRequest> objectList = DBSession.getAllCRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }
}
