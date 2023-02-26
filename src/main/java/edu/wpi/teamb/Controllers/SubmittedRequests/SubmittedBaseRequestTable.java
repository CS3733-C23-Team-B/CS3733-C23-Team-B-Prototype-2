package edu.wpi.teamb.Controllers.SubmittedRequests;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Database.Requests.GeneralRequest;
import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Entities.Urgency;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public abstract class SubmittedBaseRequestTable {
  @FXML protected TableView table = new TableView<>();
  @FXML private TableColumn employeeID = new TableColumn();
  @FXML private TableColumn firstname = new TableColumn();
  @FXML private TableColumn lastname = new TableColumn();
  @FXML private TableColumn email = new TableColumn();
  @FXML private TableColumn urgency = new TableColumn();
  @FXML private TableColumn<GeneralRequest, String> assignedEmployee = new TableColumn<>();
  @FXML private TableColumn<GeneralRequest, RequestStatus> status = new TableColumn<>();
  @FXML private TableColumn<GeneralRequest, String> notes = new TableColumn();
  @FXML TableColumn date = new TableColumn();
  Login l;
  private List<TableColumn> cols = new ArrayList<>();
  private List<String> colNames = new ArrayList<>();
  private ObservableList<RequestStatus> Status =
      FXCollections.observableArrayList(
          RequestStatus.BLANK, RequestStatus.PROCESSING, RequestStatus.DONE);
  private ObservableList<String> staff = DBSession.getStaff();

  public void initialize() {
    l = SigninController.getInstance().currentUser;
    addcol(date, "date");
    //    addcol(firstname, "firstname");
    //    addcol(lastname, "lastname");
    //    addcol(employeeID, "employeeID");
    //    addcol(email, "email");
    addcol(urgency, "urgency");
    addcol(assignedEmployee, "assignedEmployee");
    addcol(status, "status");
    //    addcol(notes, "notes");
  }

  public TableView getTable() {
    return table;
  }

  public TableView getTable(
      RequestStatus status,
      String assignedEmployee,
      String requestor,
      Urgency urgency,
      Boolean myRequestsOnly,
      Date d) {
    return table;
  }

  public void editableCols() {
    notes.setCellFactory(TextFieldTableCell.forTableColumn());
    notes.setOnEditCommit(
        e -> {
          GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
          r.setNotes(e.getNewValue());
          DBSession.updateRequest(r);
        });

    if (l.getAdmin()) {
      //      edit assignEmployee
      assignedEmployee.setCellFactory(new ComboBoxTableCell().forTableColumn(staff));
      assignedEmployee.setOnEditCommit(
          e -> {
            GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
            r.setAssignedEmployee(e.getNewValue());
            DBSession.updateRequest(r);
          });

      //       edit staff
      status.setCellFactory(new ComboBoxTableCell().forTableColumn(Status));
      status.setOnEditCommit(
          e -> {
            GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
            r.setStatus(e.getNewValue());
            DBSession.updateRequest(r);
          });
    }
    table.setEditable(true);
  }

  private void addcol(TableColumn col, String colName) {
    this.cols.add(col);
    this.colNames.add(colName);
  }

  protected void addCol(TableColumn col, String colName) {
    int insert = 2;
    this.cols.add(cols.size() - insert, col);
    this.colNames.add(colNames.size() - insert, colName);
  }

  protected void addColGR(TableColumn col, String colName) {
    int insert = cols.size() - 1;
    this.cols.add(cols.size() - insert, col);
    this.colNames.add(colNames.size() - insert, colName);
  }

  protected void setTable() {
    table.getItems().clear();
    for (int i = 0; i < cols.size(); i++) {
      cols.get(i).setCellValueFactory(new PropertyValueFactory<>(colNames.get(i)));
      table.getColumns().add(cols.get(i));
      cols.get(i).setText(colNames.get(i));
    }
    editableCols();
  }

  private List<GeneralRequest> filterTableStatus(
      RequestStatus status, List<GeneralRequest> objectList) {
    List<GeneralRequest> filtered = new ArrayList<>();
    objectList.forEach(
        (value) -> {
          if (status != null) {
            if (status == RequestStatus.DONE && value.getStatus() == RequestStatus.DONE) {
              filtered.add(value);
            } else if (status == RequestStatus.PROCESSING
                && value.getStatus() == RequestStatus.PROCESSING) {
              filtered.add(value);
            } else if (status == RequestStatus.BLANK && value.getStatus() == RequestStatus.BLANK) {
              filtered.add(value);
            }
          } else {
            filtered.add(value);
          }
        });
    return filtered;
  }

  private List<GeneralRequest> filterTableUrgency(
      Urgency urgency, List<GeneralRequest> objectList) {
    List<GeneralRequest> filtered = new ArrayList<>();
    objectList.forEach(
        (value) -> {
          if (urgency != null) {
            if (urgency == Urgency.LOW && value.getUrgency() == Urgency.LOW) {
              filtered.add(value);
            } else if (urgency == Urgency.MODERATE && value.getUrgency() == Urgency.MODERATE) {
              filtered.add(value);
            } else if (urgency == Urgency.HIGH && value.getUrgency() == Urgency.HIGH) {
              filtered.add(value);
            } else if (urgency == Urgency.REQUIRESIMMEADIATEATTENTION
                && value.getUrgency() == Urgency.REQUIRESIMMEADIATEATTENTION) {
              filtered.add(value);
            }
          } else {
            filtered.add(value);
          }
        });
    return filtered;
  }

  List<GeneralRequest> myRequests(List<GeneralRequest> objectList) {
    List<GeneralRequest> filtered = new ArrayList<>();
    String myName = l.getFirstname() + " " + l.getLastname();
    objectList.forEach(
        (value) -> {
          String name = value.getFirstname() + " " + value.getLastname();
          String assignedEmp = value.getAssignedEmployee();
          if (myName.equals(name) || myName.equals(assignedEmp)) {
            filtered.add(value);
          }
        });

    return filtered;
  }

  List<GeneralRequest> requestor(String requestor, List<GeneralRequest> objectList) {
    List<GeneralRequest> filtered = new ArrayList<>();
    if (requestor != null) {
      objectList.forEach(
          (value) -> {
            String name = value.getFirstname() + " " + value.getLastname();
            if (name.equals(requestor)) {
              filtered.add(value);
            }
          });
    }
    return filtered;
  }
  //  this one needs to go last cause it does the dirty work
  private void filterTableEmployee(String assignedEmployee, List<GeneralRequest> filtered) {
    table.getItems().clear();
    filtered.forEach(
        (value) -> {
          if (assignedEmployee != null) {
            if (value.getAssignedEmployee().equals(assignedEmployee)) {
              table.getItems().add(value);
            }
          } else {
            table.getItems().add(value);
          }
        });
  }

  protected void filterTable(
      RequestStatus status,
      String assignedEmployee,
      String requestor,
      List<GeneralRequest> objectList,
      Urgency urgency,
      Boolean myRequestsOnly,
      Date d) {
    table.getItems().clear();
    List<GeneralRequest> grList = objectList;
    List<GeneralRequest> filtered = filterTableStatus(status, grList);
    filtered = filterTableUrgency(urgency, filtered);
    if (myRequestsOnly) {
      filtered = myRequests(filtered);
    }
    if (requestor != null) {
      filtered = requestor(requestor, filtered);
    }

    filterTableEmployee(assignedEmployee, filtered);
  }
}
