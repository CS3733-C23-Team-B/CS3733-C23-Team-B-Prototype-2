package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EdgeCreatorController {
  @FXML Text bigText;
  @FXML MFXFilterComboBox<String> edgeBox;
  private Node node = MapEditorController.getCurrentNode();

  public void initialize() {
    edgeBox.setItems(getNodes());
  }

  public void submitClicked() {
    if (edgeBox.getText().isEmpty()) {
      bigText.setText("No selection");
      bigText.setFill(Color.RED);
      return;
    }

    Edge newEdge = new Edge();
    newEdge.setNode1(node.getNodeID());
    newEdge.setNode2(edgeBox.getValue());
    DBSession.addORM(newEdge);
    cancelClicked();
  }

  public ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Collection<Node> nodes = DBSession.getAllNodes().values();
    nodes.forEach(n -> list.add(n.getNodeID()));
    return list;
  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }
}
