package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class KioskEditController {

  @FXML MFXComboBox moveDropdown;
  @FXML MFXTextField moveMessage;

  public void initialize() {
    List<Move> moves = DBSession.getAllMoves();
    List<String> l =
        moves.stream()
            .map(move -> move.getLocationName().getLongName())
            .collect(Collectors.toList());
    moveDropdown.setItems(FXCollections.observableList(l));
  }

  public void addKiosk() {
    if (moveMessage.getText().length() > 0 && moveDropdown.getValue() != null) {
      DBSession.addKioskMove((Move) moveDropdown.getValue(), moveMessage.getText());
    }
  }
}
