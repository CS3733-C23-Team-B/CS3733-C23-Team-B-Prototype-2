package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DAO.RequestDAO;
import edu.wpi.teamb.Database.Requests.*;
import edu.wpi.teamb.Entities.RequestType;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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

    chartAndTablePane.getChildren().add(0, chart);
  }

  public void updatePage(RequestType t) {

    switch (t) {
      case SECURITY:
        break;

      case SANITATION:
        break;

      case PATIENTTRANSPORTATION:
        break;

      case COMPUTER:
        break;

      case MEDICINE:
        break;

      case MEDICALEQUIPMENT:
        break;

      case FACILITIES:
        break;

      case AUDIOVISUAL:
        break;

      default:
        break;
    }
  }

  public void backButtonClicked() {
    Popup.hidePopup(Screen.MAP_EDITOR_LOCATION_STATS_POPUP);
  }
}
