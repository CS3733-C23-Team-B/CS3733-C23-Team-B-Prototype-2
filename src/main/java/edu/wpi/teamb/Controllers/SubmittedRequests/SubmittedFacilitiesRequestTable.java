package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Database.Requests.SecurityRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;

public class SubmittedFacilitiesRequestTable extends SubmittedBaseRequestTable {

  @Override
  public void initialize() {
    super.initialize();
    setTable();
  }

  public TableView getTable(
      RequestStatus status, String Employee, Urgency urgency, Boolean myRequests) {
    table.getItems().clear();
    super.filterTable(status, Employee, convertObj(), urgency, myRequests);
    return table;
  }

  private List<GeneralRequest> convertObj() {
    List<GeneralRequest> grList = new ArrayList<>();
//    change this once i have the query
    List<SecurityRequest> objectList = DBSession.getAllSecRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }
}
