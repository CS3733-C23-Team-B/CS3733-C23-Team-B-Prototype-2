package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Bapp;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SideNodeEditor {

  @FXML MFXFilterComboBox selectNodeID;
  @FXML TextField xField;
  @FXML TextField yField;
  Node currentNode;
  private Map<String, List<Move>> moveMap;
  String origFloor;
  @FXML MFXButton edgesButton;
  @FXML MFXFilterComboBox floorBox;
  Circle dot;
  @FXML private AnchorPane forms;

  public void initialize() {
    moveMap = DBSession.getIDMoves(new Date(123, 1, 1));
    ObservableList<String> floors = FXCollections.observableArrayList();
    Collections.addAll(floors, "1", "2", "3", "L1", "L2");
    ObservableList<String> nodes = FXCollections.observableArrayList();
    for (Node n : DBSession.getAllNodes().values()) nodes.add(n.getNodeID());
    selectNodeID.setItems(nodes);
    floorBox.setItems(floors);
  }

  public void setFields() {
    if (selectNodeID.getText().isEmpty()) return;
    currentNode = DBSession.getAllNodes().get(selectNodeID.getText());
    if (currentNode == null) return;
    MapEditorController.setCurrentNode(currentNode);
    dot = MapEditorController.getInstance().getDot(currentNode);
    origFloor = currentNode.getFloor();
    xField.setPromptText("" + currentNode.getXCoord());
    yField.setPromptText("" + currentNode.getYCoord());
    floorBox.setValue("" + currentNode.getFloor());
  }

  public void submitClicked() {
    boolean changed = false;

    String newX = xField.getText();
    String newY = yField.getText();
    String newFloor = floorBox.getText();

    if (!newX.isEmpty()) {
      currentNode.setXCoord(Integer.parseInt(newX));
      changed = true;
    }
    if (!newY.isEmpty()) {
      currentNode.setYCoord(Integer.parseInt(newY));
      changed = true;
    }

    if (!newFloor.equals(origFloor)) {
      currentNode.setFloor(newFloor);
      changed = true;
    }

    if (changed) {
      DBSession.updateNode(currentNode);
      currentNode.setNodeID(currentNode.buildID());
      Pathfinding.refreshData();
    }
    MapEditorController.setCurrentNode(currentNode);
    MapEditorController.getInstance().setCurrentDot(dot);
    MapEditorController.getInstance().refreshPopUp();

    ObservableList<String> nodes = FXCollections.observableArrayList();
    for (Node n : DBSession.getAllNodes().values()) nodes.add(n.getNodeID());
    selectNodeID.setItems(nodes);

    resetFields();
  }

  public void cancelClicked() {
    resetFields();
  }

  public void resetFields() {
    selectNodeID.setValue("");
    xField.setText("");
    yField.setText("");
    xField.setPromptText("");
    yField.setPromptText("");
    floorBox.setText("");
  }

  public void edgesClicked() throws IOException {
    forms.getChildren().clear();
    final var res = Bapp.class.getResource(Screen.MOVE_CREATOR.getFilename());
    final FXMLLoader loader = new FXMLLoader(res);
    forms.getChildren().add(loader.load());
  }

  public void deleteClicked() {
    DBSession.deleteNode(currentNode);
    Stage s = (Stage) yField.getScene().getWindow();
    s.close();
    MapEditorController.getInstance().removeNode();
  }
}
