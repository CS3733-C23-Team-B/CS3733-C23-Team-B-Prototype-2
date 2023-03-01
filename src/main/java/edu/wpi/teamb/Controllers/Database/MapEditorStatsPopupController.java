package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DAO.RequestDAO;
import edu.wpi.teamb.Database.Requests.*;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MapEditorStatsPopupController {
  @FXML private GridPane popupWindow;
  @FXML private Label titleField;
  @FXML private GridPane chartAndTablePane;
  private TableView table;
  private BarChart<String, Number> chart;
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
    lName = MapEditorController.getInstance().getCurrentLoc();
    secRs = RequestDAO.getAllSecRequests(lName);
    sanRs = RequestDAO.getAllSanRequests(lName);
    ptRs = RequestDAO.getAllPTRequests(lName);
    cRs = RequestDAO.getAllCRequests(lName);
    medicineRs = RequestDAO.getAllMDRequests(lName);
    medequipRs = RequestDAO.getAllMEDRequests(lName);
    facRs = RequestDAO.getAllFacRequests(lName);
    avRs = RequestDAO.getAllAVRequests(lName);

    ArrayList<Integer> sizes = new ArrayList<Integer>();
    sizes.add(secRs.size());
    sizes.add(sanRs.size());
    sizes.add(ptRs.size());
    sizes.add(cRs.size());
    sizes.add(medicineRs.size());
    sizes.add(medequipRs.size());
    sizes.add(facRs.size());
    sizes.add(avRs.size());
    Integer max = Collections.max(sizes);

    CategoryAxis types = new CategoryAxis();
    types.setLabel("Request Types");
    NumberAxis sums = new NumberAxis(0, max + 1, 1);
    sums.setTickMarkVisible(false);
    sums.setLabel("Total");
    sums.setTickLabelGap(5);

    chart = new BarChart(types, sums);

    XYChart.Series sr = new XYChart.Series();
    sr.getData().add(new XYChart.Data(RequestType.SECURITY.toString(), secRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.SANITATION.toString(), sanRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.PATIENTTRANSPORTATION.toString(), ptRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.COMPUTER.toString(), cRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.MEDICINE.toString(), medicineRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.MEDICALEQUIPMENT.toString(), medequipRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.FACILITIES.toString(), facRs.size()));
    sr.getData().add(new XYChart.Data(RequestType.AUDIOVIDEO.toString(), avRs.size()));

    chart.getData().add(sr);

    for (XYChart.Series<String, Number> series : chart.getData()) {
      for (XYChart.Data<String, Number> data : series.getData()) {
        String type = data.getXValue();
        switch (type) {
          case "Security":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.SECURITY);
                    });
            break;

          case "Sanitation":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.SANITATION);
                    });
            break;

          case "Internal Patient Transportation":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.PATIENTTRANSPORTATION);
                    });
            break;

          case "Computer":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.COMPUTER);
                    });
            break;

          case "Medicine Delivery":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.MEDICINE);
                    });
            break;

          case "Medical Equipment Delivery":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.MEDICALEQUIPMENT);
                    });
            break;

          case "Facilities Maintenance":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.FACILITIES);
                    });
            break;

          case "Audio and Visual":
            data.getNode().setCursor(Cursor.HAND);
            data.getNode()
                .setOnMouseReleased(
                    (MouseEvent event) -> {
                      updatePage(RequestType.AUDIOVIDEO);
                    });
            break;

          default:
            break;
        }
      }
    }

    chart.setLegendVisible(false);
    chart.setHorizontalGridLinesVisible(false);
    chart.setVerticalGridLinesVisible(false);
    chart.setStyle("-fx-background-color: #F2F2F2");

    chartAndTablePane.add(chart, 0, 0, 1, 1);

    titleField.setText("Request Statistics For Location: " + lName);

    table = new TableView<>();
    updatePage(RequestType.SECURITY);
  }

  public void updatePage(RequestType t) {
    for (XYChart.Series<String, Number> series : chart.getData()) {
      for (XYChart.Data<String, Number> data : series.getData()) {
        if (data.getXValue().equals(t.toString())) {
          data.getNode().setStyle("-fx-background-color: #E89F55");
        } else {
          data.getNode().setStyle("-fx-background-color: #21357E;");
        }
      }
    }

    table.getColumns().clear();

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
        destinationCol.setCellValueFactory(
            new PropertyValueFactory<>("patientDestinationLocation"));
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

      case AUDIOVIDEO:
        TableColumn avTypeCol = new TableColumn<GeneralRequest, String>("Type");

        avTypeCol.setCellValueFactory(new PropertyValueFactory<>("AVType"));

        table.getColumns().addAll(avTypeCol);
        table.getItems().addAll(avRs);
        break;

      default:
        break;
    }
    for (int i = chartAndTablePane.getChildren().size() - 1; i >= 0; i--) {
      if (chartAndTablePane.getChildren().get(i) instanceof TableView<?>) {
        chartAndTablePane.getChildren().remove(i);
      }
    }
    chartAndTablePane.add(table, 1, 0, 1, 1);
  }

  public void backButtonClicked() {
    Popup.hidePopup(Screen.MAP_EDITOR_LOCATION_STATS_POPUP);
  }
}
