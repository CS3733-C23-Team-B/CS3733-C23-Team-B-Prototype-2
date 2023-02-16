package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Database.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SubmittedServiceRequestsController {
  @FXML VBox mainVbox;
  @FXML MFXButton saniButton;
  @FXML MFXButton transButton;
  @FXML MFXButton secButton;
  @FXML MFXButton comButton;
  @FXML MFXButton audioButton;
  SubmittedSanitationRequestTable saniTable = new SubmittedSanitationRequestTable();
  SubmittedTransportationRequestTable ptTable = new SubmittedTransportationRequestTable();
  SubmittedComputerRequestTable comTable = new SubmittedComputerRequestTable();

  public void initialize() {
    saniTable.initialize();
    ptTable.initialize();
    comTable.initialize();
    saniButton.setOnAction(e -> makeTableSani("Sanitation"));
    transButton.setOnAction(e -> makeTableTrans("Internal Patient Transportation"));
    comButton.setOnAction(e -> makeTableCom("Computer"));
    mainVbox.setPadding(new Insets(50, 20, 0, 20));
  }

  private void makeTableSani(String name) {
    mainVbox.getChildren().clear();
    TableView t = saniTable.getTable();

    List<SanitationRequest> objectList = DBSession.getAllSanRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    setLabel(name);
    mainVbox.getChildren().add(t);
  }

  private void makeTableTrans(String name) {
    mainVbox.getChildren().clear();
    TableView t = ptTable.getTable();

    List<PatientTransportationRequest> objectList = DBSession.getAllPTRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    setLabel(name);
    mainVbox.getChildren().add(t);
  }

  private void makeTableCom(String name) {
    mainVbox.getChildren().clear();
    TableView t = comTable.getTable();

    List<ComputerRequest> objectList = DBSession.getAllCRequests();
    objectList.forEach(
        (value) -> {
          t.getItems().add(value);
        });
    setLabel(name);
    mainVbox.getChildren().add(t);
  }

  private void setLabel(String name){
    Label la = new Label();
    la.setText(name);
    la.setFont(new Font("Ariel", 25));
    mainVbox.getChildren().add(la);

  }
}
