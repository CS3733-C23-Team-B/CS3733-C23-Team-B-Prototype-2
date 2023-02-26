package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Database.Requests.SanitationRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SubmittedSanitationRequestTable extends SubmittedBaseRequestTable {

  @FXML private TableColumn typeOfCleanUp = new TableColumn<>();
  @FXML private TableColumn cleanUpLocation = new TableColumn<>();

  @Override
  public void initialize() {
    super.initialize();
    //    addCol(typeOfCleanUp, "typeOfCleanUp");
    //    addCol(cleanUpLocation, "cleanUpLocation");
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
    List<SanitationRequest> objectList = DBSession.getAllSanRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }
}
