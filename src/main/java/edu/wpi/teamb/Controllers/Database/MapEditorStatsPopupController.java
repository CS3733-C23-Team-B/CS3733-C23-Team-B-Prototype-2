package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DAO.RequestDAO;
import edu.wpi.teamb.Database.Requests.*;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MapEditorStatsPopupController {
  @FXML private GridPane popupWindow;
  @FXML private Label titleField;
  @FXML private GridPane chartAndTablePane;
  private TableView table;
  private BarChart chart;
  private String lName;
  private List<SecurityRequest> secRs;
  private List<SanitationRequest> sanRs;
  private List<PatientTransportationRequest> ptRs;
  private List<ComputerRequest> cRs;
  private List<MedicineDeliveryRequest> medicineRs;
  private List<MedicalEquipmentDeliveryRequest> medequipRs;
  private List<FacilitiesRequest> facRs;
  private List<AudioVideoRequest> avRs;

  public void initialize() {
    RequestDAO.refreshRequests();
    lName = MapEditorController.getInstance().getCurrentLoc();
    secRs = RequestDAO.getAllSecRequests(lName);
    sanRs = RequestDAO.getAllSanRequests(lName);
    ptRs = RequestDAO.getAllPTRequests(lName);
    cRs = RequestDAO.getAllCRequests(lName);
    medicineRs = RequestDAO.getAllMDRequests(lName);
    medequipRs = RequestDAO.getAllMEDRequests(lName);
    facRs = RequestDAO.getAllFacRequests(lName);
    avRs = RequestDAO.getAllAVRequests(lName);

    CategoryAxis types = new CategoryAxis();
    types.setLabel("Request Types");
    NumberAxis sums = new NumberAxis();
    sums.setLabel("Total");
    chart = new BarChart(types, sums);

    XYChart.Series sr = new XYChart.Series();
    sr.getData().add(new XYChart.Data("Security", secRs.size()));
    sr.getData().add(new XYChart.Data("Sanitation", sanRs.size()));
    sr.getData().add(new XYChart.Data("Patient Transportation", ptRs.size()));
    sr.getData().add(new XYChart.Data("Computer", cRs.size()));
    sr.getData().add(new XYChart.Data("Medicine", medicineRs.size()));
    sr.getData().add(new XYChart.Data("Medical Equipment", medequipRs.size()));
    sr.getData().add(new XYChart.Data("Facilities", facRs.size()));
    sr.getData().add(new XYChart.Data("Audio Video", avRs.size()));

    chart.getData().add(sr);
    chartAndTablePane.add(chart, 0, 0, 1, 1);

    titleField.setText("Request Statistics For Location: " + lName);

    table = new TableView<>();
    updatePage(RequestType.SECURITY);
  }

  public void updatePage(RequestType t) {
    TableColumn rIDCol = new TableColumn<GeneralRequest, Integer>("Request ID");
    TableColumn empIDCol = new TableColumn<GeneralRequest, String>("Requestor ID");
    TableColumn assignCol = new TableColumn<GeneralRequest, String>("Assigned To");
    TableColumn dateCol = new TableColumn<GeneralRequest, String>("Date");
    TableColumn urgCol = new TableColumn<GeneralRequest, String>("Urgency");
    TableColumn noteCol = new TableColumn<GeneralRequest, String>("Notes");
    TableColumn statusCol = new TableColumn<GeneralRequest, String>("Status");

    rIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    empIDCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
    assignCol.setCellValueFactory(new PropertyValueFactory<>("assignedEmployee"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    urgCol.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    noteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    ObservableList<String> list = FXCollections.observableArrayList();
    table.setItems(list);
    table.getColumns().addAll(rIDCol, empIDCol, dateCol, urgCol, statusCol, noteCol, assignCol);
    switch (t) {
      case SECURITY:
        TableColumn equipCol = new TableColumn<GeneralRequest, String>("Equipment");
        TableColumn issueCol = new TableColumn<GeneralRequest, String>("Issue");
        TableColumn numCol = new TableColumn<GeneralRequest, Integer>("Number Required");

        equipCol.setCellValueFactory(new PropertyValueFactory<>("equipmentNeeded"));
        issueCol.setCellValueFactory(new PropertyValueFactory<>("issueType"));
        numCol.setCellValueFactory(new PropertyValueFactory<>("numberRequired"));

        table.getColumns().addAll(equipCol, issueCol, numCol);
        table.getItems().addAll(secRs);
        break;

      case SANITATION:
        TableColumn typeCol = new TableColumn<GeneralRequest, String>("Type");

        typeCol.setCellValueFactory(new PropertyValueFactory<>("typeOfCleanUp"));

        table.getColumns().addAll(typeCol);
        table.getItems().addAll(sanRs);
        break;

      case PATIENTTRANSPORTATION:
        TableColumn transportEquipCol = new TableColumn<GeneralRequest, String>("Equipment");
        TableColumn destinationCol = new TableColumn<GeneralRequest, String>("Destination");
        TableColumn patientCol = new TableColumn<GeneralRequest, String>("Patient");

        transportEquipCol.setCellValueFactory(new PropertyValueFactory<>("equipmentNeeded"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("patientDestination"));
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        table.getColumns().addAll(transportEquipCol, destinationCol, patientCol);
        table.getItems().addAll(ptRs);
        break;

      case COMPUTER:
        TableColumn repairTypeCol = new TableColumn<GeneralRequest, String>("Type");
        TableColumn deviceCol = new TableColumn<GeneralRequest, String>("Device");

        repairTypeCol.setCellValueFactory(new PropertyValueFactory<>("typeOfRepair"));
        deviceCol.setCellValueFactory(new PropertyValueFactory<>("device"));

        table.getColumns().addAll(repairTypeCol, deviceCol);
        table.getItems().addAll(cRs);
        break;

      case MEDICINE:
        TableColumn medTypeCol = new TableColumn<GeneralRequest, String>("Type");
        TableColumn dosageCol = new TableColumn<GeneralRequest, String>("Dosage");
        TableColumn mdPatientCol = new TableColumn<GeneralRequest, String>("Patient");

        medTypeCol.setCellValueFactory(new PropertyValueFactory<>("medicineType"));
        dosageCol.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        mdPatientCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        table.getColumns().addAll(medTypeCol, dosageCol, mdPatientCol);
        table.getItems().addAll(medicineRs);
        break;

      case MEDICALEQUIPMENT:
        TableColumn medEquipCol = new TableColumn<GeneralRequest, String>("Equipment");

        medEquipCol.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));

        table.getColumns().addAll(medEquipCol);
        table.getItems().addAll(medequipRs);
        break;

      case FACILITIES:
        TableColumn facTypeCol = new TableColumn<GeneralRequest, String>("Type");

        facTypeCol.setCellValueFactory(new PropertyValueFactory<>("maintenanceType"));

        table.getColumns().addAll(facTypeCol);
        table.getItems().addAll(facRs);
        break;

      case AUDIOVISUAL:
        TableColumn avTypeCol = new TableColumn<GeneralRequest, String>("Type");

        avTypeCol.setCellValueFactory(new PropertyValueFactory<>("AVType"));

        table.getColumns().addAll(avTypeCol);
        table.getItems().addAll(avRs);
        break;

      default:
        break;
    }
    chartAndTablePane.add(table, 1, 0, 1, 1);
  }

  public void backButtonClicked() {
    Popup.hidePopup(Screen.MAP_EDITOR_LOCATION_STATS_POPUP);
  }
}
