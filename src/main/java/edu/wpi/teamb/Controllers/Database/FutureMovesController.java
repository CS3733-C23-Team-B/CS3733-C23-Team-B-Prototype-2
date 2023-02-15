package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
  List<Move> movesList = new ArrayList<Move>();

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
    moveDate.setCellValueFactory(new PropertyValueFactory<>("moveDate"));
    locationName.setCellValueFactory(new PropertyValueFactory<>("locationName"));
    node.setCellValueFactory(new PropertyValueFactory<>("node"));
    updateFutureMoves();
  }

  public void newMove() {
    Navigation.navigate(Screen.MOVE_CREATOR);
  }

  public void backButton() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void selectDate() {
    updateFutureMoves();
  }

  public void updateFutureMoves() {
    Date date = new Date(123, 0, 1);
    movesList = DBSession.getFutureMoves(date);
    movesList.forEach(
        (value) -> {
          //          List<String> row = new ArrayList<String>();
          //          row.add(value.getMoveDate().toString());
          //          row.add(value.getLocationName().getLongName());
          //          row.add(value.getNode().getNodeID());
          table1.getItems().add(value);
        });
    table1.refresh();
  }
}
