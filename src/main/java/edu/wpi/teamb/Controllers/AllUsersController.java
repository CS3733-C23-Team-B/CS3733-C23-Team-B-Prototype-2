package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class AllUsersController {

  @FXML TableView table;
  @FXML TableColumn id;
  @FXML TableColumn first;
  @FXML TableColumn last;
  @FXML TableColumn email;
  @FXML TableColumn user;
  @FXML TableColumn<Login, Boolean> admin;

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
    Map<String, Login> userMap = new HashMap<String, Login>();
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    first.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    last.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    user.setCellValueFactory(new PropertyValueFactory<>("username"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    admin.setCellValueFactory(new PropertyValueFactory<>("admin"));

    userMap = DBSession.getAllLogins();
    userMap.forEach(
        (key, value) -> {
          table.getItems().add(value);
        });

    editableCols();
  }

  public void editableCols() {
    admin.setCellFactory(TextFieldTableCell.forTableColumn(converter));
    admin.setOnEditCommit(
        e -> {
          Login login = e.getTableView().getItems().get(e.getTablePosition().getRow());
          login.setAdmin(e.getNewValue());
          DBSession.updateAdmin(login.getUsername(), login.getAdmin());
        });

    table.setEditable(true);
  }

  public void backButton() {
    Navigation.navigate(Screen.PROFILE);
  }
}
