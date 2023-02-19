package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SubmittedSanitationRequestTable extends SubmittedGeneralRequestTable {

  @FXML private TableColumn typeOfCleanUp = new TableColumn<>();
  @FXML private TableColumn cleanUpLocation = new TableColumn<>();

  @Override
  public void initialize() {
    super.initialize();
    addCol(typeOfCleanUp, "typeOfCleanUp");
    addCol(cleanUpLocation, "cleanUpLocation");
    setTable();
  }
}
