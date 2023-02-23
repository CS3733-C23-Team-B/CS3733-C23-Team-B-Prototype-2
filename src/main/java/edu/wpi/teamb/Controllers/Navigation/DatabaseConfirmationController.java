package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Database.DatabaseRestore;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.ParseException;
import javafx.fxml.FXML;

public class DatabaseConfirmationController {

  @FXML MFXButton yesButton;
  @FXML MFXButton noButton;

  public void noButtonClicked() {
    Popup.hidePopup(Screen.DATABASE_CONFIRMATION);
  }

  public void yesButtonClicked() {
    try {
      DatabaseRestore.runRestore();
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    } catch (ParseException ex) {
      throw new RuntimeException(ex);
    }
    Popup.hidePopup(Screen.DATABASE_CONFIRMATION);
  }
}
