package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EdgeEditorController {
  @FXML Text nodeIDText;
  @FXML Text bigText;
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

  public void newEdgeClicked() {
    Navigation.navigate(Screen.EDGE_CREATOR);
  }

  public void deleteClicked() {
    String s = edgesBox.getValue();
    if (edgesBox.getText().isEmpty()) {
      bigText.setText("No selection");
      bigText.setFill(Color.RED);
      return;
    }
    Node n1 = node;
    Node n2 = DBSession.getAllNodes().get(edgesBox.getValue());
    DBSession.deleteEdge(n1, n2);
    Pathfinding.refreshData();
    resetFields();
  }
}
