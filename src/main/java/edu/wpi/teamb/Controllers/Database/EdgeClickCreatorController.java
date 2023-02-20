package edu.wpi.teamb.Controllers.Database;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EdgeClickCreatorController {

  @FXML Text text1, text2;

  public void edgeCancel() {
    MapEditorController.getInstance().cancelClickEdge();
  }
}
