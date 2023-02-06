package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LocationEditorController {
  @FXML ChoiceBox nodeChange;
  @FXML Button submitChange;
  @FXML TextField xCoord;
  @FXML TextField yCoord;
  @FXML TextField locationField;
  @FXML Button dataHelp;
  @FXML Button exit;

  /** Method run when controller is initialized */
  public void initialize() {
    //    nodeSearchButton.setOnAction((actionEvent) -> getNodeData());
    //    edgeQuery.setOnAction((actionEvent) -> getEdgeData());
    //    dataHelp.setOnAction((actionEvent) -> changeToHelp());
    exit.setOnAction(
        (actionEvent) -> {
          try {
            exitButtonClicked();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    nodeChange.setItems(getNodes());
    submitChange.setOnAction(
        (actionEvent) -> {
          try {
            changeCoords();
            changeLocation();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  /** Inserts thing into database */
  private void changeCoords() throws SQLException {
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    Node n = nodes.get(nodeChange.getValue());

    n.setCoords(Integer.parseInt(xCoord.getText()), Integer.parseInt(yCoord.getText()));
  }

  public void exitButtonClicked() throws IOException {
    Stage stage = (Stage) exit.getScene().getWindow();
    stage.close();
  }

  private void changeToHelp() {
    Navigation.navigate(Screen.DATABASE_HELP);
  }

  private void changeLocation() throws SQLException {
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    Node n = nodes.get(nodeChange.getValue());
    String nID = n.getID();
    String oldLocName = Move.getLocationName(nID);
    String newLoc = locationField.getText();
    Map<String, LocationName> allLocs = LocationName.getAll();
    LocationName loc = allLocs.get(oldLocName);
  }

  /** Queries data from database, displays in list */
  private void getNodeData() {
    BorderPane bor = (BorderPane) nodeChange.getScene().getRoot();
    VBox nodeBox = new VBox();
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    nodeBox.getChildren().clear();
    nodeBox.getChildren().add(new Label("Current Nodes:"));
    nodes.forEach(
        (key, value) -> {
          Label label = new Label(value.getInfo());
          nodeBox.getChildren().add(label);
          label.setFont(new Font("Arial", 7));
        });
    bor.setCenter(nodeBox);
    Button b = new Button();
    b.setText("Back");
    b.setOnAction(e -> Navigation.navigate(Screen.MAP_DATA_EDITOR));
    nodeBox.getChildren().add(b);
  }

  static ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, Node> nodes;
    try {
      nodes = Node.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    nodes.forEach((key, value) -> list.add(key));

    return list;
  }
}
