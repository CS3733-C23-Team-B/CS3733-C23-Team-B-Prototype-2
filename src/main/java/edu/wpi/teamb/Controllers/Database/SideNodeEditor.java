package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SideNodeEditor {

  @FXML MFXFilterComboBox selectNodeID;
  @FXML TextField xField;
  @FXML TextField yField;
  Node node = MapEditorController.getCurrentNode();
  String origFloor;
  @FXML MFXButton edgesButton;

  public void initialize() {
    ObservableList<String> floors = FXCollections.observableArrayList();
    Collections.addAll(floors, "1", "2", "3", "L1", "L2");
    xField.setPromptText("" + node.getXCoord());
    yField.setPromptText("" + node.getYCoord());
    selectNodeID.setItems(node.getNodeID( List< Move > moves = DBSession.getMostRecentMoves(node.getNodeID());));
  }

  public void submitClicked() {
    boolean changed = false;

    String newX = xField.getText();
    String newY = yField.getText();

    if (!newX.isEmpty()) {
      node.setXCoord(Integer.parseInt(newX));
      changed = true;
    }
    if (!newY.isEmpty()) {
      node.setYCoord(Integer.parseInt(newY));
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
    Stage s = (Stage) yField.getScene().getWindow();
    s.close();
    MapEditorController.getInstance().refreshPopUp();
  }

//  public void edgesClicked2() {
//    Navigation.navigate(Screen.EDGE_EDITOR);
//    Stage s = (Stage) yField.getScene().getWindow();
//    s.close();
//  }

  public void deleteClicked() {
    DBSession.deleteNode(node);
    Stage s = (Stage) yField.getScene().getWindow();
    s.close();
    MapEditorController.getInstance().removeNode();
  }
}
