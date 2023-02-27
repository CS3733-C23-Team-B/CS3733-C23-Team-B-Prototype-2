package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;

public class AboutPageController {

  @FXML MFXButton backButton;
  @FXML
  ImageView christinaAube,
      lukeGrady,
      samColebourn,
      kavyaMani,
      adrianJohnson,
      jackLafond,
      michaelGatti,
      seanLendrum,
      jolieWalts,
      cierraOGrady;

  public void initialize() {
    List<ImageView> people =
        Arrays.asList(
            christinaAube,
            lukeGrady,
            samColebourn,
            kavyaMani,
            adrianJohnson,
            jackLafond,
            michaelGatti,
            seanLendrum,
            jolieWalts,
            cierraOGrady);
    for (ImageView image : people) image.setCursor(Cursor.HAND);
    System.out.println("INITIALIZED");
  }

  public void backButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void kavyaManiClicked() {
    Popup.displayPopup(Screen.KAVYA_MANI);
  }

  public void cierraOGradyClicked() {
    Popup.displayPopup(Screen.CIERRA_OGRADY);
  }

  public void adrianJohnsonClicked() {
    Popup.displayPopup(Screen.ADRIAN_JOHNSON);
  }

  public void jolieWaltsClicked() {
    Popup.displayPopup(Screen.JOLIE_WALTS);
  }

  public void lukeGradyClicked() {
    Popup.displayPopup(Screen.LUKE_GRADY);
  }

  public void jackLafordClicked() {
    Popup.displayPopup(Screen.JACK_LAFORD);
  }

  public void christinaAubeClicked() {
    Popup.displayPopup(Screen.CHRISTINA_AUBE);
  }

  public void samColebournClicked() {
    Popup.displayPopup(Screen.SAM_COLEBOURN);
  }

  public void seanLendrumClicked() {
    Popup.displayPopup(Screen.SEAN_LENDRUM);
  }

  public void michaelGattiClicked() {
    Popup.displayPopup(Screen.MICHAEL_GATTI);
  }
}
