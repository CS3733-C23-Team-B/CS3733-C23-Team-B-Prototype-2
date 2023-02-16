package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SubmittedTransportationRequestTable extends SubmittedGeneralRequestTable {
  @FXML private TableColumn patientID = new TableColumn<>();
  @FXML private TableColumn patientCurrentLocation = new TableColumn<>();
  @FXML private TableColumn patientDestinationLocation = new TableColumn();
  @FXML private TableColumn equipmentNeeded = new TableColumn<>();

  @Override
  public void initialize() {
    super.initialize();
    addCol(patientID, "patientID");
    addCol(patientCurrentLocation, "patientCurrentLocation");
    addCol(patientDestinationLocation, "patientDestinationLocation");
    addCol(equipmentNeeded, "equipmentNeeded");
    setTable();
  }
}
