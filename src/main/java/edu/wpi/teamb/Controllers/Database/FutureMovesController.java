package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class FutureMovesController {

  @FXML TableView table1;
  @FXML TableColumn moveDate;
  @FXML TableColumn locationName;
  @FXML TableColumn node;

  @FXML MFXDatePicker datePicker;

  StringConverter<Boolean> converter =
      new StringConverter<Boolean>() {
        @Override
        public String toString(Boolean object) {
          return object.toString();
        }

        @Override
        public Boolean fromString(String string) {
          return (string.equals("true"));
        }
      };

  public void initialize() {
    Map<String, Move> movesMap = new HashMap<>();
    moveDate.setCellValueFactory(new PropertyValueFactory<>("moveDate"));
    locationName.setCellValueFactory(new PropertyValueFactory<>("locationName"));
    node.setCellValueFactory(new PropertyValueFactory<>("node"));

    Date date = new Date(2023, 01, 01);
    if (datePicker.getValue() != null) {
      date = Date.valueOf(datePicker.getValue());
    }

    movesMap = DBSession.getLNMoves(date);
    movesMap.forEach(
        (key, value) -> {
          table1.getItems().add(value);
        });
  }

  public void newMove() {
    Navigation.navigate(Screen.MOVE_CREATOR);
  }

  public void backButton() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void selectDate() {}
}
