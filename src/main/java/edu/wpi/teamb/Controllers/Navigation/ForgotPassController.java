package edu.wpi.teamb.Controllers.Navigation;

import static edu.wpi.teamb.Database.SendEmail.sendEmail;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Navigation.Popup;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.Map;
import javafx.fxml.FXML;
import javax.mail.MessagingException;

public class ForgotPassController {
  @FXML MFXTextField username;
  @FXML MFXButton sendPassword;

  public void sendPassword() {
    Map<String, Login> loginMap = DBSession.getAllLogins();
    if (username.getText().length() > 0) {
      try {
        String user = "bodaciousbadger1@gmail.com";
        String password = "ftannejwvxyokvet";
        String recipient = loginMap.get(username.getText()).getEmail();
        String subject = "Forgot Password";

        String message =
            "Dear "
                + loginMap.get(username.getText()).getFirstname()
                + ",\nYour Password is: "
                + loginMap.get(username.getText()).getPassword()
                + "\nHave a great day! :)";
        sendEmail(user, password, recipient, subject, message);

      } catch (MessagingException ex) {
        System.out.println("Failed to send email: " + ex.getMessage());
      }
      Popup.hidePopup(Screen.FORGOT_PASSWORD);
    }
  }

  public void close() {
    Popup.hidePopup(Screen.FORGOT_PASSWORD);
  }
}
