package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class KioskEditController {

  @FXML MFXFilterComboBox moveDropdown;
  @FXML MFXTextField moveMessage;
  @FXML Label timeLabel;
  @FXML Label dateLabel;

  private List<Move> moves;

  public void initialize() {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
    moves = DBSession.getAllMoves();
    List<String> l =
        moves.stream()
            .map(
                move ->
                    move.getLocationName().getLongName()
                        + ", "
                        + move.getNode().getNodeID()
                        + ", "
                        + fmt.format(move.getMoveDate()))
            .collect(Collectors.toList());
    moveDropdown.setItems(FXCollections.observableList(l));

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    String formattedDate = currentDate.format(formatter);

    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0),
                event -> {
                  LocalDateTime currentTime = LocalDateTime.now();
                  DateTimeFormatter timefmt = DateTimeFormatter.ofPattern("h:mm a");
                  timeLabel.setText(currentTime.format(timefmt));
                }),
            new KeyFrame(Duration.seconds(1)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    dateLabel.setText(formattedDate);
  }

  public void addKiosk() {
    if (moveMessage.getText().length() > 0 && moveDropdown.getValue() != null) {
      DBSession.addKioskMove(moves.get(moveDropdown.getSelectedIndex()), moveMessage.getText());
    }
  }
}
