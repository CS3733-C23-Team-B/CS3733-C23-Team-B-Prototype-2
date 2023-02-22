package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapEditorHelpPopUpController {

  @FXML private Button backButton;

  public void backButtonClicked() {
    Popup.hidePopup(Screen.MAP_EDITOR_HELP_POP_UP);
  }
}
