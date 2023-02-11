package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Entities.ORMType;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllUsersController {

  @FXML TableView table;
  @FXML TableColumn id;
  @FXML TableColumn first;
  @FXML TableColumn last;
  @FXML TableColumn email;
  @FXML TableColumn user;
  @FXML TableColumn admin;

  public void initialize() {
    List<Object> userList;
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    first.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    last.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    user.setCellValueFactory(new PropertyValueFactory<>("username"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    admin.setCellValueFactory(new PropertyValueFactory<>("admin"));

    userList = DBSession.getAll(ORMType.LOGIN);
    userList.forEach(
        (value) -> {
          table.getItems().add(value);
        });
  }

  public void backButton() {
    Navigation.navigate(Screen.PROFILE);
  }
}
