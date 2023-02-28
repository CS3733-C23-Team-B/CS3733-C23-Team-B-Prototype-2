package edu.wpi.teamb.Controllers.Popups;

import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;

public class KioskPopup {
  public void backButtonClicked() {
    Popup.hidePopup(Screen.KIOSK_POPUP);
  }
}
