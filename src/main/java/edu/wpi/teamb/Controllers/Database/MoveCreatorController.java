package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Date;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MoveCreatorController {
  @FXML Text bigText;
  @FXML MFXFilterComboBox<String> nodeIdBox;
  @FXML MFXFilterComboBox<String> locationsBox;

  @FXML MFXDatePicker datePicker;
  @FXML Label lblDate;

  public void initialize() {
    nodeIdBox.setItems(getNodes());
    locationsBox.setItems(getLocations());
  }

  public void submitClicked() {

    String nodeID = nodeIdBox.getText();
    String location = locationsBox.getText();
    String date = datePicker.getValue().toString();
    System.out.println("date" + date);

    if (nodeID.isEmpty() || location.isEmpty() || date == null) {
      bigText.setText("Missing fields");
      bigText.setFill(Color.RED);
      return;
    }

    Move newMove = new Move();
    Node node = new Node();
    node.setNodeID(nodeID);
    newMove.setNode(node);
//    newMove.setLocationName();
    Date newDate = Date.valueOf(datePicker.getValue());
    newMove.setMoveDate(newDate);
    DBSession.addMove(newMove);

    cancelClicked();
  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void selectDate() {
    lblDate.setText(datePicker.getValue().toString());
  }

  private ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, LocationName> locationsDBList = DBSession.getLocationNames();

    locationsDBList.forEach((key, value) -> list.add(value.getLongName()));

    Sorting.quickSort(list);
    return list;
  }

  private ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, Node> nodeDBMap = DBSession.getNodes();

    nodeDBMap.forEach((key, value) -> list.add(value.getNodeID()));

    Sorting.quickSort(list);
    return list;
  }
}
