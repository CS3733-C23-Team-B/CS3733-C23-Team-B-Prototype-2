package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SubmittedComputerRequestTable extends SubmittedGeneralRequestTable {

  @FXML private TableColumn device = new TableColumn<>();
  @FXML private TableColumn typeOfRepair = new TableColumn<>();
  @FXML private TableColumn repairLocation = new TableColumn();

  @Override
  public void initialize() {
    super.initialize();
    addCol(device, "device");
    addCol(typeOfRepair, "typeOfRepair");
    addCol(repairLocation, "repairLocation");
    setTable();
  }
}
