package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.TransportationDataset;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestsController {
  @FXML TableView requestsTable;
  @FXML TableColumn nameColumn;
  @FXML TableColumn equipmentColumn;
  @FXML TableColumn urgencyColumn;
  @FXML TableColumn locationColumn;
  @FXML TableColumn destinationColumn;
  @FXML TableColumn notesColumn;
  @FXML TableColumn statusColumn;

  public void initialize() {
    List<TransportationDataset> requestList;
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    equipmentColumn.setCellValueFactory(new PropertyValueFactory<>("equipment"));
    urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
    notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    try {
      requestList = TransportationDataset.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    requestList.forEach(
        (value) -> {
          requestsTable.getItems().add(value);
        });
    Button b = new Button();
    b.setText("Back");
    b.setOnAction(e -> Navigation.navigate(Screen.REQUESTS));
  }
}
