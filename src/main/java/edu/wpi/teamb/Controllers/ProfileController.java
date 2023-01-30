package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.Login;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class ProfileController {
    @FXML private Text usernameText;
    @FXML private Text passwordText;
    @FXML private ToggleButton showPasswordButton;

    private String passwordDisplay;

    public void initialize() {
        Login user = SigninController.currentUser;
        usernameText.setText("Username: " + user.getUsername());
        String passwordDisplay = "Password: ";
        for (int i = 0; i < user.getPassword().length(); i++)
            passwordDisplay += "*";
        passwordText.setText(passwordDisplay);
    }

    public void showPassword() {

    }
}
