package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SubmittedSanitationRequestTable extends SubmittedGeneralRequestTable {

  @FXML TableColumn typeOfCleanUp = new TableColumn<>();
  @FXML TableColumn cleanUpLocation = new TableColumn<>();

  @Override
  public void initialize() {
    super.initialize();
    addCol(typeOfCleanUp, "typeOfCleanUp");
    addCol(cleanUpLocation, "cleanUpLocation");
    setTable();
  }

  public void SubmittedSanitationRequestTable() {}
}
