package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FutureMovesController {

  @FXML TableView table1;
  @FXML TableColumn moveDate;
  @FXML TableColumn locationName;
  @FXML TableColumn node;
  @FXML MFXDatePicker datePicker;
  List<Move> movesList = new ArrayList<Move>();
  boolean first = true;
  Date date;

  public void initialize() {
    moveDate.setCellValueFactory(new PropertyValueFactory<>("moveDate"));
    locationName.setCellValueFactory(new PropertyValueFactory<>("locationName"));
    node.setCellValueFactory(new PropertyValueFactory<>("node"));
    updateFutureMoves();
  }

  public void newMove() {
    Navigation.navigate(Screen.MOVE_CREATOR);
  }

  public void dateEntered() {
    LocalDate d = datePicker.getValue();
    ZoneId z = ZoneId.of("-05:00");
    ZonedDateTime zdt = d.atStartOfDay(z);
    Instant instant = zdt.toInstant();
    Date date = java.util.Date.from(instant);
    setDate(date);
    updateFutureMoves();
  }

  public void updateFutureMoves() {
    table1.getItems().clear();
    if (first) {
      movesList = DBSession.getFutureMoves(new Date(123, 0, 1));
      first = false;
    } else {
      movesList = DBSession.getFutureMoves(date);
    }

    movesList.forEach(
        (value) -> {
          table1.getItems().add(value);
        });
    table1.refresh();
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
