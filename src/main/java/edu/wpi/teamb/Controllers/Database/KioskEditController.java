package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.*;
import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lombok.Getter;

public class KioskEditController {

  @FXML MFXFilterComboBox<String> moveDropdown;
  @FXML MFXFilterComboBox<String> locationDropdown;
  @FXML MFXTextField moveMessage;
  @FXML Label timeLabel;
  @FXML Label dateLabel;
  @FXML MFXButton preview;

  @FXML TableView<KioskMove> table;
  @FXML TableColumn dateCol;
  @FXML TableColumn<KioskMove, String> messageCol;
  @FXML TableColumn nameCol;
  @FXML TableColumn startCol;
  @FXML TableColumn endCol;
  @FXML MFXCheckbox inverseBox;

  private List<Move> moves;

  @Getter public static KioskEditController instance;
  @Getter public KioskMove currentSelection;

  public void initialize() {
    instance = this;
    preview.setDisable(true);
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    moves = DBSession.getAllMoves();
    String current = MapDAO.getKioskLocation().getLocationName().getLongName();
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

    ObservableList<String> list = FXCollections.observableArrayList();

    Map<String, LocationName> locationNames = DBSession.getAllLocationNames();
    locationNames.forEach((key, value) -> list.add(value.getLongName()));
    Sorting.quickSort(list);
    locationDropdown.setItems(list);
    locationDropdown.setValue(current);
    locationDropdown.setOnAction(
        e -> {
          System.out.println(locationDropdown.getSelectedItem());
          KioskLocation k = new KioskLocation();
          if (inverseBox.isSelected()) k.setInverse(true);
          else k.setInverse(false);
          k.setLocationName(locationNames.get(locationDropdown.getSelectedItem()));
          MapDAO.updateKioskLocation(k);
        });
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
                currentSelection = table.getSelectionModel().getSelectedItem();
                preview.setDisable(false);
              }
            });
    if (table.getSelectionModel().getSelectedItem() == null) {
      preview.setDisable(true);
    }
    messageCol.setCellFactory(TextFieldTableCell.forTableColumn());
    messageCol.setOnEditCommit(
        e -> {
          KioskMove kiosk = e.getTableView().getItems().get(e.getTablePosition().getRow());
          kiosk.setMessage(e.getNewValue());
          MapDAO.updateKioskMove(kiosk);
        });
    table.setEditable(true);

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

  public void previewClicked() {
    Popup.displayPopup(Screen.KIOSK_POPUP);
  }

  public void handleKeyPress(KeyEvent event) {
    if (event.getCode().equals(KeyCode.BACK_SPACE)) {
      System.out.println("key pressed");
      if (table.getSelectionModel().getSelectedItem() != null) {
        KioskMove k = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(k);
        DBSession.deleteKioskMove(k);
      }
    }
  }

  public void helpButtonClicked() {
    Popup.displayPopup(Screen.KIOSK_HELP);
  }
}
