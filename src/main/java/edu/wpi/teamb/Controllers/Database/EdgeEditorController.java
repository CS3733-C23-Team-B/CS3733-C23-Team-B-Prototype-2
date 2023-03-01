package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class EdgeEditorController {
  @FXML Label nodeIDText;
  @FXML Label bigText;
  @FXML MFXFilterComboBox<String> edgesBox;
  private Node node;

  /** Method run when controller is initialized */
  public void initialize() {
    Pathfinding.refreshData();
    node = MapEditorController.getCurrentNode();
    nodeIDText.setText(node.getNodeID());
    edgesBox.setItems(getEdges());
  }

  private ObservableList<String> getEdges() {
    ObservableList<String> list = FXCollections.observableArrayList();
    List<String> edges = Pathfinding.getDirectPaths(node.getNodeID());
    edges.forEach(e -> list.add(e));
    return list;
  }

  private void resetFields() {
    edgesBox.setItems(getEdges());
    edgesBox.setValue("");
  }

  public void cancelClicked() {
    MapEditorController.getInstance().clearForm();
  }

  public void newEdgeClicked() throws IOException {
    MapEditorController.getInstance().getForms().getChildren().clear();
    final var res = Bapp.class.getResource(Screen.EDGE_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    MapEditorController.getInstance().getForms().getChildren().add(loader.load());
  }

  public void deleteClicked() {
    String s = edgesBox.getValue();
    if (edgesBox.getText().isEmpty()) {
      bigText.setText("No selection");
      bigText.setStyle("-fx-text-fill: red");
      return;
    }
    Node n1 = node;
    Node n2 = DBSession.getAllNodes().get(edgesBox.getValue());
    DBSession.deleteEdge(n1, n2);
    Pathfinding.refreshData();
    resetFields();
  }
}
