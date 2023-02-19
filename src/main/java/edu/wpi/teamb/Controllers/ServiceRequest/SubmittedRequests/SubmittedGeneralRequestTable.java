package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.AudioVideoRequest;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import java.util.ArrayList;
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
    //    addCol(device, "device");
    //    addCol(typeOfRepair, "typeOfRepair");
    //    addCol(repairLocation, "repairLocation");
    setTable();
  }

  @Override
  public TableView getTable(RequestStatus status, String employee, List<String> types) {
    table.getItems().clear();
    List<GeneralRequest> filtered = filterByRequest(types);
    super.filterTable(status, employee, filtered);
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

  private List<GeneralRequest> filterByRequest(List<String> types) {
    List<GeneralRequest> requests = new ArrayList<>();
    List<GeneralRequest> objectList = DBSession.getAllRequests();
    return requests;
  }
}
