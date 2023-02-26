package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Database.Requests.MedicineDeliveryRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TableView;

public class SubmittedMedicineRequestTable extends SubmittedBaseRequestTable {

  @Override
  public void initialize() {
    super.initialize();
    setTable();
  }

  public TableView getTable(
      RequestStatus status,
      String assignedEmployee,
      String requestor,
      Urgency urgency,
      Boolean myRequests,
      Date d) {
    table.getItems().clear();
    super.filterTable(status, assignedEmployee, requestor, convertObj(), urgency, myRequests, d);
    return table;
  }

  private List<GeneralRequest> convertObj() {
    List<GeneralRequest> grList = new ArrayList<>();
    //    change this once i have the query
    List<MedicineDeliveryRequest> objectList = DBSession.getAllMDRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }
}
