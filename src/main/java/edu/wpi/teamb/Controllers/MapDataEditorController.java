package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Database.NodeInfo;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class MapDataEditorController {
  @FXML TableView<NodeInfo> dataTable;
  @FXML TableColumn<NodeInfo, String> IDColumn;
  @FXML TableColumn<NodeInfo, Integer> xColumn;
  @FXML TableColumn<NodeInfo, Integer> yColumn;
  @FXML TableColumn<NodeInfo, String> locationColumn;
  @FXML TableColumn<NodeInfo, Date> dateColumn;
  @FXML TableColumn<NodeInfo, String> edgesColumn;

  StringConverter<Integer> converter =
      new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
          return object.toString();
        }

        @Override
        public Integer fromString(String string) {
          return Integer.parseInt(string);
        }
      };

  /** Refreshes the page */
  public void refresh() {
    Navigation.navigate(Screen.MAP_DATA_EDITOR);
  }

  /** Initializes the data table with all the required columns */
  public void initialize() {
    IDColumn.setCellValueFactory(new PropertyValueFactory<NodeInfo, String>("nodeID"));
    xColumn.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    yColumn.setCellValueFactory(new PropertyValueFactory<NodeInfo, Integer>("yCoord"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<NodeInfo, String>("location"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<NodeInfo, Date>("moveDate"));
    edgesColumn.setCellValueFactory(new PropertyValueFactory<NodeInfo, String>("edges"));

    List<NodeInfo> nodeList = new ArrayList<>();
    Map<String, Node> nodes;

    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    for (Node node : nodes.values()) {
      String nodeID = node.getID();
      int xCoord = node.getXCoord();
      int yCoord = node.getYCoord();
      Move move = Move.getMostRecentMove(nodeID);
      Date moveDate = move.getMoveDate();
      String location = move.getLongName();
      String edges = "";
      for (String edge : Pathfinding.getDirectPaths(nodeID)) edges += edge + ", ";
      edges = edges.substring(0, edges.length() - 2);
      nodeList.add(new NodeInfo(nodeID, xCoord, yCoord, location, moveDate, edges, node, move));
    }

    nodeList.forEach((value) -> dataTable.getItems().add(value));
    editableCols();
  }

  /**
   * Makes the columns editable that the user should have access to edit. When text is edited,
   * handles editing the actual instances in the database
   */
  public void editableCols() {
    xColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
    xColumn.setOnEditCommit(
        e -> {
          NodeInfo node = e.getTableView().getItems().get(e.getTablePosition().getRow());
          node.setxCoord(e.getNewValue());
          node.update();
          Pathfinding.refreshData();
        });

    yColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
    yColumn.setOnEditCommit(
        e -> {
          NodeInfo node = e.getTableView().getItems().get(e.getTablePosition().getRow());
          node.setyCoord(e.getNewValue());
          node.update();
          Pathfinding.refreshData();
        });

    locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    locationColumn.setOnEditCommit(
        e -> {
          NodeInfo node = e.getTableView().getItems().get(e.getTablePosition().getRow());

          Map<String, LocationName> locationNames;
          try {
            locationNames = LocationName.getAll();
            locationNames.get(node.getLocation()).updateLN(e.getNewValue());
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
          node.setLocation(e.getNewValue());
        });

    dataTable.setEditable(true);
  }
}
