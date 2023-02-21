package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Edge;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EdgeRepairController {
  @FXML Text node1Field, node2Field;
  @FXML MFXButton yesButton, noButton;
  private static Node node1, node2;

  public void initialize() {
    node1Field.setText(node1.getNodeID());
    node2Field.setText(node2.getNodeID());
  }

  public void noButtonClicked() {
    Popup.hidePopup(Screen.EXIT_CONFIRMATION);
  }

  public void yesButtonClicked() {
    if (node1 == null || node2 == null) {
      System.err.println("ONE OR BOTH NODES ARE NULL");
      return;
    }
    Edge edge = new Edge();
    edge.setNode1(node1);
    edge.setNode2(node2);
    DBSession.addEdge(edge);
    noButtonClicked();
  }

  public static void setNodes(Node n1, Node n2) {
    node1 = n1;
    node2 = n2;
  }
}
