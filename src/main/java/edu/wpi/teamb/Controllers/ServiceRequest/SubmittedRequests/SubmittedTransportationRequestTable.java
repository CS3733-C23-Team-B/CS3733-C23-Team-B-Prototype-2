package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SubmittedTransportationRequestTable extends SubmittedGeneralRequestTable {
  @FXML TableColumn patientID = new TableColumn<>();
  @FXML TableColumn patientCurrentLocation = new TableColumn<>();
  @FXML TableColumn patientDestinationLocation = new TableColumn();
  @FXML TableColumn equipmentNeeded = new TableColumn<>();

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
