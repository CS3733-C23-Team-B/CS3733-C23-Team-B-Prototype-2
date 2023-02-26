package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.AudioVideoRequest;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SubmittedAVRequestTable extends SubmittedBaseRequestTable {

  //  add the right tablecols here for this request
  @FXML private TableColumn AVType = new TableColumn();
  @FXML private TableColumn location = new TableColumn();

  @Override
  public void initialize() {
    super.initialize();
    //    addCol(AVType, "AVType");
    //    addCol(location, "location");
    setTable();
  }

  @Override
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
    List<AudioVideoRequest> objectList = DBSession.getAllAVRequests();
    objectList.forEach(
        (value) -> {
          grList.add(value);
        });
    return grList;
  }
}
