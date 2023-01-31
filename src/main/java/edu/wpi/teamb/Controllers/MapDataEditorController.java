package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Database.NodeInfo;
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

public class MapDataEditorController {
  @FXML TableView dataTable;
  @FXML TableColumn IDColumn;
  @FXML TableColumn xColumn;
  @FXML TableColumn yColumn;
  @FXML TableColumn locationColumn;
  @FXML TableColumn dateColumn;
  @FXML TableColumn edgesColumn;

  public void initialize() {
    IDColumn.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    xColumn.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    yColumn.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("moveDate"));
    edgesColumn.setCellValueFactory(new PropertyValueFactory<>("edges"));

    List<NodeInfo> nodeList = new ArrayList<>();
    Map<String, Node> nodes;

    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    for (Node node : nodes.values()) {
      String nodeID = node.getID();
      int xCoord = node.getXcoord();
      int yCoord = node.getYcoord();
      Move move = Move.getMostRecentMove(nodeID);
      Date moveDate = move.getMoveDate();
      String location = move.getLongName();
      String edges = "";
      for (String edge : Pathfinding.getDirectPaths(nodeID)) edges += edge + ", ";
      edges = edges.substring(0, edges.length() - 2);
      nodeList.add(new NodeInfo(nodeID, xCoord, yCoord, location, moveDate, edges, node, move));
    }

    nodeList.forEach((value) -> dataTable.getItems().add(value));
  }
}
