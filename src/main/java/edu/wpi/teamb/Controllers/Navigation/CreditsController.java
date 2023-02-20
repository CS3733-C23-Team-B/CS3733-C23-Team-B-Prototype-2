package edu.wpi.teamb.Controllers.Navigation;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

import java.io.IOException;

public class CreditsController {
    @FXML
    private MFXButton backButton;

    public void backButtonClicked() throws IOException {
        Navigation.navigate(Screen.HOME);
    }
}

}
