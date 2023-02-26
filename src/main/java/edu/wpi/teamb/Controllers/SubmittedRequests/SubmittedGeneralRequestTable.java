package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SubmittedGeneralRequestTable extends SubmittedBaseRequestTable {

  //  add the right tablecols here for this request
  @FXML private TableColumn requestType = new TableColumn<>();

  @Override
  public void initialize() {
    super.initialize();
    addColGR(requestType, "requestType");
    setTable();
  }

  public TableView getTable(RequestStatus status, String employee, List<String> types) {
    //    table.getItems().clear();
    //    List<GeneralRequest> filtered = filterByRequest(types);
    //    super.filterTable(status, employee, filtered);
    return table;
  }

  @Override
  public TableView getTable(
      RequestStatus status,
      String assignedEmployee,
      String requestor,
      Urgency urgency,
      Boolean myRequestsOnly,
      Date d) {
    table.getItems().clear();
    List<GeneralRequest> objectList = filterDate(d);
    super.filterTable(status, assignedEmployee, requestor, objectList, urgency, myRequestsOnly, d);
    return table;
  }

  List<GeneralRequest> filterDate(Date d) {
    List<GeneralRequest> objectList = new ArrayList<>();
    if (d != null) {
      objectList = DBSession.getAllRequestsOnDate(d);
    } else {
      objectList = DBSession.getAllRequests();
    }
    return objectList;
  }

  private List<GeneralRequest> filterByRequest(List<String> types) {
    List<GeneralRequest> requests = new ArrayList<>();
    List<GeneralRequest> objectList = DBSession.getAllRequests();
    return requests;
  }
}
