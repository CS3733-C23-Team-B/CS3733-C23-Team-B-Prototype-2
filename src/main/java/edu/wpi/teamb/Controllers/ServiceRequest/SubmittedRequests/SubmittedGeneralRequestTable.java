package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.GeneralRequest;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Entities.RequestStatus;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class SubmittedGeneralRequestTable {
  @FXML protected TableView table = new TableView<>();

  @FXML protected TableColumn employeeID = new TableColumn();
  @FXML protected TableColumn firstname = new TableColumn();
  @FXML protected TableColumn lastname = new TableColumn();
  @FXML protected TableColumn email = new TableColumn();
  @FXML protected TableColumn urgency = new TableColumn();
  @FXML protected TableColumn assignedEmployee = new TableColumn();
  @FXML protected TableColumn<GeneralRequest, RequestStatus> status = new TableColumn();
  @FXML protected TableColumn notes = new TableColumn();

  @FXML TableColumn date = new TableColumn();
  Login l;
  List<TableColumn> cols =
      Arrays.asList(
          firstname, lastname,employeeID,  email, urgency, assignedEmployee, status, date, notes);
  List<String> colNames =
      Arrays.asList(
          "firstname",
          "lastname",
          "employeeID",
          "email",
          "urgency",
          "assignedEmployee",
          "status",
          "date",
          "notes");

  public void initialize() {
    l = SigninController.getCurrentUser();


    //        initialize the columns
    //        add columns to the table
    //        set col names
    for (int i = 0; i < cols.size(); i++) {
      cols.get(i).setCellValueFactory(new PropertyValueFactory<>(colNames.get(i)));
      table.getColumns().add(cols.get(i));
      cols.get(i).setText(colNames.get(i));
    }
    editableCols();
  }

  public TableView getTable() {
    return table;
  }

  public void editableCols() {
    if (DBSession.isAdmin(l)) {
      status.setCellFactory(TextFieldTableCell.forTableColumn(converter));
      status.setOnEditCommit(
          e -> {
            GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
            r.setStatus(e.getNewValue());
            DBSession.updateRequest(r);
          });
    }
    table.setEditable(true);
  }

  StringConverter<RequestStatus> converter =
      new StringConverter<>() {
        @Override
        public String toString(RequestStatus object) {
          if (object == null) return "";
          return object.toString();
        }

        @Override
        public RequestStatus fromString(String string) {
          switch (string) {
            case ("blank"):
              return RequestStatus.BLANK;
            case ("processing"):
              return RequestStatus.PROCESSING;
            case ("done"):
              return RequestStatus.DONE;
            default:
              return RequestStatus.PROCESSING;
          }
        }
      };

  public void SubmittedGeneralRequestTable() {}
}
