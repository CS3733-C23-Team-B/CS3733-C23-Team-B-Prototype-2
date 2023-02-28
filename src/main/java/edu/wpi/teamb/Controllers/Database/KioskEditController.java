package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.KioskMove;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

public class KioskEditController {

  @FXML MFXFilterComboBox<String> moveDropdown;
  @FXML MFXTextField moveMessage;
  @FXML Label timeLabel;
  @FXML Label dateLabel;

  @FXML TableView<KioskMove> table;
  @FXML TableColumn dateCol;
  @FXML TableColumn messageCol;
  @FXML TableColumn nameCol;
  @FXML TableColumn startCol;
  @FXML TableColumn endCol;

  private List<Move> moves;

  @Getter public static KioskEditController instance;

  @Getter @Setter public int rowVal;

  public void initialize() {
    instance = this;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
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

    dateCol.setCellValueFactory(new PropertyValueFactory<>("moveDate"));
    messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("locationName"));
    startCol.setCellValueFactory(new PropertyValueFactory<>("prevNode"));
    endCol.setCellValueFactory(new PropertyValueFactory<>("nextNode"));

    table
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                KioskMove selectedData = table.getSelectionModel().getSelectedItem();
                String info =
                    "Selected: "
                        + selectedData.getLocationName()
                        + ", Message: "
                        + selectedData.getMessage();
                System.out.println(info);
                this.getInstance().setRowVal(table.getSelectionModel().getSelectedIndex());
                table
                    .getSelectionModel()
                    .clearSelection(table.getSelectionModel().getSelectedIndex());
                Popup.displayPopup(Screen.KIOSK_POPUP);
              }
            });
    setTableValues();
  }

  private void setTableValues() {
    List<KioskMove> allKiosks = DBSession.getAllKioskMoves();
    table.getItems().clear();
    allKiosks.forEach(
        (value) -> {
          table.getItems().add(value);
        });
  }

  public void addKiosk() {
    if (moveMessage.getText().length() > 0 && moveDropdown.getValue() != null) {
      DBSession.addKioskMove(moves.get(moveDropdown.getSelectedIndex()), moveMessage.getText());
      moveMessage.clear();
      moveDropdown.clear();
      setTableValues();
    }
  }
}
