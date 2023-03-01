package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EdgeCreatorController {
  @FXML Label bigText;
  @FXML MFXFilterComboBox<String> edgeBox;
  private Node node = MapEditorController.getCurrentNode();

  public void initialize() {
    edgeBox.setItems(getNodes());
  }

  public void submitClicked() {
    if (edgeBox.getText().isEmpty()) {
      bigText.setText("No selection");
      bigText.setStyle("-fx-text-fill: red");
      return;
    }

    Edge newEdge = new Edge();
    newEdge.setNode1(node);
    newEdge.setNode2(DBSession.getAllNodes().get(edgeBox.getValue()));
    DBSession.addEdge(newEdge);
    Pathfinding.refreshData();
    cancelClicked();
  }

  public ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Collection<Node> nodes = DBSession.getAllNodes().values();
    nodes.forEach(n -> list.add(n.getNodeID()));
    return list;
  }

  public void cancelClicked() {
    MapEditorController.getInstance().clearForm();
  }
}
