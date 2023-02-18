package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

public class SubmittedSecurityRequestTable extends SubmittedGeneralRequestTable {

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
}
