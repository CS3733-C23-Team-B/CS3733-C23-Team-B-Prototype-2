package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Requests.AudioVideoRequest;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;

public class SubmittedAVRequestTable extends SubmittedBaseRequestTable {

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
  public TableView getTable(
      RequestStatus status, String Employee, Urgency urgency, Boolean myRequests) {
    table.getItems().clear();
    super.filterTable(status, Employee, convertObj(), urgency, myRequests);
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

  protected List<GeneralRequest> getAVRequests() {
    return convertObj();
  }
}
