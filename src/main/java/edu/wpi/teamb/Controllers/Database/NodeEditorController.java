package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeEditorController {
  @FXML Text nodeIDText;
  @FXML MFXTextField xField;
  @FXML MFXTextField yField;
  @FXML MFXFilterComboBox<String> floorBox;
  Node node = MapEditorController.getCurrentNode();
  Circle circle = MapEditorController.getCurrentDot();
  String origFloor;

  public void initialize() {
    ObservableList<String> floors = FXCollections.observableArrayList();
    Collections.addAll(floors, "1", "2", "3", "L1", "L2");
    xField.setPromptText("" + node.getXCoord());
    yField.setPromptText("" + node.getYCoord());
    origFloor = node.getFloor();
    floorBox.setItems(floors);
    floorBox.setValue(origFloor);
    nodeIDText.setText(node.getNodeID());
  }

  public void submitClicked() {
    boolean changed = false;

    String newX = xField.getText();
    String newY = yField.getText();
    String newFloor = floorBox.getValue();

    if (!newX.isEmpty()) {
      node.setXCoord(Integer.parseInt(newX));
      changed = true;
    }
    if (!newY.isEmpty()) {
      node.setYCoord(Integer.parseInt(newY));
      changed = true;
    }
    if (!newFloor.equals(origFloor)) {
      node.setFloor(newFloor);
      changed = true;
    }

    if (changed) {
      DBSession.updateNode(node);
      Node newNode = node;
      newNode.setNodeID(newNode.buildID());
      Pathfinding.refreshData();
      MapEditorController.setCurrentNode(newNode);
    }
    cancelClicked();
  }

  public void cancelClicked() {
    MapEditorController.setCurrentDot(circle);
    MapEditorController.getInstance().refreshPopUp();
    MapEditorController.getInstance().clearForm();
  }

  public void edgesClicked() throws IOException {
    MapEditorController.getInstance().getForms().getChildren().clear();
    final var res = Bapp.class.getResource(Screen.EDGE_EDITOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    MapEditorController.getInstance().getForms().getChildren().add(loader.load());
  }

  public void deleteClicked() {
    MapEditorController.getInstance().clearForm();
    MapEditorController.promptEdgeRepair(node);
    MapEditorController.getInstance().removeNode();
    DBSession.deleteNode(node);
  }
}
